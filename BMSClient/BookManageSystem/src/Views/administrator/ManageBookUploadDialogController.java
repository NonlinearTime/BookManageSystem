package Views.administrator;

import Conponent.Connector;
import Conponent.MessageData;
import Conponent.MessageType;
import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageBookUploadDialogController implements ControlledStage, Initializable {
    private StageController stageController;


    @FXML
    private JFXTextField BookIDField;

    @FXML
    private JFXTextField BookNameField;

    @FXML
    private JFXComboBox<String> BookClassComboList;

    @FXML
    private JFXTextField BookAuthorField;

    @FXML
    private JFXTextField BookPubField;

    @FXML
    private JFXDatePicker BookPubDatePicker;

    @FXML
    private JFXTextField BookTotNumField;

    @FXML
    private JFXTextField BookLeftNumField;

    @FXML
    private JFXTextField BookPriceField;


    public ManageBookUploadDialogController() {
    }

    @Override
    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onCancelrButtonClicked() {
        stageController.closeStage(Main.ManagerBookUploadViewID);
    }

    public void onChangeButtonClicked() {
        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.sqlReq);
        String sql = "insert into Book (bID, bName, bType, aName, pubName, uDate, totNum, rNum, price) values" +
                "(" + BookIDField.getText().trim() + ", " +
                "'" + BookNameField.getText().trim() + "', " +
                "'" + BookClassComboList.getEditor().getText().trim() + "', " +
                "'" + BookAuthorField.getText().trim() + "', " +
                "'" + BookPubField.getText().trim() + "', " +
                "'" + BookPubDatePicker.getValue().toString().trim() + "', " +
                BookTotNumField.getText().trim() + ", " +
                BookLeftNumField.getText().trim() + ", " +
                BookPriceField.getText().trim() + ")";
        System.out.println(sql);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);
        popHintDialog("上架图书成功");
    }

    public void clearInfo() {
        BookIDField.clear();
        BookNameField.clear();
        BookAuthorField.clear();
        BookClassComboList.getEditor().clear();
        BookPubField.clear();
        BookPubDatePicker.getEditor().clear();
        BookTotNumField.clear();
        BookLeftNumField.clear();
        BookPriceField.clear();
    }
    public void popHintDialog(String hintContent) {
        JFXAlert alert = new JFXAlert((Stage) BookIDField.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("提示"));
        layout.setBody(new Label(hintContent));

        JFXButton closeButton = new JFXButton("确定");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);

        alert.show();
    }

    public void setBookClassComboList(ArrayList<String> items) {
        BookClassComboList.getItems().clear();
        for (String item: items) {
            BookClassComboList.getItems().add(item);
        }
    }

}
