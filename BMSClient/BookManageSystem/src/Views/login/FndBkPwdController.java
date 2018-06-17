package Views.login;

import StageController.ControlledStage;
import StageController.StageController;
import javafx.fxml.Initializable;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class FndBkPwdController implements ControlledStage, Initializable {
    private StageController stageController;

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

    }

    public void onGetCodeClicked() {

    }
}
