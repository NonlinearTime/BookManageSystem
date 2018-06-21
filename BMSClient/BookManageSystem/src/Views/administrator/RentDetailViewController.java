package Views.administrator;

import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RentDetailViewController implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private JFXTextField UserIDField;

    @FXML
    private JFXTextField UserNameField;

    @FXML
    private JFXTextField RentIDField;

    @FXML
    private JFXTextField RentNumField;

    @FXML
    private JFXTextField UnreturnBooksField;

    @FXML
    private JFXTextField FineMountField;

    @FXML

    @Override
    public void setStageController(StageController stageController) {this.stageController = stageController;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onEnterButtonClicked() {
        stageController.closeStage(Main.ManagerRentDetailViewID);
    }

    public void setUnreturnBooksField(ArrayList<String> books) {
        StringBuilder bookNames = new StringBuilder();
        for (String book: books
             ) {
            bookNames.append(book);
            bookNames.append(" ");
        }
        UnreturnBooksField.setText(bookNames.toString().trim());
    }

    public void setRentIDField(String rentid) {
        RentIDField.setText(rentid);
    }

    public void setRentNumField(String rentNum) {
        RentNumField.setText(rentNum);
    }

    public void setUserIDField(String userId) {
        UserIDField.setText(userId);
    }

    public void setUserNameField(String userName) {
        UserNameField.setText(userName);
    }

    public void setFineMountField(String fineMount) {
        FineMountField.setText(fineMount);
    }

    public void clearInfo() {
        RentIDField.clear();
        RentNumField.clear();
        UserNameField.clear();
        UserIDField.clear();
        UserNameField.clear();
        FineMountField.clear();
    }
}
