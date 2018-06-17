package Views.login;

import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterDialogController implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private JFXTextField NameField;

    @FXML
    private JFXTextField PwdField;

    @FXML
    private JFXTextField DPwdField;

    @FXML
    private JFXTextField EmailField;

    @FXML
    private JFXTextField TeleField;

    @FXML
    private JFXTextField JobField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void onQuitButtonClicked() {
        stageController.closeStage(Main.LoginRegViewID);

    }

    public void onReguButtonClicked() {

    }
}
