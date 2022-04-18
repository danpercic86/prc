package client;

import common.IOHelper;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class SocketClient extends IOHelper {
    protected final InetAddress address;

    public SocketClient(String address, int port) throws IOException {
        super(new DatagramSocket(), port);
        socket.setSoTimeout(10000);
        this.address = InetAddress.getByName(address);
        System.out.println("Client started");
    }

    public void write(int value) {
        this.send(String.valueOf(value), this.address, this.port);
    }

    public void write(String value) {
        this.send(value, this.address, this.port);
    }

    @Override
    public String read() {
        packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.receive(packet);
            return new String(packet.getData(), 0, packet.getLength());
        } catch (SocketTimeoutException e) {
            return "timeout";
        } catch (IOException e) {
            System.out.println(e);
        }

        return "Eroare la citire!";
    }
}
