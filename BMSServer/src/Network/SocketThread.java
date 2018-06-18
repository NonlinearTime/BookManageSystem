package Network;

import Conponent.DataAccess;
import Conponent.Email;
import Conponent.MessageData;
import Conponent.MessageType;

import java.awt.desktop.SystemSleepEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;

public class SocketThread extends Thread {
    private Socket socket;
    private ObjectOutputStream out;
    private Consumer<Serializable> onReceiveCallback;
    private PreparedStatement preparedStatement;
    private HashMap<String, String> codeMap = new HashMap<>();
    public SocketThread(Socket socket) {
        this.socket = socket;
        this.onReceiveCallback = this::CallBack;
    }
    public void send(Serializable data) throws IOException {
        out.writeObject(data);
    }
    @Override
    public void run () {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            this.out = out;
            this.socket.setTcpNoDelay(true);

            while (true) {
                Serializable data = (Serializable) in.readObject();
                onReceiveCallback.accept(data);
            }

        } catch (Exception e) {
            onReceiveCallback.accept(new MessageData(MessageType.error, new ArrayList<>()));
        }
    }

    private void CallBack(Serializable data) {
        MessageData messageData = (MessageData) data;
        switch (messageData.getMessageType()) {
            case MessageType.regReq:
                onRegisterCallBack(messageData);
                break;
            case MessageType.fndReq:
                onFindbackpwdCallBack(messageData);
                break;
            case MessageType.fndCodeReq:
                onFindCodeReqCallBack(messageData);
                break;
            case MessageType.userLoginReq:
                onUserLoginCallBack(messageData);
                break;
            case MessageType.adminLoginReq:
                onAdminLoginCallBack(messageData);
                break;
            case MessageType.altPwd:
                onAltPwdCallBack(messageData);
            default:
                break;
        }
    }

    private void onAltPwdCallBack(MessageData messageData) {
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 4;
        String code = data.get(3);
        String email = data.get(2);
        String newPwd = data.get(1);
        String userID = data.get(0);
        MessageData reply = new MessageData();

        if (!code.equals(codeMap.get(email))) {
            reply.setMessageType(MessageType.altDeny);
            reply.getData().add("验证码错误");
            try {
                send(reply);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try {
            System.out.println(Integer.valueOf(userID));
            preparedStatement = DataAccess.getConnection().prepareStatement(
                    "update User set uPwd = ? where uID = ?"
            );
            preparedStatement.setString(1,newPwd);
            preparedStatement.setInt(2,Integer.valueOf(userID));
            preparedStatement.executeUpdate();
            reply.setMessageType(MessageType.altAdmit);
            try {
                send(reply);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onRegisterCallBack(MessageData messageData){
        int num = messageData.getData().size();
        ArrayList<String> data = messageData.getData();
        assert num >= 5;
        MessageData reply = new MessageData();
        String sql = "select * from User where uName = '" + messageData.getData().get(0).trim() + "'";
        System.out.println(messageData.getData().get(0));
        ResultSet rs = DataAccess.Query(sql);
        try {
            System.out.println(rs.wasNull());
            if (rs.next()) {
                reply.getData().add("用户名已存在");
                reply.setMessageType(MessageType.regDeny);
                try {
                    send(reply);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send(reply);
            } else {
                preparedStatement = DataAccess.getConnection().prepareStatement(
                        "insert into User (uName, uPwd, email, uTele, uType)" +
                                "values (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS
                );
                preparedStatement.setString(1,data.get(0));
                preparedStatement.setString(2,data.get(1));
                preparedStatement.setString(3,data.get(2));
                preparedStatement.setString(4,data.get(3));
                preparedStatement.setString(5,data.get(4));
                preparedStatement.executeUpdate();

                rs = preparedStatement.getGeneratedKeys();
                rs.next();
                int userID = rs.getInt(1);
                System.out.println(userID);

                reply.setMessageType(MessageType.regAdmit);
                reply.getData().add(String.valueOf(userID));
                send(reply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onFindbackpwdCallBack(MessageData messageData) {
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 4;
        String code = data.get(3);
        String email = data.get(2);
        String newPwd = data.get(1);
        String userID = data.get(0);
        MessageData reply = new MessageData();

        if (!code.equals(codeMap.get(email))) {
            reply.setMessageType(MessageType.fndDeny);
            reply.getData().add("验证码错误");
            try {
                send(reply);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        String sql = "select * from User where uID = " + userID;
        ResultSet rs = DataAccess.Query(sql);
        try {
            if (rs.wasNull()) {
                reply.setMessageType(MessageType.fndDeny);
                reply.getData().add("账户不存在");
                try {
                    send(reply);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            if (!rs.next()) {
                System.out.println("next");
                return;
            }
            if (!email.equals(rs.getString("email"))) {
                reply.setMessageType(MessageType.fndDeny);
                reply.getData().add("邮箱错误");
                try {
                    send(reply);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            System.out.println(Integer.valueOf(userID));
            preparedStatement = DataAccess.getConnection().prepareStatement(
                    "update User set uPwd = ? where uID = ?"
            );
            preparedStatement.setString(1,newPwd);
            preparedStatement.setInt(2,Integer.valueOf(userID));
            preparedStatement.executeUpdate();
            reply.setMessageType(MessageType.fndAdmit);
            try {
                send(reply);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onFindCodeReqCallBack(MessageData messageData) {
        System.out.println("get fndcode");
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 1;
        String dstEmailAddress = data.get(0);
        System.out.println(dstEmailAddress);
        Email email = new Email("luohaimin.com@qq.com", dstEmailAddress);
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0 ; i < 6; ++i) {
            code.append(String.valueOf(random.nextInt(10)));
        }
        codeMap.put(dstEmailAddress,code.toString());
        email.SendEmail(code.toString());
    }

    private void onUserLoginCallBack(MessageData messageData) {
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 2;
        MessageData reply = new MessageData();
        String sql = "select * from User where uID = " + Integer.valueOf(data.get(0));
        ResultSet rs = DataAccess.Query(sql);
        try {
            if (!rs.next()) {
                System.out.println("next");
                reply.setMessageType(MessageType.loginDeny);
                reply.getData().add("账户不存在");
                try {
                    send(reply);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            String pwd = rs.getString("uPwd");
            if (pwd.trim().equals(data.get(1).trim())) {
                reply.setMessageType(MessageType.userLoginAdmit);
                reply.getData().add(String.valueOf(rs.getInt("uID")));
                reply.getData().add(rs.getString("uName"));
                reply.getData().add(rs.getString("uPwd"));
                reply.getData().add(rs.getString("email"));
                reply.getData().add(rs.getString("uTele"));
                reply.getData().add(rs.getString("uType"));
                reply.getData().add(rs.getDate("rTime").toString());
                send(reply);
            } else {
                reply.setMessageType(MessageType.loginDeny);
                reply.getData().add("密码错误");
                send(reply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onAdminLoginCallBack(MessageData messageData) {
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 2;
        MessageData reply = new MessageData();
        String sql = "select * from Administrator where aID = " + data.get(0);
        ResultSet rs = DataAccess.Query(sql);
        try {
            if (!rs.next()) {
                System.out.println("next");
                reply.setMessageType(MessageType.loginDeny);
                reply.getData().add("账户不存在");
                send(reply);
                return;
            }
            String pwd = rs.getString("aPwd");
            if (pwd.trim().equals(data.get(1).trim())) {
                reply.setMessageType(MessageType.adminLoginAdmit);
                reply.getData().add(String.valueOf(rs.getInt("aID")));
                reply.getData().add(rs.getString("aName"));
                reply.getData().add(rs.getString("aPwd"));
                reply.getData().add(rs.getString("email"));
                reply.getData().add(rs.getString("aTele"));
                reply.getData().add(rs.getString("aType"));
                reply.getData().add(rs.getDate("rTime").toString());
                send(reply);
            } else {
                reply.setMessageType(MessageType.loginDeny);
                reply.getData().add("密码错误");
                send(reply);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
