package common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class IOHelper implements IClosable {
    protected DataOutputStream out;
    protected DataInputStream in;
    protected Socket socket;

    protected IOHelper(Socket socket) {
        this.socket = socket;
        init();
    }

    void init() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Eroare la închidere!");
        }

        System.out.println("Connection closed");
    }
}
