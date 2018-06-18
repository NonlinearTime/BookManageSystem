package Views.user;

import Conponent.Connector;
import Conponent.MessageData;
import Conponent.MessageType;
import StageController.ControlledStage;
import StageController.StageController;
import Views.data.DataContainer;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.DataTruncation;
import java.util.ResourceBundle;

public class UserAltpwdDialog implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private JFXTextField pwdField;

    @FXML
    private JFXTextField newPwdField;

    @FXML
    private JFXTextField dPwdField;

    @FXML
    private JFXTextField codeField;

    @FXML
    private JFXButton codeButton;

    @FXML
    private JFXButton enterButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void onEnterButtonClicked() {
        assert DataContainer.profile.size() >= 7;
        String userID = DataContainer.profile.get(0);
        String inputPwd = pwdField.getText().trim();
        String newPwd = newPwdField.getText().trim();
        String code = codeField.getText().trim();

        if (!inputPwd.equals(DataContainer.profile.get(2))) {
            popHintDialog("输入账户密码错误，请重新输入");
            pwdField.clear();
            return;
        }

        if (!newPwd.equals(dPwdField.getText().trim())) {
            popHintDialog("两次输入密码不一致，请重新输入");
            newPwdField.clear();
            dPwdField.clear();
            return;
        }


        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.altPwd);
        messageData.getData().add(userID);
        messageData.getData().add(newPwd);
        messageData.getData().add(DataContainer.profile.get(3));
        messageData.getData().add(code);

        Connector.getInstance().send(messageData);

    }

    public void onCodeButtonClicked() {
        assert DataContainer.profile.size() >= 7;
        String email = DataContainer.profile.get(3);

        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.fndCodeReq);
        messageData.getData().add(email);

        Connector.getInstance().send(messageData);
    }

    public void popHintDialog(String hintContent) {
        JFXAlert alert = new JFXAlert((Stage) enterButton.getScene().getWindow());
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

    public void clearInfo() {
        pwdField.clear();
        newPwdField.clear();
        codeField.clear();
        dPwdField.clear();
    }

    public String getNewPwd() {
        return newPwdField.getText().trim();
    }
}
