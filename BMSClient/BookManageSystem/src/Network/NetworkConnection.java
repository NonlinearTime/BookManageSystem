package Network;

import javax.swing.plaf.TableHeaderUI;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.ServerCloneException;
import java.util.function.Consumer;

public abstract class NetworkConnection {
    private Consumer<Serializable> onReceiveCallback;
    private ConnectionThread connectionThread = new ConnectionThread();

    public NetworkConnection(Consumer<Serializable> onReceiveCallback) {
        this.onReceiveCallback = onReceiveCallback;
        connectionThread.setDaemon(true);
    }

    public void startConnection() {
        connectionThread.start();
    }

    public void send (Serializable data) throws IOException {
        connectionThread.out.writeObject(data);
    }

    public void closeConnection() throws IOException {
        connectionThread.socket.close();
    }

    protected abstract boolean isServer();
    protected abstract String getIP();
    protected abstract int getPort();

    private class ConnectionThread extends Thread {
        private Socket socket;
        private ObjectOutputStream out;
        @Override
        public void run() {
            try {
                ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
                assert server != null;
                Socket socket = isServer() ? server.accept() : new Socket(getIP() ,getPort());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                this.out = out;
                this.socket = socket;
                this.socket.setTcpNoDelay(true);

                while (true) {
                    Serializable data = (Serializable) in.readObject();
                    onReceiveCallback.accept(data);
                }

            } catch (Exception e) {
                onReceiveCallback.accept("Connection closed");
            }

        }
    }
}
