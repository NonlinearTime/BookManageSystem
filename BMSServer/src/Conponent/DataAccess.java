package Conponent;

import java.sql.*;

public class DataAccess {
    private static Connection connection;
    private static Connection connection1;

    private static Statement statement;
    private static Statement statement1;

    private static DataAccess ourInstance = new DataAccess();

    public static DataAccess getInstance() {
        return ourInstance;
    }

    private DataAccess() {
        System.out.println("Connecting to db.");
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookDB?useUnicode=true&characterEncoding=utf-8", "root", "lhm199710");
            connection1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/BookDB?useUnicode=true&characterEncoding=utf-8", "root", "lhm199710");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            statement = connection.createStatement();
            statement1 = connection1.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet Query(String sql) {
        ResultSet rs = null;
        try {
            if (sql.contains("update")) {statement.execute(sql);}
            else rs = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static ResultSet Query1(String sql) {
        ResultSet rs = null;
        try {
            if (sql.contains("update")) {statement1.execute(sql);}
            else rs = statement1.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static Connection getConnection() {return connection;}
    public static Connection getConnection1() {return connection1;}

}
