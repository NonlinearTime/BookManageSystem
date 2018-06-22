package Views.administrator;

import Conponent.Connector;
import Conponent.MessageData;
import Conponent.MessageType;
import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerUserDetailDialogController implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private JFXTextField UserIDField;

    @FXML
    private JFXTextField UserNameField;

    @FXML
    private JFXTextField UserPwdField;

    @FXML
    private JFXTextField UserEMailField;

    @FXML
    private JFXTextField UserTeleField;

    @FXML
    private JFXTextField UserJobField;

    @FXML
    private JFXTextField UserRegisterTimeField;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    @FXML
    void onCancelButtonClicked(MouseEvent event) {
        stageController.closeStage(Main.ManagerUserDetailViewID);
    }

    @FXML
    void onChangeButtonCicked(MouseEvent event) {
        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.sqlReq);
        String sql = "update User set " +
                "uName = '" + UserNameField.getText().trim() + "', " +
                "uPwd = '" + UserPwdField.getText().trim() + "', "+
                "email = '" + UserEMailField.getText().trim() + "', "+
                "uTele = '" + UserTeleField.getText().trim() + "', " +
                "uType = '" + UserJobField.getText().trim() + "', "+
                "rTime = '" + UserRegisterTimeField.getText().trim() + "' " +
                "where uID = " + UserIDField.getText().trim();
        System.out.println(sql);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);
        popHintDialog("修改读者信息成功");
    }

    public void popHintDialog(String hintContent) {
        JFXAlert alert = new JFXAlert((Stage) UserIDField.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new javafx.scene.control.Label("提示"));
        layout.setBody(new Label(hintContent));

        JFXButton closeButton = new JFXButton("确定");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);

        alert.show();
    }

    public void setUserIDField(String userIDField) {
        UserIDField.setText(userIDField);
    }

    public void setUserNameField(String userNameField) {
        UserNameField.setText(userNameField);
    }

    public void setUserPwdField(String userPwdField) {
        UserPwdField.setText(userPwdField);
    }

    public void setUserEMailField(String userEMailField) {
        UserEMailField.setText(userEMailField);
    }

    public void setUserTeleField(String userTeleField) {
        UserTeleField.setText(userTeleField);
    }

    public void setUserJobField(String userJobField) {
        UserJobField.setText(userJobField);
    }

    public void setUserRegisterTimeField(String userRegisterTimeField) {
        UserRegisterTimeField.setText(userRegisterTimeField);
    }
}