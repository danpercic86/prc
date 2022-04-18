package client;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static final SocketClient socket;

    static {
        try {
            socket = new SocketClient("127.0.0.1", 9797);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static int getMenuOption() {
        System.out.println("MENIU: ");
        System.out.println("1: Adunare");
        System.out.println("2: Scădere");
        System.out.println("3: Inmulțire");
        System.out.println("4: Impărțire");
        System.out.println("5: Informații operație");
        System.out.println("0: Ieșire");

        Scanner in = new Scanner(System.in);

        return in.nextInt();
    }

    public static void main(String[] args) {
        int option = getMenuOption();

        while (option != 0) {
            socket.write(option);

            switch (option) {
                case 1, 2, 3, 4 -> {
                    System.out.println("Introduceți numerele:");

                    Scanner in = new Scanner(System.in);
                    socket.writeDouble(in.nextDouble());
                    socket.writeDouble(in.nextDouble());

                    System.out.println(socket.read());
                }
                case 5 -> {
                    System.out.println("Introduceți numărul operației:");

                    Scanner in = new Scanner(System.in);
                    socket.write(in.nextInt());

                    System.out.println(socket.read());
                }
                default -> System.out.println("Această opțiune nu există!");
            }

            option = getMenuOption();
        }

        socket.write(0);
        socket.close();
    }
}