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
import java.util.ResourceBundle;

public class ProfileView implements ControlledStage, Initializable {
    private StageController stageController;


    @FXML
    private JFXTextField pwdField;

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
}
