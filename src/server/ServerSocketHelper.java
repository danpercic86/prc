package server;

import common.IOHelper;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerSocketHelper extends IOHelper {
    public ServerSocketHelper(Socket socket) {
        super(socket);
        System.out.println("New socket started");
    }

    public void write(String string) {
        try {
            out.writeUTF(string);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public int readInt() {
        try {
            return in.readInt();
        } catch (IOException e) {
            System.out.println(e);
        }

        return 0;
    }

    public double readDouble() {
        try {
            return in.readDouble();
        } catch (IOException e) {
            System.out.println(e);
        }

        return 0;
    }

    public String getClientIp() {
        return ((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress().toString().replace("/", "");
    }

    public int getClientPort() {
        return ((InetSocketAddress) socket.getRemoteSocketAddress()).getPort();
    }
}
