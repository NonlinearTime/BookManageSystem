package Conponent;

import java.sql.*;

public class DataAccess {
    private static Connection connection;
    private static Statement statement;

    private static DataAccess ourInstance = new DataAccess();

    public static DataAccess getInstance() {
        return ourInstance;
    }

    private DataAccess() {
        System.out.println("Connecting to db.");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookDB", "root", "lhm199710");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet Query(String sql) {
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static Connection getConnection() {return connection;}

}
