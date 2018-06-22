package Views.administrator;

import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import sample.Main;

import javax.lang.model.element.QualifiedNameable;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageFineDialogController implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private JFXTextField FineIDField;

    @FXML
    private JFXTextField RentIDField;

    @FXML
    private JFXTextField UserIDName;

    @FXML
    private JFXTextField UserNameFIeld;

    @FXML
    private JFXTextField FineMountField;

    @FXML
    private JFXTextField FineDateField;

    public void onEnterButtonClicked() {
        stageController.closeStage(Main.ManagerFineDetailViewID);
    }

    @Override
    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setUserIDName(String userIDName) {
        UserIDName.setText(userIDName);
    }

    public void setUserNameFIeld(String userNameFIeld) {
        UserNameFIeld.setText(userNameFIeld);
    }

    public void setRentIDField(String rentIDField) {
        RentIDField.setText(rentIDField);
    }

    public void setFineIDField(String fineIDField) {
        FineIDField.setText(fineIDField);
    }

    public void setFineDateField(String fineDateField) {
        FineDateField.setText(fineDateField);
    }

    public void setFineMountField(String fineMountField) {
        FineMountField.setText(fineMountField);
    }

    public void clearInfo() {
        UserNameFIeld.clear();
        UserIDName.clear();
        RentIDField.clear();
        FineMountField.clear();
        FineIDField.clear();
        FineDateField.clear();
    }
}
