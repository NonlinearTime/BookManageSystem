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
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginDialogController implements ControlledStage, Initializable {
    private StageController loginDialogController;


    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton userIcon;

    @FXML
    private JFXTextField userText;

    @FXML
    private JFXButton pwdIcon;

    @FXML
    private JFXPasswordField pwdText;


    @FXML
    private JFXCheckBox readerCheckBox;

    @FXML
    private JFXCheckBox adminCheckBox;

    @Override
    public void setStageController(StageController stageController) {
        loginDialogController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onLoginButtonClicked() {
        MessageData messageData = new MessageData();
        messageData.getData().add(userText.getText().trim());
        messageData.getData().add(pwdText.getText().trim());
        if (readerCheckBox.isSelected() && !adminCheckBox.isSelected()) {
            messageData.setMessageType(MessageType.userLoginReq);
            Connector.getInstance().send(messageData);
        }
        else if (adminCheckBox.isSelected() && !readerCheckBox.isSelected()) {
            messageData.setMessageType(MessageType.adminLoginReq);
            Connector.getInstance().send(messageData);
        } else popHintDialog("Hello, 罗海旻 ^_^");
    }

    @FXML
    void readerCheckBoxChanged() {
        if (readerCheckBox.isSelected()) {
            adminCheckBox.setSelected(false);
        }
    }

    @FXML
    void adminCheckBoxChanged() {
        if (adminCheckBox.isSelected()) {
            readerCheckBox.setSelected(false);
        }
    }

    @FXML
    void onRegButtonClicked() {
        System.out.println("register");
        loginDialogController.setStage(Main.LoginRegViewID);
    }

    @FXML
    void onFndBackButtonClicked() {
        System.out.println("Find pwd");
        loginDialogController.setStage(Main.LoginFndViewID);
    }

    public void popHintDialog(String hintContent) {
        JFXAlert alert = new JFXAlert((Stage) loginButton.getScene().getWindow());
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
}
