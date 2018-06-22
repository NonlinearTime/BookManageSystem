package Views.administrator;

import Conponent.Connector;
import Conponent.MessageData;
import Conponent.MessageType;
import StageController.ControlledStage;
import StageController.StageController;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.awt.print.Book;
import java.net.URL;
import java.security.PublicKey;
import java.util.ResourceBundle;

public class ManageBookOpDialogController implements ControlledStage, Initializable {
    private StageController stageController;

    @FXML
    private JFXTextField BookIDField;

    @FXML
    private JFXTextField BookNameField;

    @FXML
    private JFXTextField BookClassField;

    @FXML
    private JFXTextField BookAuthorField;

    @FXML
    private JFXTextField BookPubField;

    @FXML
    private JFXTextField BookPubDateField;

    @FXML
    private JFXTextField BookTotNumField;

    @FXML
    private JFXTextField BookLeftNumField;

    @FXML
    private JFXTextField BookPriceField;

    @FXML
    private JFXTextField BookGradeField;

    @FXML
    private JFXTextField BookReviewsField;


    public ManageBookOpDialogController() {
    }

    @Override
    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onCancelrButtonClicked() {
        stageController.closeStage(Main.ManagerBookDetailViewID);
    }

    public void onChangeButtonClicked() {
        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.sqlReq);
        String sql = "update Book set " +
                "bName = '" + BookNameField.getText().trim() + "', " +
                "bType = '" + BookClassField.getText().trim() + "', "+
                "aName = '" + BookAuthorField.getText().trim() + "', "+
                "pubName = '" + BookPubField.getText().trim() + "', " +
                "uDate = '" + BookPubDateField.getText().trim() + "', "+
                "totNum = '" + BookTotNumField.getText().trim() + "', " +
                "rNum = '" + BookLeftNumField.getText().trim() + "', " +
                "price = '" + BookPriceField.getText().trim()+ "', " +
                "bScore = '" + BookPriceField.getText().trim() + "', " +
                "reviews = '" + BookReviewsField.getText().trim() + "'" +
                "where bID = " + BookIDField.getText().trim();
        System.out.println(sql);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);
        popHintDialog("修改图书信息成功");
    }

    public void clearInfo() {
        BookIDField.clear();
        BookNameField.clear();
        BookAuthorField.clear();
        BookClassField.clear();
        BookPubField.clear();
        BookPubDateField.clear();
        BookTotNumField.clear();
        BookLeftNumField.clear();
        BookGradeField.clear();
        BookReviewsField.clear();
        BookPriceField.clear();
    }

    public void setBookIDField(String bookIDField) {
        BookIDField.setText(bookIDField);
    }

    public void setBookNameField(String bookNameField) {
        BookNameField.setText(bookNameField);
    }

    public void setBookClassField(String bookClassField) {
        BookClassField.setText(bookClassField);
    }

    public void setBookAuthorField(String bookAuthorField) {
        BookAuthorField.setText(bookAuthorField);
    }

    public void setBookPubField(String bookPubField) {
        BookPubField.setText(bookPubField);
    }

    public void setBookPubDateField(String bookPubDateField) {
        BookPubDateField.setText(bookPubDateField);
    }

    public void setBookPriceField(String bookPriceField) {
        BookPriceField.setText(bookPriceField);
    }

    public void setBookGradeField(String bookGradeField) {
        BookGradeField.setText(bookGradeField);
    }

    public void setBookReviewsField(String bookReviewsField) {
        BookReviewsField.setText(bookReviewsField);
    }

    public void setBookTotNumField(String bookTotNumField) {
        BookTotNumField.setText(bookTotNumField);
    }

    public void setBookLeftNumField(String bookLeftNumField) {
        BookLeftNumField.setText(bookLeftNumField);
    }

    public void popHintDialog(String hintContent) {
        JFXAlert alert = new JFXAlert((Stage) BookIDField.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new javafx.scene.control.Label("提示"));
        layout.setBody(new Label(hintContent));

        JFXButton closeButton = new JFXButton("确定");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);

        alert.show();
    }
}
