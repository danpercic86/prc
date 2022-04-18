package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    static final ServerSocket socket;

    static {
        try {
            socket = new ServerSocket(9797);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Server started");
        try {
            while (true) {
                createThreadWhenConnectionAccepted();
            }
        } catch (IOException e) {
            System.out.println("Server stopped.");
        }
    }

    private static void createThreadWhenConnectionAccepted() throws IOException {
        var acceptedConnection = socket.accept();
        var serverSocketHelper = new ServerSocketHelper(acceptedConnection);
        new Thread(new OperationsHandler(serverSocketHelper)).start();
    }
}