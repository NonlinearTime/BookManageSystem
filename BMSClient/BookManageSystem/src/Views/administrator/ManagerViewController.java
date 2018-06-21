package Views.administrator;

import Conponent.Connector;
import Conponent.MessageData;
import Conponent.MessageType;
import StageController.ControlledStage;
import StageController.StageController;
import Views.Conponent.ListItem;
import Views.data.DataContainer;
import com.company.RentDetail;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ManagerViewController implements ControlledStage, Initializable {
    private StageController managerViewController;
    private HashMap<ListItem, AnchorPane> tabController = new HashMap<>();
    private ObservableList<ListItem> managerListView = FXCollections.observableArrayList();
    private AnchorPane currentTab;
    private ObservableList<BookTableItem> books =  FXCollections.observableArrayList();
    private ObservableList<RentTableItem> rents =  FXCollections.observableArrayList();
    private ObservableList<BackTableItem> backs =  FXCollections.observableArrayList();
    private ObservableList<FineTableItem> fines =  FXCollections.observableArrayList();
    private ObservableList<UserTableItem> users =  FXCollections.observableArrayList();

    @FXML
    private JFXTreeTableView<BookTableItem> bookTable;

    @FXML
    private JFXTreeTableView<RentTableItem> rentTable;

    @FXML
    private JFXTreeTableView<BackTableItem> backTable;

    @FXML
    private JFXTreeTableView<FineTableItem> fineTable;

    @FXML
    private JFXTreeTableView<UserTableItem> userTable;


    @FXML
    private JFXListView<ListItem> managerItemListView;

    @FXML
    private AnchorPane bookTab;

    @FXML
    private AnchorPane rentTab;

    @FXML
    private AnchorPane timeTab;

    @FXML
    private AnchorPane backTab;

    @FXML
    private AnchorPane readerTab;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private JFXTextField rentIDField;

    @FXML
    private JFXTextField rentUserIDFIeld;

    @FXML
    private JFXTextField rentUserNameField;

    @FXML
    private JFXTextField backRentIDField;

    @FXML
    private JFXTextField backUserIDField;

    @FXML
    private JFXTextField fineIDField;

    @FXML
    private JFXTextField fineRentIDField;

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
    private JFXTextField UserIDField;

    @FXML
    private JFXTextField UserNameFIeld;


    class BookTableItem extends RecursiveTreeObject<BookTableItem> {
        StringProperty bookID, bookName, bookType, bookAuthor, bookPub, bookNum;
        HBox btnHBox;
        JFXButton detailBtn;
        JFXButton opBtn;
        public BookTableItem(String bookID, String bookName, String bookType, String bookAuthor, String bookPub, String bookNum) {
            this.bookID = new SimpleStringProperty(bookID);
            this.bookName = new SimpleStringProperty(bookName);
            this.bookType = new SimpleStringProperty(bookType);
            this.bookAuthor = new SimpleStringProperty(bookAuthor);
            this.bookNum = new SimpleStringProperty(bookNum);
            this.bookPub = new SimpleStringProperty(bookPub);
        }
        public BookTableItem() {
            btnHBox = new HBox();
            detailBtn = new JFXButton("查看");
            detailBtn.setStyle("-fx-background-color: forestgreen");
            detailBtn.setTextFill(Color.WHITE);
            opBtn = new JFXButton("下架");
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
        StringProperty rentID, userID, userName, bookName, rentDate, backDate;
        HBox btnHBox;
        JFXButton detailBtn;
        JFXButton opBtn;
        JFXButton deleteBtn;
        public RentTableItem(String rentID, String userID, String userName, String bookName, String rentDate, String backDate) {
            this.rentID = new SimpleStringProperty(rentID);
            this.userID = new SimpleStringProperty(userID);
            this.userName = new SimpleStringProperty(userName);
            this.bookName = new SimpleStringProperty(bookName);
            this.rentDate = new SimpleStringProperty(rentDate);
            this.backDate = new SimpleStringProperty(backDate);
        }
        public RentTableItem() {
            btnHBox = new HBox();
            detailBtn = new JFXButton("查看");
//            detailBtn.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));
            detailBtn.setStyle("-fx-background-color: forestgreen");
            detailBtn.setFont(new Font(".PingFang SC", 10));
            detailBtn.setTextFill(Color.WHITE);
            opBtn = new JFXButton("批准");
            opBtn.setStyle("-fx-background-color: mediumpurple");
            opBtn.setFont(new Font(".PingFang SC", 10));
            opBtn.setTextFill(Color.WHITE);
            deleteBtn = new JFXButton("删除");
            deleteBtn.setStyle("-fx-background-color: red");
            deleteBtn.setFont(new Font(".PingFang SC", 10));
            deleteBtn.setTextFill(Color.WHITE);
            btnHBox.getChildren().addAll(detailBtn,opBtn,deleteBtn);
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
        public void SetDeleteButtonVisible(boolean visible) {deleteBtn.setVisible(visible);}
        public HBox getGraphic() {
            return btnHBox;
        }
        public JFXButton getDetailBtn() {return detailBtn;}
        public JFXButton getOpBtn() {return opBtn;}
        public JFXButton getDeleteBtn() {return deleteBtn;}
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

    class FineTableItem extends RecursiveTreeObject<FineTableItem> {
        StringProperty  fineID, userName, rentID, fineMount, fineDate;
        HBox btnHBox;
        JFXButton detailBtn;
        JFXButton opBtn;
        public FineTableItem(String fineID, String userName, String rentID, String fineMount, String fineDate) {
            this.rentID = new SimpleStringProperty(rentID);
            this.fineID = new SimpleStringProperty(fineID);
            this.userName = new SimpleStringProperty(userName);

            this.fineMount = new SimpleStringProperty(fineMount);
            this.fineDate = new SimpleStringProperty(fineDate);
        }
        public FineTableItem() {
            btnHBox = new HBox();
            detailBtn = new JFXButton("查看");
//            detailBtn.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));
            detailBtn.setStyle("-fx-background-color: forestgreen");
            detailBtn.setFont(new Font(".PingFang SC", 14));
            detailBtn.setTextFill(Color.WHITE);
            opBtn = new JFXButton("处理");
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

    class FineTableCell extends JFXTreeTableCell<FineTableItem, Boolean> {
        private FineTableItem timeItem;
        public FineTableCell() {
            super();
            timeItem = new FineTableItem();
        }

        public  void updateItem(Boolean timeItem, boolean empty) {
            super.updateItem(timeItem, empty);
            if (timeItem != null &&  !empty) {
                setGraphic(this.timeItem.getGraphic());
            } else {
                setGraphic(null);
            }
        }

        public FineTableItem getFineItem() {
            return timeItem;
        }
    }


    class BackTableItem extends RecursiveTreeObject<BackTableItem> {
        StringProperty  rentID, userID, userName, bookName, rentDate, backDate;
        HBox btnHBox;
        JFXButton detailBtn;
        JFXButton opBtn;
        public BackTableItem(String rentID, String userID, String userName, String bookName, String rentDate, String backDate) {
            this.rentID = new SimpleStringProperty(rentID);
            this.userID = new SimpleStringProperty(userID);
            this.userName = new SimpleStringProperty(userName);
            this.bookName = new SimpleStringProperty(bookName);
            this.rentDate = new SimpleStringProperty(rentDate);
            this.backDate = new SimpleStringProperty(backDate);
        }
        public BackTableItem() {
            btnHBox = new HBox();
            detailBtn = new JFXButton("查看");
//            detailBtn.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));
            detailBtn.setStyle("-fx-background-color: forestgreen");
            detailBtn.setTextFill(Color.WHITE);
            opBtn = new JFXButton("处理");
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

    class BackTableCell extends JFXTreeTableCell<BackTableItem, Boolean> {
        private BackTableItem backItem;
        public BackTableCell() {
            super();
            backItem = new BackTableItem();
        }

        public  void updateItem(Boolean backItem, boolean empty) {
            super.updateItem(backItem, empty);
            if (backItem != null &&  !empty) {
                setGraphic(this.backItem.getGraphic());
            } else {
                setGraphic(null);
            }
        }

        public BackTableItem getBackItem() {
            return backItem;
        }
    }

    class UserTableItem extends RecursiveTreeObject<UserTableItem> {
        StringProperty  userID, userName, userEmail, userJob, userTele, userDate;
        HBox btnHBox;
        JFXButton detailBtn;
        JFXButton altBtn;
        JFXButton deleteBtn;
        public UserTableItem(String userID, String userName, String userEmail, String userJob, String userTele, String userDate) {
            this.userID = new SimpleStringProperty(userID);
            this.userName = new SimpleStringProperty(userName);
            this.userEmail = new SimpleStringProperty(userEmail);
            this.userJob = new SimpleStringProperty(userJob);
            this.userTele = new SimpleStringProperty(userTele);
            this.userDate = new SimpleStringProperty(userDate);
        }
        public UserTableItem() {
            btnHBox = new HBox();
            detailBtn = new JFXButton("查看");
//            detailBtn.setBackground(new Background(new BackgroundFill(Color.ORANGE,null,null)));
            detailBtn.setStyle("-fx-background-color: forestgreen");
            detailBtn.setFont(new Font(".PingFang SC", 10));
            detailBtn.setTextFill(Color.WHITE);
            altBtn = new JFXButton("修改");
            altBtn.setStyle("-fx-background-color: darkorange");
            altBtn.setFont(new Font(".PingFang SC", 10));
            altBtn.setTextFill(Color.WHITE);
            deleteBtn = new JFXButton("删除");
            deleteBtn.setStyle("-fx-background-color: red");
            deleteBtn.setFont(new Font(".PingFang SC", 10));
            deleteBtn.setTextFill(Color.WHITE);
            btnHBox.getChildren().addAll(detailBtn, altBtn, deleteBtn);
            btnHBox.setAlignment(Pos.CENTER);
            btnHBox.setSpacing(5);
            btnHBox.setPrefHeight(30);
            btnHBox.setPrefWidth(100);
        }
        public void SetDetailButtonVisible(boolean visible) {
            detailBtn.setVisible(visible);
        }
        public void SetAltButtonVisible(boolean visible) {
            altBtn.setVisible(visible);
        }
        public void SetDeleteButtonVisible(boolean visible) {deleteBtn.setVisible(visible);}
        public JFXButton getDetailBtn() {return detailBtn;}
        public JFXButton getAltBtn() {return altBtn;}
        public JFXButton getDeleteBtn() {return deleteBtn;}
        public HBox getGraphic() {
            return btnHBox;
        }

    }

    class UserTableCell extends JFXTreeTableCell<UserTableItem, Boolean> {
        private UserTableItem userItem;
        public UserTableCell() {
            super();
            userItem = new UserTableItem();
        }

        public  void updateItem(Boolean userItem, boolean empty) {
            super.updateItem(userItem, empty);
            if (userItem != null &&  !empty) {
                setGraphic(this.userItem.getGraphic());
            } else {
                setGraphic(null);
            }
        }

        public UserTableItem getUserItem() {
            return userItem;
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ListItem listItem;
        double listViewHeight = 0;
        managerItemListView.setItems(managerListView);

        EventHandler<javafx.event.ActionEvent> eventHandler = e -> {
            Date date = new Date();
            SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeLabel.setText(dataformat.format(date));
        };

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        setBookClassComboList();

        try {
            listItem = new ListItem("/home/haines/IdeaProjects/BookManageSystem/BMSClient/BookManageSystem/src/pictures/rent.png","借阅审核",20);
            managerListView.add(listItem);
            listViewHeight += listItem.getHeight() + 10;
            tabController.put(listItem, rentTab);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            listItem = new ListItem("/home/haines/IdeaProjects/BookManageSystem/BMSClient/BookManageSystem/src/pictures/back.png","归还审核",20);
            managerListView.add(listItem);
            listViewHeight += listItem.getHeight() + 10;
            tabController.put(listItem, backTab);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            listItem = new ListItem("/home/haines/IdeaProjects/BookManageSystem/BMSClient/BookManageSystem/src/pictures/stopwatch.png","逾期处理",20);
            managerListView.add(listItem);
            listViewHeight += listItem.getHeight() + 10;
            tabController.put(listItem, timeTab);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            listItem = new ListItem("/home/haines/IdeaProjects/BookManageSystem/BMSClient/BookManageSystem/src/pictures/quill.png","图书管理",20);
            managerListView.add(listItem);
            listViewHeight += listItem.getHeight() + 10;
            tabController.put(listItem, bookTab);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            listItem = new ListItem("/home/haines/IdeaProjects/BookManageSystem/BMSClient/BookManageSystem/src/pictures/team.png","读者管理",20);
            managerListView.add(listItem);
            listViewHeight += listItem.getHeight() + 10;
            tabController.put(listItem, readerTab);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        managerItemListView.setCellFactory(param -> new ListItem.Cell());
        managerItemListView.setDepth(1);
        managerItemListView.setPrefHeight(listViewHeight + (managerListView.size() - 1) * 9);

        rentTab.setVisible(true);
        timeTab.setVisible(false);
        bookTab.setVisible(false);
        readerTab.setVisible(false);
        backTab.setVisible(false);


        currentTab = rentTab;
        managerItemListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ListItem>() {
            @Override
            public void changed(ObservableValue<? extends ListItem> observable, ListItem oldValue, ListItem newValue) {
                if (newValue != null) {
                    currentTab.setVisible(false);
                    AnchorPane tab  = tabController.get(newValue);
                    if (tab != null) currentTab = tab;
                    System.out.println(currentTab);
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
//                        userViewController.getStage(Main.UserDetailDialogID).setTitle("书籍详情");
//                        userViewController.setStage(Main.UserDetailDialogID);
//                        DetailDialog detailDialogController = (DetailDialog) userViewController.getController(Main.UserDetailDialogID);
//                        detailDialogController.getLabel1().setText("书籍编号：" + books.get(cell.getIndex()).bookID.getValue());
//                        detailDialogController.getLabel2().setText("书籍名称：" + books.get(cell.getIndex()).bookName.getValue());
//                        detailDialogController.getLabel3().setText("书籍类别：" + books.get(cell.getIndex()).bookType.getValue());
//                        detailDialogController.getLabel4().setText("作者：" + books.get(cell.getIndex()).bookAuthor.getValue());
//                        detailDialogController.getLabel5().setText("出版社：" + books.get(cell.getIndex()).bookPub.getValue());
//                        detailDialogController.getLabel6().setText("出版时间：" + books.get(cell.getIndex()).bookID.getValue());
//                        detailDialogController.getLabel7().setText("馆藏数量：" + books.get(cell.getIndex()).bookID.getValue());
//                        detailDialogController.getLabel8().setText("在馆数量：" + books.get(cell.getIndex()).bookID.getValue());
//                        detailDialogController.getLabel9().setText("价格：" + books.get(cell.getIndex()).bookID.getValue());
//                        detailDialogController.getLabel10().setText("评分：" + books.get(cell.getIndex()).bookID.getValue());
//                        detailDialogController.getLabel11().setText("评论数量：" + books.get(cell.getIndex()).bookID.getValue());
////                        detailDialogController.getLabel12().setText("书籍编号：" + books.get(cell.getIndex()).bookID.getValue());
                    }
                });
                cell.getBookItem().getOpBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm out of book!");
                    }
                });
                return cell;
            }
        });

        books.add(new BookTableItem("1","图书管理系统","工具书","lhm","hust","11"));
        books.add(new BookTableItem("1","图书管理系统","工具书","lhm","hust","11"));

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
        JFXTreeTableColumn<RentTableItem, String> rUserID = new JFXTreeTableColumn<>("用户编号");
        rUserID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RentTableItem, String> param) {
                return param.getValue().getValue().userID;
            }
        });
        JFXTreeTableColumn<RentTableItem, String> userName = new JFXTreeTableColumn<>("用户名");
        userName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RentTableItem, String> param) {
                return param.getValue().getValue().userName;
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
//                        userViewController.getStage(Main.UserDetailDialogID).setTitle("借阅详情");
//                        userViewController.setStage(Main.UserDetailDialogID);
                        RentDetailViewController rentDetailViewController = (RentDetailViewController) managerViewController.getController(Main.ManagerRentDetailViewID);
                        int index = cell.getIndex();
                        assert DataContainer.rents.size() > index;
                        assert DataContainer.rentDetails.size() > index;
                        ArrayList<String> rent = DataContainer.managerents.get(index);
                        RentDetail detail = DataContainer.rentDetails.get(index);
                        rentDetailViewController.clearInfo();
                        rentDetailViewController.setUserIDField(rent.get(1));
                        rentDetailViewController.setUserNameField(rent.get(2));
                        rentDetailViewController.setRentIDField(rent.get(0));
                        rentDetailViewController.setRentNumField(String.valueOf(detail.getRentNum()));
                        rentDetailViewController.setUnreturnBooksField(detail.getBooks());
                        rentDetailViewController.setFineMountField(String.valueOf(detail.getFineMount()));
                        managerViewController.getStage(Main.ManagerRentDetailViewID).setTitle("借阅详情");
                        managerViewController.setStage(Main.ManagerRentDetailViewID);
                    }
                });
                cell.getRentItem().getOpBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm out of rent!");
                        int index = cell.getIndex();
                        ArrayList<String> rent = DataContainer.managerents.get(index);
                        RentDetail detail = DataContainer.rentDetails.get(index);
                        if (detail.getRentNum() > 3) {
                            popHintDialog("该用户借阅数量达到上限");
                            return;
                        }
                        if (detail.getBooks().size() > 0) {
                            popHintDialog("该用户存在逾期行为");
                            return;
                        }
                        if (detail.getFineMount() > 0) {
                            popHintDialog("该用户罚款未缴纳");
                            return;
                        }
                        String sql = "insert into BorrowReg (uID, bID, rDate, rbDate, isBack) values (" +
                                rent.get(1) + "," + rent.get(3) + ",'" + rent.get(5) + "','" +
                                rent.get(6) + "'," + "0" +
                                ")";

                        MessageData messageData = new MessageData();
                        messageData.setMessageType(MessageType.sqlReq);
                        messageData.getData().add(sql);
                        Connector.getInstance().send(messageData);
                        popHintDialog("已允许该用户借阅请求");
                        sql = "delete from RentRecord where RentID = " + DataContainer.managerents.get(cell.getIndex()).get(0);
                        messageData.getData().clear();
                        messageData.getData().add(sql);
                        messageData.setMessageType(MessageType.sqlReq);
                        Connector.getInstance().send(messageData);

                        DataContainer.managerents.remove(cell.getIndex());
                        rents.remove(cell.getIndex());
                    }
                });
                cell.getRentItem().getDeleteBtn().setOnMouseClicked((new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm in-out of rent!");
                        String sql = "delete from RentRecord where RentID = " + DataContainer.managerents.get(cell.getIndex()).get(0);
                        MessageData messageData = new MessageData();
                        messageData.getData().add(sql);
                        messageData.setMessageType(MessageType.sqlReq);
                        Connector.getInstance().send(messageData);


                        DataContainer.managerents.remove(cell.getIndex());
                        rents.remove(cell.getIndex());

                        popHintDialog("删除借阅申请成功");
                    }
                }));
                return cell;
            }
        });

        rentTable.getColumns().setAll(rentID, rUserID, userName, rBookName, rentDate, backDate, rentOp);
        rentTable.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);

        final TreeItem<RentTableItem> rentRoot = new RecursiveTreeItem<>(rents, RecursiveTreeObject::getChildren);
        rentTable.setRoot(rentRoot);
        rentTable.setShowRoot(false);

        /*****************fine table********************/


        JFXTreeTableColumn<FineTableItem, String> fineID = new JFXTreeTableColumn<>("罚款编号");
        fineID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FineTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FineTableItem, String> param) {
                return param.getValue().getValue().fineID;
            }
        });
        JFXTreeTableColumn<FineTableItem, String> fuserID = new JFXTreeTableColumn<>("用户名称");
        fuserID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FineTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FineTableItem, String> param) {
                return param.getValue().getValue().userName;
            }
        });
        JFXTreeTableColumn<FineTableItem, String> frentID = new JFXTreeTableColumn<>("借阅编号");
        frentID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FineTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FineTableItem, String> param) {
                return param.getValue().getValue().rentID;
            }
        });
        JFXTreeTableColumn<FineTableItem, String> fineMount = new JFXTreeTableColumn<>("罚款金额");
        fineMount.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FineTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FineTableItem, String> param) {
                return param.getValue().getValue().fineMount;
            }
        });
        JFXTreeTableColumn<FineTableItem, String> fineDate = new JFXTreeTableColumn<>("罚款日期");
        fineDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FineTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<FineTableItem, String> param) {
                return param.getValue().getValue().fineDate;
            }
        });
        JFXTreeTableColumn<FineTableItem, Boolean> fineOp = new JFXTreeTableColumn<>("操作");
        fineOp.setSortable(false);
        fineOp.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<FineTableItem, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<FineTableItem, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().getValue().btnHBox != null);
            }
        });
        fineOp.setCellFactory(new Callback<TreeTableColumn<FineTableItem, Boolean>, TreeTableCell<FineTableItem, Boolean>>() {
            @Override
            public TreeTableCell<FineTableItem, Boolean> call(TreeTableColumn<FineTableItem, Boolean> param) {
                FineTableCell cell = new FineTableCell();
                cell.getFineItem().getDetailBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm in time!");
//                        DetailDialog detailDialog = (DetailDialog)userViewController.getController(Main.UserDetailDialogID);
//                        detailDialog.getLabel1().setText("lalalala");
//
//                        userViewController.getStage(Main.UserDetailDialogID).setTitle("罚款详情");
//                        userViewController.setStage(Main.UserDetailDialogID);

                    }
                });
                cell.getFineItem().getOpBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm out of time!");
                    }
                });
                return cell;
            }
        });

        fines.add(new FineTableItem("1","lhm","2","2018","2019"));
        fines.add(new FineTableItem("1","lhm","2","lhm","hust"));

        fineTable.getColumns().setAll(fineID, frentID, fuserID, fineMount, fineDate, fineOp);
        fineTable.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);

        final TreeItem<FineTableItem> fineRoot = new RecursiveTreeItem<>(fines, RecursiveTreeObject::getChildren);
        fineTable.setRoot(fineRoot);
        fineTable.setShowRoot(false);

        /******************** back Table **********************/
        JFXTreeTableColumn<BackTableItem, String> bRentID = new JFXTreeTableColumn<>("借阅编号");
        bRentID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BackTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BackTableItem, String> param) {
                return param.getValue().getValue().rentID;
            }
        });
        JFXTreeTableColumn<BackTableItem, String> bUserID = new JFXTreeTableColumn<>("用户编号");
        bUserID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BackTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BackTableItem, String> param) {
                return param.getValue().getValue().userID;
            }
        });
        JFXTreeTableColumn<BackTableItem, String> bUserName = new JFXTreeTableColumn<>("用户名");
        bUserName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BackTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BackTableItem, String> param) {
                return param.getValue().getValue().userName;
            }
        });
        JFXTreeTableColumn<BackTableItem, String> bBookID = new JFXTreeTableColumn<>("图书名称");
        bBookID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BackTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BackTableItem, String> param) {
                return param.getValue().getValue().bookName;
            }
        });
        JFXTreeTableColumn<BackTableItem, String> bRentDate = new JFXTreeTableColumn<>("借阅日期");
        bRentDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BackTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BackTableItem, String> param) {
                return param.getValue().getValue().rentDate;
            }
        });
        JFXTreeTableColumn<BackTableItem, String> bBackDate = new JFXTreeTableColumn<>("应还日期");
        bBackDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BackTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<BackTableItem, String> param) {
                return param.getValue().getValue().backDate;
            }
        });
        JFXTreeTableColumn<BackTableItem, Boolean> backOp = new JFXTreeTableColumn<>("操作");
        backOp.setSortable(false);
        backOp.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<BackTableItem, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<BackTableItem, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().getValue().btnHBox != null);
            }
        });
        backOp.setCellFactory(new Callback<TreeTableColumn<BackTableItem, Boolean>, TreeTableCell<BackTableItem, Boolean>>() {
            @Override
            public TreeTableCell<BackTableItem, Boolean> call(TreeTableColumn<BackTableItem, Boolean> param) {
                BackTableCell cell = new BackTableCell();
                cell.getBackItem().getDetailBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm in rent!");
//                        userViewController.getStage(Main.UserDetailDialogID).setTitle("借阅详情");
//                        userViewController.setStage(Main.UserDetailDialogID);
                    }
                });
                cell.getBackItem().getOpBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm out of rent!");
                    }
                });
                return cell;
            }
        });

        backs.add(new BackTableItem("1","1","lhm","2","2018","2019"));
        backs.add(new BackTableItem("1","1","lhm","工具书","lhm","hust"));

        backTable.getColumns().setAll(bRentID, bUserID, bUserName, bBookID, bRentDate, bBackDate, backOp);
        backTable.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);

        final TreeItem<BackTableItem> backRoot = new RecursiveTreeItem<>(backs, RecursiveTreeObject::getChildren);
        backTable.setRoot(backRoot);
        backTable.setShowRoot(false);

        /******************* user table *************************/
        JFXTreeTableColumn<UserTableItem, String> uUserID = new JFXTreeTableColumn<>("用户编号");
        uUserID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserTableItem, String> param) {
                return param.getValue().getValue().userID;
            }
        });
        JFXTreeTableColumn<UserTableItem, String> uUserName = new JFXTreeTableColumn<>("用户名称");
        uUserName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserTableItem, String> param) {
                return param.getValue().getValue().userName;
            }
        });
        JFXTreeTableColumn<UserTableItem, String> uEmail = new JFXTreeTableColumn<>("用户邮箱");
        uEmail.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserTableItem, String> param) {
                return param.getValue().getValue().userEmail;
            }
        });
        JFXTreeTableColumn<UserTableItem, String> uDate = new JFXTreeTableColumn<>("注册日期");
        uDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserTableItem, String> param) {
                return param.getValue().getValue().userDate;
            }
        });
        JFXTreeTableColumn<UserTableItem, String> uJob = new JFXTreeTableColumn<>("职业");
        uJob.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserTableItem, String> param) {
                return param.getValue().getValue().userJob;
            }
        });
        JFXTreeTableColumn<UserTableItem, String> uTele = new JFXTreeTableColumn<>("电话");
        uTele.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<UserTableItem, String> param) {
                return param.getValue().getValue().userTele;
            }
        });
        JFXTreeTableColumn<UserTableItem, Boolean> userOp = new JFXTreeTableColumn<>("操作");
        userOp.setSortable(false);
        userOp.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<UserTableItem, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<UserTableItem, Boolean> param) {
                return new SimpleBooleanProperty(param.getValue().getValue().btnHBox != null);
            }
        });
        userOp.setCellFactory(new Callback<TreeTableColumn<UserTableItem, Boolean>, TreeTableCell<UserTableItem, Boolean>>() {
            @Override
            public TreeTableCell<UserTableItem, Boolean> call(TreeTableColumn<UserTableItem, Boolean> param) {
                UserTableCell cell = new UserTableCell();
                cell.getUserItem().getDetailBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm in rent!");
//                        userViewController.getStage(Main.UserDetailDialogID).setTitle("借阅详情");
//                        userViewController.setStage(Main.UserDetailDialogID);
                    }
                });
                cell.getUserItem().getAltBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm out of rent!");
                    }
                });
                cell.getUserItem().getDeleteBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                    }
                });
                return cell;
            }
        });

        users.add(new UserTableItem("1","lhm","2","2018","2019",""));
        users.add(new UserTableItem("1","lhm","工具书","lhm","hust",""));

        userTable.getColumns().setAll(uUserID, uUserName, uEmail, uTele , uJob, uDate, userOp);
        userTable.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);

        final TreeItem<UserTableItem> userRoot = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
        userTable.setRoot(userRoot);
        userTable.setShowRoot(false);

    }

    @Override
    public void setStageController(StageController stageController) {
        managerViewController = stageController;
    }

    public void onProfileButtonClicked() {
        managerViewController.getStage(Main.UserProfileViewID).setTitle("个人信息");
        managerViewController.setStage(Main.UserProfileViewID);
    }

    public void onAltpwdButtonClicked() {
        managerViewController.getStage(Main.UserAltpwdDialogID).setTitle("修改密码");
        managerViewController.setStage(Main.UserAltpwdDialogID);
    }

    public void setWelcomeLabel(String text) {
        welcomeLabel.setText(text);
    }

    public void onQuitButtonClicked() {
        managerViewController.closeStage(Main.ManagerViewID);
    }

    public void onRentSearchButtonClicked() {
        String sql = "select * from RentReqView ";
        ArrayList<String> alter = new ArrayList<>();
        String sqlRentUserID = rentUserIDFIeld.getText().trim().equals("") ? "" : "UserID = " + rentUserIDFIeld.getText().trim();
        String sqlRentID = rentIDField.getText().trim().equals("") ? "" :  "RentID = " + rentIDField.getText().trim();
        if (!sqlRentID.equals("")) alter.add(sqlRentID);
        if (!sqlRentUserID.equals("")) alter.add(sqlRentUserID);
        if (alter.size() > 0) sql = "select * from RentReqView where ";
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
        messageData.setMessageType(MessageType.sqlRentRecReq);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);

        rents.clear();
    }

    public void onReturnSearchButtonClicked() {
        String sql = "select * from BackReqView ";
        ArrayList<String> alter = new ArrayList<>();
        String sqlRentUserID = backUserIDField.getText().trim().equals("") ? "" : "UserID = " + rentUserIDFIeld.getText().trim();
        String sqlRentID = backRentIDField.getText().trim().equals("") ? "" :  "RentID = " + rentIDField.getText().trim();
        if (!sqlRentID.equals("")) alter.add(sqlRentID);
        if (!sqlRentUserID.equals("")) alter.add(sqlRentUserID);
        if (alter.size() > 0) sql = "select * from BackReqView where ";
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
        messageData.setMessageType(MessageType.sqlReturnRecReq);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);

        backs.clear();

    }

    public void onFineSearchButtonClicked() {
        String sql = "select * from FineReqView ";
        ArrayList<String> alter = new ArrayList<>();
        String sqlFineRentID = fineRentIDField.getText().trim().equals("") ? "" : "UserID = " + rentUserIDFIeld.getText().trim();
        String sqfineID = fineIDField.getText().trim().equals("") ? "" :  "RentID = " + rentIDField.getText().trim();
        if (!sqfineID.equals("")) alter.add(sqfineID);
        if (!sqlFineRentID.equals("")) alter.add(sqlFineRentID);
        if (alter.size() > 0) sql = "select * from FineReqView where ";
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
        messageData.setMessageType(MessageType.sqlFineRecReq);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);

        fines.clear();
    }

    public void onBookSearchButtonClicked() {
        String sql = "select * from BookReqView ";
        ArrayList<String> alter = new ArrayList<>();
        String sqlBookID = bookIDField.getText().trim().equals("") ? "" : "BookID = " + bookIDField.getText().trim();
        String sqlBookName = bookNameField.getText().trim().equals("") ? "" : "BookName = '" +  bookNameField.getText().trim() + "'";
        String sqlBookClass = bookClassComboList.getEditor().getText().trim().equals("") ? "" : "BookType = '" + bookClassComboList.getSelectionModel().getSelectedItem() + "'";
        String sqlBookAuthor = authorTield.getText().trim().equals("") ? "" : "AuthorName = '" + authorTield.getText().trim() + "'";
        String sqlPublisher = pubField.getText().trim().equals("") ? "" : "PubName = '" + pubField.getText().trim() + "'";
        System.out.println(bookClassComboList.getEditor().getText().trim());
        if (!sqlBookID.equals("")) alter.add(sqlBookID);
        if (!sqlBookName.equals("")) alter.add(sqlBookName);
        if (!sqlBookClass.equals("")) alter.add(sqlBookClass);
        if (!sqlBookAuthor.equals("")) alter.add(sqlBookAuthor);
        if (!sqlPublisher.equals("")) alter.add(sqlPublisher);
        if (alter.size() > 0) sql = "select * from BookReqView where ";
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
        messageData.setMessageType(MessageType.sqlBookRecReq);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);
        books.clear();
    }

    public void onUserSearchButtonClicked() {
        String sql = "select * from UserReqView ";
        ArrayList<String> alter = new ArrayList<>();
        String sqlUserID = UserIDField.getText().trim().equals("") ? "" : "UserID = " + rentUserIDFIeld.getText().trim();
        String sqlUserName = UserNameFIeld.getText().trim().equals("") ? "" :  "UserName = " + rentIDField.getText().trim();
        if (!sqlUserID.equals("")) alter.add(sqlUserID);
        if (!sqlUserName.equals("")) alter.add(sqlUserName);
        if (alter.size() > 0) sql = "select * from UserReqView where ";
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
        messageData.setMessageType(MessageType.sqlUserRecReq);
        messageData.getData().add(sql);
        Connector.getInstance().send(messageData);

        users.clear();

    }

    public void addRentTableItem(ArrayList<String> rent) {
        assert rent.size() >= 7;
        // (RentID, UserID, UserName, BookID, BookName, RentDate, ReturnDate)
        rents.add(new RentTableItem(rent.get(0), rent.get(1), rent.get(2), rent.get(4), rent.get(5), rent.get(6)));
    }

    public void addReturnTableItem(ArrayList<String> back) {
        assert back.size() >= 7;
        // (RentID, UserID, UserName, BookID, BookName, RetDate, ReturnDate)
        backs.add(new BackTableItem(back.get(0), back.get(1), back.get(2), back.get(4), back.get(5), back.get(6)));
    }

    public void addFineTableItem(ArrayList<String> fine) {
        assert fine.size() >= 6;
        // (FineID, RentID, UserID, UserName, FineMount, FineDate)
        fines.add(new FineTableItem(fine.get(0), fine.get(1), fine.get(3), fine.get(4), fine.get(5)));
    }

    public void addBookTableItem(ArrayList<String> book) {
        assert book.size() >= 11;
        // (BookID, BookName, BookType, AuthorName, PubName, PubDate, TotNum, LeftNum, Price, Score, Reviews)
        books.add(new BookTableItem(book.get(0), book.get(1), book.get(2), book.get(3), book.get(4), book.get(6)));
    }

    public void addUserTableItem(ArrayList<String> user) {
        assert user.size() >= 6;
        // (UserID, UserName, UserPwd, UserEmail, UserTele, UserJob, UserRegisterTime)
        users.add(new UserTableItem(user.get(0), user.get(1), user.get(3), user.get(4), user.get(5), user.get(6)));
    }

    private void setBookClassComboList() {
        MessageData messageData = new MessageData();
        messageData.setMessageType(MessageType.sqlMTypeReq);
        Connector.getInstance().send(messageData);
    }

    public void setBookClassComboList(ArrayList<String> items) {
        bookClassComboList.getItems().clear();
        for (String item: items) {
            bookClassComboList.getItems().add(item);
        }
    }

    public void popHintDialog(String hintContent) {
        JFXAlert alert = new JFXAlert((Stage) managerItemListView.getScene().getWindow());
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
