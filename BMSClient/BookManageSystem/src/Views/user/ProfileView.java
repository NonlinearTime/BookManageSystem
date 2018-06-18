package Views.user;

import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleRole;
import sample.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProfileView implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private JFXTextField userID;

    @FXML
    private JFXTextField userName;

    @FXML
    private JFXTextField pwdField;

    @FXML
    private JFXTextField userJob;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField teleNum;

    @FXML
    private JFXTextField regDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        pwdField.setAccessibleRole(AccessibleRole.TEXT_FIELD);
    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void onEnterButtonCLicked() {
        stageController.closeStage(Main.UserProfileViewID);
    }

    public void setInfo(ArrayList<String> info) {
        assert info.size() >= 7;
        this.userID.setText(info.get(0));
        this.userName.setText(info.get(1));
        this.pwdField.setText(info.get(2));
        this.email.setText(info.get(3));
        this.teleNum.setText(info.get(4));
        this.userJob.setText(info.get(5));
        this.regDate.setText(info.get(6));
    }

    public void clearInfo() {
        userID.clear();
        userName.clear();
        pwdField.clear();
        userJob.clear();
        email.clear();
        teleNum.clear();
        regDate.clear();
    }
}
