package Views.administrator;

import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageBackDialogController implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private JFXTextField RentIDField;

    @FXML
    private JFXTextField UserIDField;

    @FXML
    private JFXTextField UserNameField;

    @FXML
    private JFXTextField BookIDField;

    @FXML
    private JFXTextField BookNameField;

    @FXML
    private JFXTextField RetDateField;

    @FXML
    private JFXTextField ReturnDateField;


    @Override
    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setRentIDField(String rentID) {
        RentIDField.setText(rentID);
    }

    public void setUserIDField(String userID) {
        UserIDField.setText(userID);
    }

    public void setUserNameField(String userName) {
        UserNameField.setText(userName);
    }

    public void setBookIDField(String bookID) {
        BookIDField.setText(bookID);
    }

    public void setBookNameField(String bookName) {
        BookNameField.setText(bookName);
    }

    public void setRetDateField(String retDate) {
        RetDateField.setText(retDate);
    }

    public void setReturnDateField(String returnDate) {
        ReturnDateField.setText(returnDate);
    }

    public void clearInfo() {
        RentIDField.clear();
        UserNameField.clear();
        UserIDField.clear();
        BookIDField.clear();
        BookNameField.clear();
        RetDateField.clear();
        ReturnDateField.clear();
    }

    public void onEnterButtonClicked() {
        stageController.closeStage(Main.ManagerBackDetailViewID);
    }
}
