package Views.user;

import Conponent.Connector;
import Conponent.MessageData;
import Conponent.MessageType;
import StageController.ControlledStage;
import StageController.StageController;
import Views.Conponent.ListItem;
import Views.data.DataContainer;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import sample.Main;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserViewController implements ControlledStage, Initializable {
    private StageController userViewController;
    private AnchorPane currentTab;
    private HashMap<ListItem, AnchorPane> tabController = new HashMap<>();
    private ObservableList<BookTableItem> books =  FXCollections.observableArrayList();
    private ObservableList<RentTableItem> rents =  FXCollections.observableArrayList();
    private ObservableList<TimeTableItem> fines =  FXCollections.observableArrayList();
    private Date date = new Date();
    private Timestamp timestamp;

    @FXML
    private AnchorPane BookTab;

    @FXML
    private AnchorPane RentTab;

    @FXML
    private AnchorPane TimeTab;

    @FXML
    private JFXToolbar infoToolBar;

    @FXML
    private JFXListView<ListItem> itemListView;

    @FXML
    private JFXTreeTableView<BookTableItem> bookTable;

    @FXML
    private JFXTreeTableView<RentTableItem> rentTable;

    @FXML
    private JFXTreeTableView<TimeTableItem> fineTable;

    @FXML
    private JFXButton profileButton;

    @FXML
    private JFXButton quitButton;

    @FXML
    private JFXButton altpwdButton;

    @FXML
    private JFXTextField bookIDField;

    @FXML
    private JFXTextField bookNameField;

    @FXML
    private JFXComboBox<String> bookClassComboList;

    @FXML
    private JFXTextField authorTield;

    @FXML
    private JFXTextField pubField;

    @FXML
    private JFXButton bookSearchButton;

    @FXML
    private JFXTextField rentIdField;

    @FXML
    private JFXTextField rentBookIDField;

    @FXML
    private JFXTextField fineIDField;

    @FXML
    private JFXTextField fineRentIDField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label timeLabel;



    private ObservableList<ListItem> listView = FXCollections.observableArrayList();


    @Override
    public void setStageController(StageController stageController) {
        userViewController = stageController;
    }

    class BookTableItem extends RecursiveTreeObject<BookTableItem> {
        StringProperty  bookID = new SimpleStringProperty(),
                        bookName = new SimpleStringProperty(),
                        bookType = new SimpleStringProperty(),
                        bookAuthor = new SimpleStringProperty(),
                        bookPub = new SimpleStringProperty(),
                        bookNum = new SimpleStringProperty();
        HBox btnHBox;
        JFXButton detailBtn;
        JFXButton opBtn;
        public BookTableItem(String bookID, String bookName, String bookType, String bookAuthor, String bookPub, String bookNum) {
            this();
            this.bookID.setValue(bookID);
            this.bookName.setValue(bookName);
            this.bookType.setValue(bookType);
            this.bookAuthor.setValue(bookAuthor);
            this.bookNum.setValue(bookNum);
            this.bookPub.setValue(bookPub);
        }
        public BookTableItem() {
            btnHBox = new HBox();
            detailBtn = new JFXButton("详细");
//            detailBtn.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));
            detailBtn.setStyle("-fx-background-color: forestgreen");
            detailBtn.setTextFill(Color.WHITE);
            opBtn = new JFXButton("借阅");
            opBtn.setStyle("-fx-background-color: mediumpurple");
            opBtn.setFont(new Font(".PingFang SC", 14));
            opBtn.setTextFill(Color.WHITE);
            btnHBox.getChildren().addAll(detailBtn,opBtn);
            btnHBox.setAlignment(Pos.CENTER);
            btnHBox.setSpacing(10);
            btnHBox.setPrefHeight(30);
        }
        public void SetDetailButtonVisible(boolean visible) {
            detailBtn.setVisible(visible);
        }
        public void SetOperationButtonVisible(boolean visible) {
            opBtn.setVisible(visible);
        }
        public HBox getGraphic() {
            return btnHBox;
        }
        public JFXButton getDetailBtn() {return detailBtn;}
        public JFXButton getOpBtn() {return opBtn;}
    }

    class BookTableCell extends JFXTreeTableCell<BookTableItem, Boolean> {
        private BookTableItem bookItem;
        public BookTableCell() {
            super();
            bookItem = new BookTableItem();
        }

        public  void updateItem(Boolean bookItem, boolean empty) {
            super.updateItem(bookItem, empty);
            if (bookItem != null &&  !empty) {
                setGraphic(this.bookItem.getGraphic());
            } else {
                setGraphic(null);
            }
        }

        public BookTableItem getBookItem() {
            return bookItem;
        }
    }

    class RentTableItem extends RecursiveTreeObject<RentTableItem> {
        StringProperty  rentID, bookID, bookName, rentDate, backDate, isBack;
        HBox btnHBox;
        JFXButton detailBtn;
        JFXButton opBtn;
        public RentTableItem(String rentID, String bookID, String bookName, String rentDate, String backDate, String isBack) {
            this.rentID = new SimpleStringProperty(rentID);
            this.bookName = new SimpleStringProperty(bookName);
            this.bookID = new SimpleStringProperty(bookID);
            this.rentDate = new SimpleStringProperty(rentDate);
            this.backDate = new SimpleStringProperty(backDate);
            this.isBack = new SimpleStringProperty(isBack);
        }
        public RentTableItem() {
            btnHBox = new HBox();
            detailBtn = new JFXButton("详细");
//            detailBtn.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));
            detailBtn.setStyle("-fx-background-color: forestgreen");
            detailBtn.setTextFill(Color.WHITE);
            opBtn = new JFXButton("还书");
            opBtn.setStyle("-fx-background-color: mediumpurple");
            opBtn.setFont(new Font(".PingFang SC", 14));
            opBtn.setTextFill(Color.WHITE);
            btnHBox.getChildren().addAll(detailBtn,opBtn);
            btnHBox.setAlignment(Pos.CENTER);
            btnHBox.setSpacing(10);
            btnHBox.setPrefHeight(30);
        }
        public void SetDetailButtonVisible(boolean visible) {
            detailBtn.setVisible(visible);
        }
        public void SetOperatieButtonVisible(boolean visible) {
            opBtn.setVisible(visible);
        }
        public HBox getGraphic() {
            return btnHBox;
        }
        public JFXButton getDetailBtn() {return detailBtn;}
        public JFXButton getOpBtn() {return opBtn;}
    }

    class RentTableCell extends JFXTreeTableCell<RentTableItem, Boolean> {
        private RentTableItem rentItem;
        public RentTableCell() {
            super();
            rentItem = new RentTableItem();
        }

        public  void updateItem(Boolean bookItem, boolean empty) {
            super.updateItem(bookItem, empty);
            if (bookItem != null &&  !empty) {
                setGraphic(this.rentItem.getGraphic());
            } else {
                setGraphic(null);
            }
        }

        public RentTableItem getRentItem() {
            return rentItem;
        }
    }

    class TimeTableItem extends RecursiveTreeObject<TimeTableItem> {
        StringProperty  fineID, rentID, bookName, fineMount, fineDate, isSolved;
        HBox btnHBox;
        JFXButton detailBtn;
        JFXButton opBtn;
        public TimeTableItem(String fineID, String rentID, String bookName, String fineMount, String fineDate, String isSolved) {
            this.fineID = new SimpleStringProperty(fineID);
            this.bookName = new SimpleStringProperty(bookName);
            this.rentID = new SimpleStringProperty(rentID);
            this.fineMount = new SimpleStringProperty(fineMount);
            this.fineDate = new SimpleStringProperty(fineDate);
            this.isSolved = new SimpleStringProperty(isSolved);
        }
        public TimeTableItem() {
            btnHBox = new HBox();
            detailBtn = new JFXButton("详细");
//            detailBtn.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));
            detailBtn.setStyle("-fx-background-color: forestgreen");
            detailBtn.setTextFill(Color.WHITE);
            opBtn = new JFXButton("还款");
            opBtn.setStyle("-fx-background-color: mediumpurple");
            opBtn.setFont(new Font(".PingFang SC", 14));
            opBtn.setTextFill(Color.WHITE);
            btnHBox.getChildren().addAll(detailBtn,opBtn);
            btnHBox.setAlignment(Pos.CENTER);
            btnHBox.setSpacing(10);
            btnHBox.setPrefHeight(30);

        }
        public void SetDetailButtonVisible(boolean visible) {
            detailBtn.setVisible(visible);
        }
        public void SetOperatieButtonVisible(boolean visible) {
            opBtn.setVisible(visible);
        }
        public HBox getGraphic() {
            return btnHBox;
        }
        public JFXButton getDetailBtn() {return detailBtn;}
        public JFXButton getOpBtn() {return opBtn;}
    }

    class TimeTableCell extends JFXTreeTableCell<TimeTableItem, Boolean> {
        private TimeTableItem timeItem;
        public TimeTableCell() {
            super();
            timeItem = new TimeTableItem();
        }

        public  void updateItem(Boolean timeItem, boolean empty) {
            super.updateItem(timeItem, empty);
            if (timeItem != null &&  !empty) {
                setGraphic(this.timeItem.getGraphic());
            } else {
                setGraphic(null);
            }
        }

        public TimeTableItem getTimeItem() {
            return timeItem;
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        itemListView.setPrefWidth(200);
        ListItem listItem;
        double listViewHeight = 0;
        itemListView.setItems(listView);

        EventHandler<javafx.event.ActionEvent> eventHandler = e -> {
            Date date = new Date();
            SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeLabel.setText(dataformat.format(date));
        };

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        try {
            listItem = new ListItem("/home/haines/IdeaProjects/BookManageSystem/BMSClient/BookManageSystem/src/pictures/quill.png","书籍信息",20);
            listView.add(listItem);
            listViewHeight += listItem.getHeight() + 10;
            tabController.put(listItem,BookTab);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            listItem = new ListItem("/home/haines/IdeaProjects/BookManageSystem/BMSClient/BookManageSystem/src/pictures/rent.png","借阅信息",20);
            listView.add(listItem);
            listViewHeight += listItem.getHeight() + 10;
            tabController.put(listItem, RentTab);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            listItem = new ListItem("/home/haines/IdeaProjects/BookManageSystem/BMSClient/BookManageSystem/src/pictures/stopwatch.png","逾期信息",20);
            listView.add(listItem);
            listViewHeight += listItem.getHeight() + 10;
            tabController.put(listItem, TimeTab);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        itemListView.setCellFactory(param -> new ListItem.Cell());
        itemListView.setDepth(1);
        itemListView.setPrefHeight(listViewHeight + (listView.size() - 1) * 10);

        RentTab.setVisible(false);
        TimeTab.setVisible(false);
        BookTab.setVisible(true);


        currentTab = BookTab;
        itemListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ListItem>() {
            @Override
            public void changed(ObservableValue<? extends ListItem> observable, ListItem oldValue, ListItem newValue) {
                if (newValue != null) {
                    currentTab.setVisible(false);
                    currentTab = tabController.get(newValue);
                    currentTab.setVisible(true);
                }
            }
        });

        JFXTreeTableColumn<BookTableItem, String> bookID = new JFXTreeTableColumn<>("图书编号");
        bookID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookTableItem, String> param) {
                return param.getValue().getValue().bookID;
            }
        });
        JFXTreeTableColumn<BookTableItem, String> bookName = new JFXTreeTableColumn<>("图书名称");
        bookName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookTableItem, String> param) {
                return param.getValue().getValue().bookName;
            }
        });
        JFXTreeTableColumn<BookTableItem, String> bookType = new JFXTreeTableColumn<>("图书类别");
        bookType.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookTableItem, String> param) {
                return param.getValue().getValue().bookType;
            }
        });
        JFXTreeTableColumn<BookTableItem, String> bookAuthor = new JFXTreeTableColumn<>("作者");
        bookAuthor.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookTableItem, String> param) {
                return param.getValue().getValue().bookAuthor;
            }
        });
        JFXTreeTableColumn<BookTableItem, String> bookPub = new JFXTreeTableColumn<>("出版社");
        bookPub.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookTableItem, String> param) {
                return param.getValue().getValue().bookPub;
            }
        });
        JFXTreeTableColumn<BookTableItem, String> bookNum = new JFXTreeTableColumn<>("在馆数量");
        bookNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BookTableItem, String> param) {
                return param.getValue().getValue().bookNum;
            }
        });
        JFXTreeTableColumn<BookTableItem, Boolean> bookOp = new JFXTreeTableColumn<>("操作");
        bookOp.setSortable(false);
        bookOp.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BookTableItem, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<BookTableItem, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().getValue().btnHBox != null);
            }
        });
        bookOp.setCellFactory(new Callback<TreeTableColumn<BookTableItem, Boolean>, TreeTableCell<BookTableItem, Boolean>>() {
            @Override
            public TreeTableCell<BookTableItem, Boolean> call(TreeTableColumn<BookTableItem, Boolean> param) {
                BookTableCell cell = new BookTableCell();
                cell.getBookItem().getDetailBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm in book!");
                        userViewController.getStage(Main.UserDetailDialogID).setTitle("书籍详情");
                        userViewController.setStage(Main.UserDetailDialogID);
                        DetailDialog detailDialogController = (DetailDialog) userViewController.getController(Main.UserDetailDialogID);
                        detailDialogController.getLabel1().setText("书籍编号：" + DataContainer.books.get(cell.getIndex()).get(0));
                        detailDialogController.getLabel2().setText("书籍名称：" + DataContainer.books.get(cell.getIndex()).get(1));
                        detailDialogController.getLabel3().setText("书籍类别：" + DataContainer.books.get(cell.getIndex()).get(2));
                        detailDialogController.getLabel4().setText("作者：" + DataContainer.books.get(cell.getIndex()).get(3));
                        detailDialogController.getLabel5().setText("出版社：" + DataContainer.books.get(cell.getIndex()).get(4));
                        detailDialogController.getLabel6().setText("出版时间：" + DataContainer.books.get(cell.getIndex()).get(5));
                        detailDialogController.getLabel7().setText("馆藏数量：" + DataContainer.books.get(cell.getIndex()).get(6));
                        detailDialogController.getLabel8().setText("在馆数量：" + DataContainer.books.get(cell.getIndex()).get(7));
                        detailDialogController.getLabel9().setText("价格：" + DataContainer.books.get(cell.getIndex()).get(8));
                        detailDialogController.getLabel10().setText("评分：" + DataContainer.books.get(cell.getIndex()).get(9));
                        detailDialogController.getLabel11().setText("评论数量：" + DataContainer.books.get(cell.getIndex()).get(10));
                    }
                });
                cell.getBookItem().getOpBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm out of book!");
                        if (Integer.valueOf(books.get(cell.getIndex()).bookNum.getValue()) == 0) {
                            popHintDialog("该图书在馆数量为0，无法借阅！");
                            return;
                        }
                        MessageData messageData = new MessageData();
                        messageData.setMessageType(MessageType.rentReq);
                        messageData.getData().add(books.get(cell.getIndex()).bookID.getValue());
                        messageData.getData().add(DataContainer.profile.get(0));
                        timestamp = DataContainer.getTimeStamp();
                        System.out.println(timestamp);
                        messageData.getData().add(timestamp.toString());
                        timestamp = DataContainer.addTimesStamp(timestamp);
                        System.out.println(timestamp);
                        messageData.getData().add(timestamp.toString());
                        Connector.getInstance().send(messageData);
                        popHintDialog("提交借阅申请成功！");
                    }
                });
                return cell;
            }
        });

        bookTable.getColumns().setAll(bookID, bookName, bookType, bookAuthor, bookPub, bookNum, bookOp);
        bookTable.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);

        final TreeItem<BookTableItem> bookRoot = new RecursiveTreeItem<>(books, RecursiveTreeObject::getChildren);
        bookTable.setRoot(bookRoot);
        bookTable.setShowRoot(false);

        /******************** rent Table **********************/
        JFXTreeTableColumn<RentTableItem, String> rentID = new JFXTreeTableColumn<>("借阅编号");
        rentID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RentTableItem, String> param) {
                return param.getValue().getValue().rentID;
            }
        });
        JFXTreeTableColumn<RentTableItem, String> rBookID = new JFXTreeTableColumn<>("图书编号");
        rBookID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RentTableItem, String> param) {
                return param.getValue().getValue().bookID;
            }
        });
        JFXTreeTableColumn<RentTableItem, String> rBookName = new JFXTreeTableColumn<>("图书名称");
        rBookName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RentTableItem, String> param) {
                return param.getValue().getValue().bookName;
            }
        });
        JFXTreeTableColumn<RentTableItem, String> rentDate = new JFXTreeTableColumn<>("借阅日期");
        rentDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RentTableItem, String> param) {
                return param.getValue().getValue().rentDate;
            }
        });
        JFXTreeTableColumn<RentTableItem, String> backDate = new JFXTreeTableColumn<>("应还日期");
        backDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RentTableItem, String> param) {
                return param.getValue().getValue().backDate;
            }
        });
        JFXTreeTableColumn<RentTableItem, String> isBack = new JFXTreeTableColumn<>("归还状态");
        isBack.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RentTableItem, String> param) {
                return param.getValue().getValue().isBack;
            }
        });
        JFXTreeTableColumn<RentTableItem, Boolean> rentOp = new JFXTreeTableColumn<>("操作");
        rentOp.setSortable(false);
        rentOp.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<RentTableItem, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().getValue().btnHBox != null);
            }
        });
        rentOp.setCellFactory(new Callback<TreeTableColumn<RentTableItem, Boolean>, TreeTableCell<RentTableItem, Boolean>>() {
            @Override
            public TreeTableCell<RentTableItem, Boolean> call(TreeTableColumn<RentTableItem, Boolean> param) {
                RentTableCell cell = new RentTableCell();
                cell.getRentItem().getDetailBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm in rent!");
                        userViewController.getStage(Main.UserDetailDialogID).setTitle("借阅详情");
                        userViewController.setStage(Main.UserDetailDialogID);
                        DetailDialog detailDialogController = (DetailDialog) userViewController.getController(Main.UserDetailDialogID);
                        detailDialogController.getLabel1().setText("借阅编号：" + DataContainer.rents.get(cell.getIndex()).get(0));
                        detailDialogController.getLabel2().setText("图书编号：" + DataContainer.rents.get(cell.getIndex()).get(1));
                        detailDialogController.getLabel3().setText("图书名称：" + DataContainer.rents.get(cell.getIndex()).get(2));
                        detailDialogController.getLabel4().setText("借阅日期：" + DataContainer.rents.get(cell.getIndex()).get(3));
                        detailDialogController.getLabel5().setText("应还日期：" + DataContainer.rents.get(cell.getIndex()).get(4));
                        detailDialogController.getLabel6().setText("是否归还：" + DataContainer.rents.get(cell.getIndex()).get(5));
                        detailDialogController.getLabel7().setVisible(false);
                        detailDialogController.getLabel8().setVisible(false);
                        detailDialogController.getLabel9().setVisible(false);
                        detailDialogController.getLabel10().setVisible(false);
                        detailDialogController.getLabel11().setVisible(false);
                        detailDialogController.getLabel12().setVisible(false);
                    }
                });
                cell.getRentItem().getOpBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        System.out.println("I'm out of rent!");
                        System.out.println("I'm out of book!");

                        if (Boolean.valueOf(rents.get(cell.getIndex()).isBack.getValue())) {
                            popHintDialog("图书已归还，无需归还！");
                            return;
                        }
                        MessageData messageData = new MessageData();
                        messageData.setMessageType(MessageType.returnReq);
                        messageData.getData().add(rents.get(cell.getIndex()).rentID.getValue());
                        messageData.getData().add(rents.get(cell.getIndex()).bookID.getValue());
                        messageData.getData().add(DataContainer.profile.get(0));
                        timestamp = DataContainer.getTimeStamp();
                        System.out.println(timestamp);
                        messageData.getData().add(timestamp.toString());
                        Connector.getInstance().send(messageData);
                        popHintDialog("提交归还申请成功！");
                    }
                });
                return cell;
            }
        });

        rentTable.getColumns().setAll(rentID, rBookID, rBookName,rentDate, backDate, isBack, rentOp);
        rentTable.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);

        final TreeItem<RentTableItem> rentRoot = new RecursiveTreeItem<>(rents, RecursiveTreeObject::getChildren);
        rentTable.setRoot(rentRoot);
        rentTable.setShowRoot(false);

        /*****************time table********************/


        JFXTreeTableColumn<TimeTableItem, String> fineID = new JFXTreeTableColumn<>("罚款编号");
        fineID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTableItem, String> param) {
                return param.getValue().getValue().fineID;
            }
        });
        JFXTreeTableColumn<TimeTableItem, String> fRentID = new JFXTreeTableColumn<>("借阅编号");
        fRentID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTableItem, String> param) {
                return param.getValue().getValue().rentID;
            }
        });
        JFXTreeTableColumn<TimeTableItem, String> fBookName = new JFXTreeTableColumn<>("借阅书籍");
        fBookName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTableItem, String> param) {
                return param.getValue().getValue().bookName;
            }
        });
        JFXTreeTableColumn<TimeTableItem, String> fineMount = new JFXTreeTableColumn<>("罚款金额");
        fineMount.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTableItem, String> param) {
                return param.getValue().getValue().fineMount;
            }
        });
        JFXTreeTableColumn<TimeTableItem, String> fineDate = new JFXTreeTableColumn<>("罚款日期");
        fineDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTableItem, String> param) {
                return param.getValue().getValue().fineDate;
            }
        });
        JFXTreeTableColumn<TimeTableItem, String> isSolved = new JFXTreeTableColumn<>("还款状态");
        isSolved.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTableItem, String> param) {
                return param.getValue().getValue().isSolved;
            }
        });
        JFXTreeTableColumn<TimeTableItem, Boolean> fineOp = new JFXTreeTableColumn<>("操作");
        fineOp.setSortable(false);
        fineOp.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTableItem, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<TimeTableItem, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().getValue().btnHBox != null);
            }
        });
        fineOp.setCellFactory(new Callback<TreeTableColumn<TimeTableItem, Boolean>, TreeTableCell<TimeTableItem, Boolean>>() {
            @Override
            public TreeTableCell<TimeTableItem, Boolean> call(TreeTableColumn<TimeTableItem, Boolean> param) {
                TimeTableCell cell = new TimeTableCell();
                cell.getTimeItem().getDetailBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm in time!");
                        DetailDialog detailDialogController = (DetailDialog)userViewController.getController(Main.UserDetailDialogID);

                        userViewController.getStage(Main.UserDetailDialogID).setTitle("罚款详情");
                        userViewController.setStage(Main.UserDetailDialogID);

                        detailDialogController.getLabel1().setText("罚款编号：" + DataContainer.fines.get(cell.getIndex()).get(0));
                        detailDialogController.getLabel2().setText("借阅编号：" + DataContainer.fines.get(cell.getIndex()).get(1));
                        detailDialogController.getLabel3().setText("图书名称：" + DataContainer.fines.get(cell.getIndex()).get(2));
                        detailDialogController.getLabel4().setText("罚款金额：" + DataContainer.fines.get(cell.getIndex()).get(3));
                        detailDialogController.getLabel5().setText("罚款日期：" + DataContainer.fines.get(cell.getIndex()).get(4));
                        detailDialogController.getLabel6().setText("是否已缴：" + DataContainer.fines.get(cell.getIndex()).get(5));
                        detailDialogController.getLabel7().setVisible(false);
                        detailDialogController.getLabel8().setVisible(false);
                        detailDialogController.getLabel9().setVisible(false);
                        detailDialogController.getLabel10().setVisible(false);
                        detailDialogController.getLabel11().setVisible(false);
                        detailDialogController.getLabel12().setVisible(false);

                    }
                });
                cell.getTimeItem().getOpBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm out of time!");
                        if (Boolean.valueOf(fines.get(cell.getIndex()).isSolved.getValue())) {
                            popHintDialog("罚款已缴清，无需缴纳！");
                            return;
                        }
                        MessageData messageData = new MessageData();
                        messageData.setMessageType(MessageType.fineReq);
                        messageData.getData().add(fines.get(cell.getIndex()).fineID.getValue());
                        Connector.getInstance().send(messageData);
                        popHintDialog("提交缴纳罚款申请成功！");
                    }
                });
                return cell;
            }
        });

        fineTable.getColumns().setAll(fineID, fRentID, fBookName, fineMount, fineDate, isSolved, fineOp);
        fineTable.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);

        final TreeItem<TimeTableItem> fineRoot = new RecursiveTreeItem<>(fines, RecursiveTreeObject::getChildren);
        fineTable.setRoot(fineRoot);
        fineTable.setShowRoot(false);

        setBookClassComboList();
    }

    public void onProfileButtonClicked() {
        assert DataContainer.profile.size() >= 7;
        userViewController.getStage(Main.UserProfileViewID).setTitle("个人信息");
        userViewController.setStage(Main.UserProfileViewID);
        Platform.runLater(() -> {
            ProfileView profileView = (ProfileView) userViewController.getController(Main.UserProfileViewID);
            profileView.clearInfo();
            profileView.setInfo(DataContainer.profile);
        });
    }

    public void onAltpwdButtonClicked() {
        userViewController.getStage(Main.UserAltpwdDialogID).setTitle("修改密码");
        userViewController.setStage(Main.UserAltpwdDialogID);

    }

    public void onQuitButtonClicked() {
        userViewController.closeStage(Main.UserViewID);
    }

    public void setBookClassComboList(ArrayList<String> items) {
        for (String item: items) {
            bookClassComboList.getItems().add(item);
        }
    }

    private void setBookClassComboList() {
        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.sqlBTypeReq);
        Connector.getInstance().send(messageData);
    }

    public void onBookSearchButtonCLicked() {
        String sql = "select * from Book ";
        ArrayList<String> alter = new ArrayList<>();
        String sqlBookID = bookIDField.getText().trim().equals("") ? "" : "bID = " + bookIDField.getText().trim();
        String sqlBookName = bookNameField.getText().trim().equals("") ? "" : "bName = '" +  bookNameField.getText().trim() + "'";
        String sqlBookClass = bookClassComboList.getEditor().getText().trim().equals("") ? "" : "bType = '" + bookClassComboList.getSelectionModel().getSelectedItem() + "'";
        String sqlBookAuthor = authorTield.getText().trim().equals("") ? "" : "aName = '" + authorTield.getText().trim() + "'";
        String sqlPublisher = pubField.getText().trim().equals("") ? "" : "pubName = '" + pubField.getText().trim() + "'";
        System.out.println(bookClassComboList.getEditor().getText().trim());
        if (!sqlBookID.equals("")) alter.add(sqlBookID);
        if (!sqlBookName.equals("")) alter.add(sqlBookName);
        if (!sqlBookClass.equals("")) alter.add(sqlBookClass);
        if (!sqlBookAuthor.equals("")) alter.add(sqlBookAuthor);
        if (!sqlPublisher.equals("")) alter.add(sqlPublisher);
        if (alter.size() > 0) sql = "select * from Book where ";
        for (int i = 0 ; i < alter.size() ; ++i) {
            if (i == alter.size() - 1) sql += alter.get(i);
            else sql += alter.get(i) + " and ";
        }

        try {
            sql = new String(sql.getBytes("UTF-8"),"UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.sqlBookReq);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);

        books.clear();
    }

    public void addBookTableItem(ArrayList<String> book) {
        assert book.size() >= 7;
        books.add(new BookTableItem(book.get(0),book.get(1),book.get(2),book.get(3),book.get(4),book.get(7)));
    }

    public void addRentTableItem(ArrayList<String> rent) {
        assert rent.size() >= 6;
        rents.add(new RentTableItem(rent.get(0), rent.get(1), rent.get(2), rent.get(3), rent.get(4), rent.get(5)));
    }

    public void addFineTableItem(ArrayList<String> fine) {
        assert fine.size() >= 6;
        fines.add(new TimeTableItem(fine.get(0), fine.get(1), fine.get(2), fine.get(3), fine.get(4), fine.get(5)));
    }

    public void onRentSearchButtonClicked() {
        String sql = "select * from BorrowReg ";
        ArrayList<String> alter = new ArrayList<>();
        String sqlRentBookId = rentBookIDField.getText().trim().equals("") ? "" : "bID = " + rentBookIDField.getText().trim();
        String sqlRentID = rentIdField.getText().trim().equals("") ? "" :  "rID = " + rentIdField.getText().trim();
        System.out.println(bookClassComboList.getEditor().getText().trim());
        if (!sqlRentID.equals("")) alter.add(sqlRentID);
        if (!sqlRentBookId.equals("")) alter.add(sqlRentBookId);
        if (alter.size() > 0) sql = "select * from BorrowReg where ";
        for (int i = 0 ; i < alter.size() ; ++i) {
            if (i == alter.size() - 1) sql += alter.get(i);
            else sql += alter.get(i) + " and ";
        }
        try {
            sql = new String(sql.getBytes("UTF-8"),"UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.sqlRentReq);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);

        rents.clear();
    }

    public void onFineSearchButtonClicked() {
        String sql = "select * from FineReg ";
        ArrayList<String> alter = new ArrayList<>();
        String sqlFineID = fineIDField.getText().trim().equals("") ? "" : "fID = " + fineIDField.getText().trim();
        String sqlFineRentID = fineRentIDField.getText().trim().equals("") ? "" :  "rID = " + fineRentIDField.getText().trim();
        System.out.println(bookClassComboList.getEditor().getText().trim());
        if (!sqlFineID.equals("")) alter.add(sqlFineID);
        if (!sqlFineRentID.equals("")) alter.add(sqlFineRentID);
        if (alter.size() > 0) sql = "select * from FineReg where ";
        for (int i = 0 ; i < alter.size() ; ++i) {
            if (i == alter.size() - 1) sql += alter.get(i);
            else sql += alter.get(i) + " and ";
        }
        try {
            sql = new String(sql.getBytes("UTF-8"),"UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.sqlFineReq);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);

        fines.clear();
    }

    public void setWelcomeLabel(String text) {
        welcomeLabel.setText(text);
    }

    public void popHintDialog(String hintContent) {
        JFXAlert alert = new JFXAlert((Stage) itemListView.getScene().getWindow());
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
