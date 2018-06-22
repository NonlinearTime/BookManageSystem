package Network;

import Conponent.MessageData;
import Conponent.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;



public abstract class NetworkConnection {
    private Consumer<Serializable> onReceiveCallback;
    private ConnectionThread connectionThread = new ConnectionThread();
    private int connections = 0;

    public NetworkConnection(Consumer<Serializable> onReceiveCallback) {
        this.onReceiveCallback = onReceiveCallback;
        connectionThread.setDaemon(true);
    }

    public NetworkConnection() {
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
        private ServerSocket serverSocket;
        public ConnectionThread() {
        }
        @Override
        public void run() {
            try {
                if (!isServer()) {
                    Socket socket = new Socket(getIP(), getPort());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    this.out = out;
                    this.socket = socket;
                    this.socket.setTcpNoDelay(true);
                    while (true) {
                        Serializable data = (Serializable) in.readObject();
                        onReceiveCallback.accept(data);
                    }
                } else {
                    try {
                        System.out.println("ConnectionThread Create");
                        serverSocket = new ServerSocket(getPort());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Server");
                    System.out.println(serverSocket.getLocalSocketAddress());
                    while (true) {
                        Socket socket = serverSocket.accept();
                        System.out.println("Catch a connection");
                        socket.setTcpNoDelay(true);
                        SocketThread socketThread = new SocketThread(socket);
                        socketThread.start();
                        connections++;
                        System.out.println("目前连接数：" + connections);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error");
                onReceiveCallback.accept(new MessageData(MessageType.error, new ArrayList<>()));
            }
        }
    }
}
