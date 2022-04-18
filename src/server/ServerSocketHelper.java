package server;

import common.IOHelper;

import java.io.IOException;
import java.net.DatagramSocket;

public class ServerSocketHelper extends IOHelper {
    public ServerSocketHelper(int port) throws IOException {
        super(new DatagramSocket(port), port);
        System.out.println("Server started");
    }

    public String getMessage() {
        return new String(packet.getData(), 0, packet.getLength());
    }

    public void write(String message) {
        this.send(message, packet.getAddress(), packet.getPort());
    }

    public String getClientIp() {
        return packet.getAddress().toString().replace("/", "");
    }

    public int getClientPort() {
        return packet.getPort();
    }
}
