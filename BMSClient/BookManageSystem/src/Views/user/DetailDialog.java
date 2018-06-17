package Views.user;

import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailDialog implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    private Label label6;

    @FXML
    private Label label7;

    @FXML
    private Label label8;

    @FXML
    private Label label9;

    @FXML
    private Label label10;

    @FXML
    private Label label11;

    @FXML
    private Label label12;

    @FXML
    private JFXButton enterBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public Label getLabel1() {return label1;}
    public Label getLabel2() {return label2;}
    public Label getLabel3() {return label3;}
    public Label getLabel4() {return label4;}
    public Label getLabel5() {return label5;}
    public Label getLabel6() {return label6;}
    public Label getLabel7() {return label7;}
    public Label getLabel8() {return label8;}
    public Label getLabel9() {return label9;}
    public Label getLabel10() {return label10;}
    public Label getLabel11() {return label11;}
    public Label getLabel12() {return label12;}
}
