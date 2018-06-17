package Views.login;

import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Paint;
import sample.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginDialogController implements ControlledStage, Initializable {
    private StageController loginDialogController;


    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton userIcon;

    @FXML
    private JFXTextField userText;

    @FXML
    private JFXButton pwdIcon;

    @FXML
    private JFXPasswordField pwdText;


    @FXML
    private JFXCheckBox readerCheckBox;

    @FXML
    private JFXCheckBox adminCheckBox;

    @Override
    public void setStageController(StageController stageController) {
        loginDialogController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onLoginButtonClicked() {
        if (readerCheckBox.isSelected() && !adminCheckBox.isSelected())
            loginDialogController.setStage(Main.UserViewID, Main.LoginDialogID);
        else if (adminCheckBox.isSelected() && !readerCheckBox.isSelected())
            loginDialogController.setStage(Main.ManagerViewID,Main.LoginDialogID);
    }

    @FXML
    void readerCheckBoxChanged() {
        if (readerCheckBox.isSelected()) {
            adminCheckBox.setSelected(false);
        }
    }

    @FXML
    void adminCheckBoxChanged() {
        if (adminCheckBox.isSelected()) {
            readerCheckBox.setSelected(false);
        }
    }

    @FXML
    void onRegButtonClicked() {
        System.out.println("register");
        loginDialogController.setStage(Main.LoginRegViewID);
    }

    @FXML
    void onFndBackButtonClicked() {
        System.out.println("Find pwd");
        loginDialogController.setStage(Main.LoginFndViewID);
    }
}
