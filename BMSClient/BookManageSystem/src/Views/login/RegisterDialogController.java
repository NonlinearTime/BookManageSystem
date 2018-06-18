package Views.login;

import Conponent.Connector;
import Conponent.Email;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterDialogController implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private JFXTextField NameField;

    @FXML
    private JFXPasswordField PwdField;

    @FXML
    private JFXPasswordField DPwdField;

    @FXML
    private JFXTextField EmailField;

    @FXML
    private JFXTextField TeleField;

    @FXML
    private JFXTextField JobField;

    @FXML
    private JFXButton regButton;

    @FXML
    private JFXButton quitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void onQuitButtonClicked() {
        stageController.closeStage(Main.LoginRegViewID);
    }

    public void onRegButtonClicked() {
        if (!PwdField.getText().trim().equals(DPwdField.getText().trim())) {
            popHintDialog("两次密码输入不一致，请重新输入！");
            PwdField.clear();
            DPwdField.clear();
            return;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(NameField.getText().trim());
        arrayList.add(PwdField.getText().trim());
        arrayList.add(EmailField.getText().trim());
        arrayList.add(TeleField.getText().trim());
        arrayList.add(JobField.getText().trim());
        MessageData messageData = new MessageData(MessageType.regReq, arrayList);
        for (int i = 0 ; i < arrayList.size() ; ++i) {
            System.out.println(arrayList.get(i));
        }
        Connector.getInstance().send(messageData);
    }

    public void popHintDialog(String hintContent) {
        JFXAlert alert = new JFXAlert((Stage) regButton.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new javafx.scene.control.Label("提示"));
        layout.setBody(new Label(hintContent));

        JFXButton closeButton = new JFXButton("确定");
        closeButton.setOnAction(event -> {
            alert.hideWithAnimation();
        });
        layout.setActions(closeButton);
        alert.setContent(layout);

        alert.show();
    }

    public void clearInfo() {
        NameField.clear();
        PwdField.clear();
        DPwdField.clear();
        EmailField.clear();
        TeleField.clear();
        JobField.clear();
    }
}
