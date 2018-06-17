package Views.user;

import StageController.ControlledStage;
import StageController.StageController;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class OperationDialog implements ControlledStage, Initializable {
    private StageController stageController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }


}
