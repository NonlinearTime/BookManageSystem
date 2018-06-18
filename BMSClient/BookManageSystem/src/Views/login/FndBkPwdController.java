package Views.login;

import Conponent.Connector;
import Conponent.MessageData;
import Conponent.MessageType;
import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class FndBkPwdController implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private JFXButton fndBckButton;

    @FXML
    private JFXButton quitButton;

    @FXML
    private JFXTextField userField;

    @FXML
    private JFXPasswordField pwdField;

    @FXML
    private JFXPasswordField dPwdField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXTextField codeField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void onQuitButtonClicked() {
        stageController.closeStage(Main.LoginFndViewID);
    }

    public void onFndBckButtonClicked() {
        if (!pwdField.getText().trim().equals(dPwdField.getText().trim())) {
            popHintDialog("两次密码输入不一致，请重新输入！");
            pwdField.clear();
            dPwdField.clear();
            return;
        }
        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.fndReq);
        messageData.getData().add(userField.getText().trim());
        messageData.getData().add(pwdField.getText().trim());
        messageData.getData().add(emailField.getText().trim());
        messageData.getData().add(codeField.getText().trim());
        Connector.getInstance().send(messageData);
    }

    public void onGetCodeClicked() {
        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.fndCodeReq);
        messageData.getData().add(emailField.getText().trim());
        Connector.getInstance().send(messageData);
    }
    public void popHintDialog(String hintContent) {
        JFXAlert alert = new JFXAlert((Stage) fndBckButton.getScene().getWindow());
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

    public void clearInfo () {
        userField.clear();
        pwdField.clear();
        dPwdField.clear();
        emailField.clear();
        codeField.clear();
    }

}
