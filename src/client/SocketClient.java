package client;

import common.IOHelper;

import java.io.IOException;
import java.net.Socket;

public class SocketClient extends IOHelper {
    public SocketClient(String address, int port) throws IOException {
        super(new Socket(address, port));
        System.out.println("Client started");
    }

    public void write(int value) {
        try {
            out.writeInt(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeDouble(double value) {
        try {
            out.writeDouble(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String read() {
        try {
            return in.readUTF();
        } catch (IOException e) {
            System.out.println(e);
        }

        return "Eroare la citire!";
    }
}
