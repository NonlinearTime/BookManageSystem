package Conponent;

import Network.Client;
import Network.Server;
import StageController.StageController;
import Views.login.FndBkPwdController;
import Views.login.LoginDialogController;
import Views.login.RegisterDialogController;
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
            case MessageType.loginDeny:
                onLoginDenyCallBack(messageData);
                break;
            default:
                break;
        }
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
        Platform.runLater(() -> {
            stageController.setStage(Main.UserViewID, Main.LoginDialogID);
        });
    }

    private void onAdminLoginAdmitCallBack(MessageData messageData) {
        Platform.runLater(() -> {
            stageController.setStage(Main.ManagerViewID, Main.LoginDialogID);
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
