package sample;

import Conponent.Connector;
import Conponent.DataAccess;
import Conponent.Email;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
//        Email email = new Email("luohaimin.com@qq.com","hainesluo@gmail.com");
//        email.SendEmail("1111");
        Connector.getInstance().start();
        launch(args);
    }

}
