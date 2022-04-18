package common;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class IOHelper implements Closeable {
    protected final DatagramSocket socket;
    protected final int port;
    public DatagramPacket packet;
    protected byte[] buffer = new byte[512];

    protected IOHelper(DatagramSocket socket, int port) {
        this.socket = socket;
        this.port = port;
    }

    public void send(String message, InetAddress address, int port) {
        var bytes = message.getBytes();
        try {
            socket.send(new DatagramPacket(bytes, bytes.length, address, port));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public String read() {
        packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
            return new String(packet.getData(), 0, packet.getLength());
        } catch (IOException e) {
            System.out.println(e);
        }

        return "Eroare la citire!";
    }

    @Override
    public void close() {
        socket.close();
        System.out.println("Connection closed");
    }
}
