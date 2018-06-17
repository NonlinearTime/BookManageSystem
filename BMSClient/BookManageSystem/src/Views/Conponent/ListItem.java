package Views.Conponent;

import Views.user.UserViewController;
import com.jfoenix.controls.JFXListCell;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ListItem {
    HBox hbox;
    Label label;
    ImageView img;
    Pane pane;
    Label background;
    double height;

    public static class Cell extends  JFXListCell<ListItem> {
        public Cell () {
            super();
        }

        public void updateItem(ListItem item, boolean empty) {
            super.updateItem(item,empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                setGraphic(item.getGraphic());
            }
        }
    }

    public ListItem(String imgName, String s, double h) throws FileNotFoundException {
        hbox = new HBox();
        pane = new Pane();
        label = new Label(s);
        img = new ImageView(new Image(new FileInputStream(imgName)));
        height = h;

        label.setPrefHeight(height);
        label.setTextAlignment(TextAlignment.LEFT);
        label.setFont(new Font(".PingFang SC", height * 0.8));

        background = new Label();
        ImageView bgp = new ImageView(new Image(new FileInputStream("/home/haines/IdeaProjects/BookManageSystem/BMSClient/BookManageSystem/src/pictures/arrow.png")));
        bgp.setFitWidth(height / 2);
        bgp.setFitHeight(height / 2);
        background.setGraphic(bgp);


        img.setFitHeight(height);
        img.setFitWidth(height);
        hbox.getChildren().addAll(img, label, pane, background);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(height);
        hbox.setHgrow(pane, Priority.ALWAYS);
    }

    public HBox getGraphic() {
        return hbox;
    }

    public double getHeight() {
        return height;
    }
}