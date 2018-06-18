package Conponent;

import Network.Server;
import com.sun.source.doctree.SinceTree;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static Conponent.MessageData.*;

public class Connector {
    private Server server = new Server(8888);

    private static Connector ourInstance = new Connector();

    public static Connector getInstance() {
        return ourInstance;
    }

    private Connector() {
    }

    public void start() {
        server.startConnection();
    }

    public void close() {
        try {
            server.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}