package Views.user;

import Conponent.MessageData;
import Conponent.MessageType;
import StageController.ControlledStage;
import StageController.StageController;
import Views.Conponent.ListItem;
import Views.data.DataContainer;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import sample.Main;

import javax.swing.text.DefaultEditorKit;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.print.Book;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class UserViewController implements ControlledStage, Initializable {
    private StageController userViewController;
    private AnchorPane currentTab;
    private HashMap<ListItem, AnchorPane> tabController = new HashMap<>();
    private ObservableList<BookTableItem> books =  FXCollections.observableArrayList();
    private ObservableList<RentTableItem> rents =  FXCollections.observableArrayList();
    private ObservableList<TimeTableItem> fines =  FXCollections.observableArrayList();

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



    private ObservableList<ListItem> listView = FXCollections.observableArrayList();


    @Override
    public void setStageController(StageController stageController) {
        userViewController = stageController;
    }

    class BookTableItem extends RecursiveTreeObject<BookTableItem> {
        StringProperty  bookID, bookName, bookType, bookAuthor, bookPub, bookNum;
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
        StringProperty  rentID, userID, bookID, rentDate, backDate, isBack;
        HBox btnHBox;
        JFXButton detailBtn;
        JFXButton opBtn;
        public RentTableItem(String rentID, String userID, String bookID, String rentDate, String backDate, String isBack) {
            this.rentID = new SimpleStringProperty(rentID);
            this.userID = new SimpleStringProperty(userID);
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
        StringProperty  fineID, userID, rentID, fineMount, fineDate, isSolved;
        HBox btnHBox;
        JFXButton detailBtn;
        JFXButton opBtn;
        public TimeTableItem(String fineID, String userID, String rentID, String fineMount, String fineDate, String isSolved) {
            this.fineID = new SimpleStringProperty(fineID);
            this.userID = new SimpleStringProperty(userID);
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
                        detailDialogController.getLabel1().setText("书籍编号：" + books.get(cell.getIndex()).bookID.getValue());
                        detailDialogController.getLabel2().setText("书籍名称：" + books.get(cell.getIndex()).bookName.getValue());
                        detailDialogController.getLabel3().setText("书籍类别：" + books.get(cell.getIndex()).bookType.getValue());
                        detailDialogController.getLabel4().setText("作者：" + books.get(cell.getIndex()).bookAuthor.getValue());
                        detailDialogController.getLabel5().setText("出版社：" + books.get(cell.getIndex()).bookPub.getValue());
                        detailDialogController.getLabel6().setText("出版时间：" + books.get(cell.getIndex()).bookID.getValue());
                        detailDialogController.getLabel7().setText("馆藏数量：" + books.get(cell.getIndex()).bookID.getValue());
                        detailDialogController.getLabel8().setText("在馆数量：" + books.get(cell.getIndex()).bookID.getValue());
                        detailDialogController.getLabel9().setText("价格：" + books.get(cell.getIndex()).bookID.getValue());
                        detailDialogController.getLabel10().setText("评分：" + books.get(cell.getIndex()).bookID.getValue());
                        detailDialogController.getLabel11().setText("评论数量：" + books.get(cell.getIndex()).bookID.getValue());
//                        detailDialogController.getLabel12().setText("书籍编号：" + books.get(cell.getIndex()).bookID.getValue());
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
        JFXTreeTableColumn<RentTableItem, String> userID = new JFXTreeTableColumn<>("用户编号");
        userID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RentTableItem, String> param) {
                return param.getValue().getValue().userID;
            }
        });
        JFXTreeTableColumn<RentTableItem, String> rBookID = new JFXTreeTableColumn<>("图书编号");
        rBookID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RentTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RentTableItem, String> param) {
                return param.getValue().getValue().bookID;
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
                    }
                });
                cell.getRentItem().getOpBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm out of rent!");
                    }
                });
                return cell;
            }
        });

        rents.add(new RentTableItem("1","lhm","2","2018","2019","未还"));
        rents.add(new RentTableItem("1","lhm","工具书","lhm","hust","11"));

        rentTable.getColumns().setAll(rentID, userID, rBookID, rentDate, backDate, isBack, rentOp);
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
        JFXTreeTableColumn<TimeTableItem, String> fuserID = new JFXTreeTableColumn<>("用户编号");
        fuserID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTableItem, String> param) {
                return param.getValue().getValue().userID;
            }
        });
        JFXTreeTableColumn<TimeTableItem, String> frentID = new JFXTreeTableColumn<>("借阅编号");
        frentID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TimeTableItem, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TimeTableItem, String> param) {
                return param.getValue().getValue().rentID;
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
                        DetailDialog detailDialog = (DetailDialog)userViewController.getController(Main.UserDetailDialogID);
                        detailDialog.getLabel1().setText("lalalala");

                        userViewController.getStage(Main.UserDetailDialogID).setTitle("罚款详情");
                        userViewController.setStage(Main.UserDetailDialogID);

                    }
                });
                cell.getTimeItem().getOpBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("I'm out of time!");
                    }
                });
                return cell;
            }
        });

        fines.add(new TimeTableItem("1","lhm","2","2018","2019","未还"));
        fines.add(new TimeTableItem("1","lhm","2","lhm","hust","11"));

        fineTable.getColumns().setAll(fineID, fuserID, frentID, fineMount, fineDate, isSolved, fineOp);
        fineTable.setColumnResizePolicy(JFXTreeTableView.CONSTRAINED_RESIZE_POLICY);

        final TreeItem<TimeTableItem> fineRoot = new RecursiveTreeItem<>(fines, RecursiveTreeObject::getChildren);
        fineTable.setRoot(fineRoot);
        fineTable.setShowRoot(false);
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

}
