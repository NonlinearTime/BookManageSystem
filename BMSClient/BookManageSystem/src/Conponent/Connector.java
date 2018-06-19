package Conponent;

import Network.Client;
import Network.Server;
import StageController.StageController;
import Views.administrator.ManagerViewController;
import Views.data.DataContainer;
import Views.login.FndBkPwdController;
import Views.login.LoginDialogController;
import Views.login.RegisterDialogController;
import Views.user.UserAltpwdDialog;
import Views.user.UserViewController;
import javafx.application.Platform;
import sample.Main;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static Conponent.MessageData.*;
import static Conponent.MessageType.regAdmit;

import Conponent.MessageType;

import javax.xml.crypto.Data;

public class Connector {
    private Client client = new Client("127.0.0.1",8888,this::CallBack);
    private StageController stageController;

    private static Connector ourInstance = new Connector();

    public static Connector getInstance() {
        return ourInstance;
    }

    private Connector() {
    }

    public void start() {
        client.startConnection();
    }

    public void close() {
        try {
            client.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Serializable data) {
        try {
            client.send(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    private void CallBack(Serializable data) {
        MessageData messageData = (MessageData) data;
        switch (messageData.getMessageType()) {
            case MessageType.regAdmit:
                onRegAdmitCallBack(messageData);
                break;
            case MessageType.regDeny:
                onRegDenyCallBack(messageData);
                break;
            case MessageType.fndAdmit:
                onFndAdmitCallBack(messageData);
                break;
            case MessageType.fndDeny:
                onFndDenyCallBack(messageData);
                break;
            case MessageType.userLoginAdmit:
                onUserLoginAdmitCallBack(messageData);
                break;
            case MessageType.adminLoginAdmit:
                onAdminLoginAdmitCallBack(messageData);
                break;
            case MessageType.loginDeny:
                onLoginDenyCallBack(messageData);
                break;
            case MessageType.altAdmit:
                onAltAmitCallBack(messageData);
                break;
            case MessageType.altDeny:
                onAltDenyCallBack(messageData);
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
            default:
                break;
        }
    }

    private void onSqlFineReqCallBack(MessageData messageData) {
        ArrayList<ArrayList<String>> fines = messageData.getDataList();
        DataContainer.fines = fines;
        UserViewController userViewController = (UserViewController) stageController.getController(Main.UserViewID);
        Platform.runLater(() -> {
            for (ArrayList<String> fine: fines) {
                userViewController.addFineTableItem(fine);
            }
        });
    }

    private void onSqlRentReqCallBack(MessageData messageData) {
        ArrayList<ArrayList<String>> rents = messageData.getDataList();
        DataContainer.rents = rents;
        UserViewController userViewController = (UserViewController) stageController.getController(Main.UserViewID);
        Platform.runLater(() -> {
            for (ArrayList<String> rent: rents) {
                userViewController.addRentTableItem(rent);
            }
        });
    }

    private void onSqlBookReqCallBack(MessageData messageData) {
        ArrayList<ArrayList<String>> books = messageData.getDataList();
        DataContainer.books = books;
        UserViewController userViewController = (UserViewController) stageController.getController(Main.UserViewID);
        Platform.runLater(() -> {
            for (ArrayList<String> book: books) {
                userViewController.addBookTableItem(book);
            }
        });
    }

    private void onSqlBTypeReqCallBack(MessageData messageData) {
        ArrayList<String> data = messageData.getData();
        UserViewController userViewController = (UserViewController) stageController.getController(Main.UserViewID);
        Platform.runLater(() -> {
            userViewController.setBookClassComboList(data);
        });
    }

    private void onSqlReqCallBack(MessageData messageData) {


    }

    private void onAltDenyCallBack(MessageData messageData) {
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 1;
        Platform.runLater(() -> {
            UserAltpwdDialog userAltpwdDialog = (UserAltpwdDialog) stageController.getController(Main.UserAltpwdDialogID);
            userAltpwdDialog.popHintDialog("密码修改失败！" + data.get(0));
        });
    }

    private void onAltAmitCallBack(MessageData messageData) {
        Platform.runLater(() -> {
            UserAltpwdDialog userAltpwdDialog = (UserAltpwdDialog) stageController.getController(Main.UserAltpwdDialogID);
            userAltpwdDialog.popHintDialog("密码修改成功!");
            ArrayList<String> prof = new ArrayList<>();
            prof.add(DataContainer.profile.get(0));
            prof.add(DataContainer.profile.get(1));
            prof.add(userAltpwdDialog.getNewPwd());
            prof.add(DataContainer.profile.get(3));
            prof.add(DataContainer.profile.get(4));
            prof.add(DataContainer.profile.get(5));
            prof.add(DataContainer.profile.get(6));
            DataContainer.profile = prof;
            userAltpwdDialog.clearInfo();
        });
    }


    private void onRegAdmitCallBack(MessageData messageData){
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 1;
        Platform.runLater(() -> {
            RegisterDialogController registerDialogController = (RegisterDialogController) stageController.getController(Main.LoginRegViewID);
            registerDialogController.popHintDialog("注册账户成功！您的账号是：" + data.get(0) +", 请您牢记账号，这将成为您的登录依据！");
            registerDialogController.clearInfo();
        });
    }

    private void onRegDenyCallBack(MessageData messageData) {
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 1;
        Platform.runLater(() -> {
            RegisterDialogController registerDialogController = (RegisterDialogController) stageController.getController(Main.LoginRegViewID);
            registerDialogController.popHintDialog("注册账户失败！失败原因：" + data.get(0));
        });
    }

    private void onFndAdmitCallBack(MessageData messageData) {
        Platform.runLater(() -> {
            FndBkPwdController fndBkPwdController = (FndBkPwdController) stageController.getController(Main.LoginFndViewID);
            fndBkPwdController.popHintDialog("重置密码成功！");
            fndBkPwdController.clearInfo();
        });
    }

    private void onFndDenyCallBack(MessageData messageData) {
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 1;
        Platform.runLater(() -> {
            FndBkPwdController fndBkPwdController = (FndBkPwdController) stageController.getController(Main.LoginFndViewID);
            fndBkPwdController.popHintDialog("重置密码失败！失败原因：" + data.get(0));
        });
    }

    private void onUserLoginAdmitCallBack(MessageData messageData) {
        assert messageData.getData().size() >= 7;
        Platform.runLater(() -> {
            stageController.setStage(Main.UserViewID, Main.LoginDialogID);
            DataContainer.profile = messageData.getData();
            UserViewController userViewController = (UserViewController) stageController.getController(Main.UserViewID);
            userViewController.setWelcomeLabel("欢迎你，" + DataContainer.profile.get(1));
        });
    }

    private void onAdminLoginAdmitCallBack(MessageData messageData) {
        assert messageData.getData().size() >= 7;
        Platform.runLater(() -> {
            stageController.setStage(Main.ManagerViewID, Main.LoginDialogID);
            DataContainer.profile = messageData.getData();
            ManagerViewController managerViewController = (ManagerViewController) stageController.getController(Main.ManagerViewID);
            managerViewController.setWelcomeLabel("欢迎你，" + DataContainer.profile.get(1));

        });
    }

    private void onLoginDenyCallBack(MessageData messageData) {
        ArrayList<String> data = messageData.getData();
        assert data.size() >= 1;
        Platform.runLater(() -> {
            LoginDialogController loginDialogController = (LoginDialogController) stageController.getController(Main.LoginDialogID);
            loginDialogController.popHintDialog("登录失败！失败原因：" + data.get(0));
        });
    }
}
