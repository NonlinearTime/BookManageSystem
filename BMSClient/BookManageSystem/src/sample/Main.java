package sample;

import StageController.StageController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static String LoginDialogID = "LoginDialog";
    public static String LoginDialogRes = "../Views/login/LoginDialog.fxml";
    public static String ManagerViewID = "ManagerView";
    public static String ManagerViewRes = "../Views/administrator/ManagerView.fxml";
    public static String UserViewID = "UserView";
    public static String UserViewRes = "../Views/user/UserView.fxml";

    public static String LoginRegViewID = "LoginRegView";
    public static String LoginRegViewRes = "../Views/login/RegisterDialog.fxml";
    public static String LoginFndViewID = "LoginRndView";
    public static String LoginFndViewRes = "../Views/login/FndBkPwdDialog.fxml";



    public static String UserOperationDialogID = "UserOpDialog";
    public static String UserOperationDialogRes = "../Views/user/OperationDialog.fxml";
    public static String UserDetailDialogID = "UserDetailDialog";
    public static String UserDetailDialodRes = "../Views/user/DetailDialog.fxml";
    public static String UserProfileViewID = "UserProfileView";
    public static String UserProfileViewRes = "../Views/user/ProfileView.fxml";
    public static String UserAltpwdDialogID = "UserAltpwdDialog";
    public static String UserAltpwdDialogRes = "../Views/user/UserAltpwdDialog.fxml";

    private StageController stageController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stageController = new StageController();
        stageController.setPrimaryStage("primaryStage",primaryStage);

        stageController.loadStage(LoginDialogID,LoginDialogRes);
        stageController.loadStage(UserViewID,UserViewRes);
        stageController.loadStage(ManagerViewID,ManagerViewRes);

        stageController.loadStage(LoginRegViewID,LoginRegViewRes);
        stageController.loadStage(LoginFndViewID,LoginFndViewRes);
        stageController.getStage(LoginRegViewID).setTitle("注册账号");
        stageController.getStage(LoginFndViewID).setTitle("找回密码");

        stageController.loadStage(UserOperationDialogID, UserOperationDialogRes);
        stageController.loadStage(UserDetailDialogID, UserDetailDialodRes);

        stageController.loadStage(UserProfileViewID, UserProfileViewRes);
        stageController.loadStage(UserAltpwdDialogID,UserAltpwdDialogRes);

        stageController.getStage(LoginDialogID).setTitle("欢迎登陆图书管理系统");
        stageController.getStage(UserViewID).setTitle("欢迎使用图书管理系统");

        stageController.setStage(LoginDialogID);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
