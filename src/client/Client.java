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
            switch (option) {
                case 1, 2, 3, 4 -> handleOperation(option);
                case 5 -> requestOperationInfo(option);
                default -> System.out.println("Această opțiune nu există!");
            }

            option = getMenuOption();
        }

        socket.close();
    }

    private static void handleOperation(int option) {
        System.out.println("Introduceți numerele:");

        Scanner in = new Scanner(System.in);
        var firstValue = in.nextDouble();
        var secondValue = in.nextDouble();
        var message = String.format("%d,%f,%f", option, firstValue, secondValue);
        String response = trySendRequest(in, message);

        System.out.println(response != null ? response : "Operația nu a fost efectuată.");
    }

    private static String trySendRequest(Scanner in, String message) {
        socket.write(message);
        var response = socket.read();
        if (response.equals("timeout")) {
            System.out.println("Serverul nu  a răspuns. Retrimitere cerere? d/n");

            if (!in.next().equals("d")) {
                return null;
            }

            socket.write(message);

            response = socket.read();
            if (response.equals("timeout")) {
                System.out.println("Serverul nu a răspuns. Încercați mai târziu.");
                return null;
            }
        }
        return response;
    }

    private static void requestOperationInfo(int option) {
        System.out.println("Introduceți numărul operației:");

        Scanner in = new Scanner(System.in);
        socket.write(String.format("%d,%d", option, in.nextInt()));

        System.out.println(socket.read());
    }
}