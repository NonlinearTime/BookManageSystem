package Network;

import Conponent.*;

import java.awt.desktop.SystemSleepEvent;
import java.io.*;
import java.net.Socket;
import java.sql.*;
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
                break;
            case MessageType.sqlBookReq:
                onSqlBookReqCallBack(messageData);
                break;
            case MessageType.sqlBTypeReq:
                onSqlBTypeReqCallBack(messageData);
                break;
            case MessageType.sqlRentReq:
                onSqlRentReqCallBack(messageData);
                break;
            case MessageType.sqlFineReq:
                onSqlFineReqCallBack(messageData);
                break;
            case MessageType.rentReq:
                onRentReqCallBack(messageData);
                break;
            case MessageType.returnReq:
                onReturnReqCallBack(messageData);
                break;
            case MessageType.fineReq:
                onFineReqCallBack(messageData);
                break;
            default:
                break;
        }
    }

    private void onFineReqCallBack(MessageData messageData) {
        System.out.println("fine");
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 1;
        System.out.println(data);
        try {
            preparedStatement = DataAccess.getConnection().prepareStatement(
                    "insert into FineRecord (fineID)" +
                            "values (?)",Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setInt(1,Integer.valueOf(data.get(0)));
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onSqlFineReqCallBack(MessageData messageData) {
        assert messageData.getData().size() >= 1;
        System.out.println(messageData.getData().get(0));
        String sql = null;
        try {
            sql = new String(messageData.getData().get(0).getBytes("UTF-8"),"UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ResultSet rs =  DataAccess.Query(sql);
        MessageData reply = new MessageData();
        reply.setMessageType(MessageType.sqlFineReq);
        System.out.println(sql);

        try {
            while (rs.next()) {
                ArrayList<String> fine = new ArrayList<>();
                ResultSet trs = DataAccess.Query1("select bID from BorrowReg where rID =" + rs.getInt("rID"));
                trs.next();
                int bID = trs.getInt("bID");
                trs = DataAccess.Query1("select bName from Book where bID =" + bID);
                trs.next();
                String bName = trs.getString("bName");
                System.out.println(bName);

                fine.add(String.valueOf(rs.getInt("fID")));
                fine.add(String.valueOf(rs.getInt("rID")));
                fine.add(bName);
                fine.add(String.valueOf(rs.getInt("fMount")));
                fine.add(new Date(rs.getTimestamp("fDate").getTime()).toString());
                fine.add(String.valueOf(rs.getBoolean("isSolved")));
                reply.getDataList().add(fine);
                System.out.println(reply.getDataList().size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            send(reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onSqlRentReqCallBack(MessageData messageData) {
        assert messageData.getData().size() >= 1;
        System.out.println(messageData.getData().get(0));
        String sql = null;
        try {
            sql = new String(messageData.getData().get(0).getBytes("UTF-8"),"UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ResultSet rs =  DataAccess.Query(sql);
        MessageData reply = new MessageData();
        reply.setMessageType(MessageType.sqlRentReq);
        System.out.println(sql);

        try {
            while (rs.next()) {
                ArrayList<String> rent = new ArrayList<>();
                ResultSet trs = DataAccess.Query1("select bName from Book where bID =" + rs.getInt("bID"));
                trs.next();
                String bName = trs.getString("bName");
                System.out.println(bName);

                rent.add(String.valueOf(rs.getInt("rID")));
                rent.add(String.valueOf(rs.getInt("bID")));
                rent.add(bName);
                rent.add(new Date(rs.getTimestamp("rDate").getTime()).toString());
                rent.add(new Date(rs.getTimestamp("rbDate").getTime()).toString());
                rent.add(String.valueOf(rs.getBoolean("isBack")));
                reply.getDataList().add(rent);
                System.out.println(reply.getDataList().size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            send(reply);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void onReturnReqCallBack(MessageData messageData) {
        System.out.println("return");
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 4;
        System.out.println(data);
        try {
            preparedStatement = DataAccess.getConnection().prepareStatement(
                    "insert into ReturnRecord (RentID, BookID, UserID, RetDate)" +
                            "values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setInt(1,Integer.valueOf(data.get(0)));
            preparedStatement.setInt(2,Integer.valueOf(data.get(1)));
            preparedStatement.setInt(3,Integer.valueOf(data.get(2)));
            preparedStatement.setTimestamp(4,Timestamp.valueOf(data.get(3)));
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onRentReqCallBack(MessageData messageData) {
        System.out.println("rent");
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 4;
        System.out.println(data);
        try {
            preparedStatement = DataAccess.getConnection().prepareStatement(
                    "insert into RentRecord (BookID, UserID, RentDate, ReturnDate)" +
                            "values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS
            );

            preparedStatement.setInt(1,Integer.valueOf(data.get(0)));
            preparedStatement.setInt(2,Integer.valueOf(data.get(1)));
            preparedStatement.setTimestamp(3,Timestamp.valueOf(data.get(2)));
            preparedStatement.setTimestamp(4,Timestamp.valueOf(data.get(3)));
//            preparedStatement.executeUpdate();
//            preparedStatement.execute();
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onSqlBookReqCallBack(MessageData messageData) {
        assert messageData.getData().size() >= 1;
        System.out.println(messageData.getData().get(0));
        String sql = null;
        try {
            sql = new String(messageData.getData().get(0).getBytes("UTF-8"),"UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ResultSet rs =  DataAccess.Query(sql);
        MessageData reply = new MessageData();
        reply.setMessageType(MessageType.sqlBookReq);
        System.out.println(sql);

        try {
            while (rs.next()) {
                ArrayList<String> book = new ArrayList<>();
                book.add(String.valueOf(rs.getInt("bID")));
                book.add(rs.getString("bName"));
                book.add(rs.getString("bType"));
                book.add(rs.getString("aName"));
                book.add(rs.getString("pubName"));
                book.add(rs.getString("uDate"));
                book.add(String.valueOf(rs.getInt("totNum")));
                book.add(String.valueOf(rs.getInt("rNum")));
                book.add(rs.getString("price"));
                book.add(String.valueOf(rs.getDouble("bScore")));
                book.add(String.valueOf(rs.getInt("reviews")));
                reply.getDataList().add(book);
                System.out.println(reply.getDataList().size());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            send(reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onSqlBTypeReqCallBack(MessageData messageData) {
        String sql = "select distinct bType from Book";
        ResultSet rs = DataAccess.Query(sql);
        MessageData reply = new MessageData();
        reply.setMessageType(MessageType.sqlBTypeReq);
        try {
            while(rs.next()) {
                reply.getData().add(rs.getString("bType"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(reply.getData().size());
            send(reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onSqlReqCallBack(MessageData messageData) {
        assert messageData.getData().size() >= 1;
        ResultSet rs =  DataAccess.Query(messageData.getData().get(0));
        MessageData reply = new MessageData();
        reply.setMessageType(MessageType.sqlReq);

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
