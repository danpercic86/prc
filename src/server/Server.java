package server;

import common.Operation;
import common.OperationType;

import java.io.IOException;

public class Server {
    static final ServerSocketHelper socket;

    static {
        try {
            socket = new ServerSocketHelper(9797);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        var request = socket.read().split(",");
        while (true) {
            var option = Integer.parseInt(request[0]);
            System.out.println(option);

            if (option == 5) {
                sendOperationInfo(Integer.parseInt(request[1]));
            } else {
                var left = Double.parseDouble(request[1]);
                var right = Double.parseDouble(request[2]);
                selectOperationToAdd(option, left, right);
            }

            request = socket.read().split(",");
        }
    }

    static void sendOperationInfo(int operationNumber) {
        Operation operation = Database.get(operationNumber);

        if (operation != null) {
            socket.write(operation.toString());
        } else {
            socket.write("Nu există această operație!");
        }
    }

    private static void selectOperationToAdd(int option, double left, double right) {
        switch (option) {
            case 1 -> addOperation(left, right, OperationType.ADD);
            case 2 -> addOperation(left, right, OperationType.SUBTRACT);
            case 3 -> addOperation(left, right, OperationType.MULTIPLY);
            case 4 -> addOperation(left, right, OperationType.DIVIDE);
            default -> socket.write("Această opțiune nu există!");
        }
    }

    static void addOperation(double left, double right, OperationType operationType) {
        var clientIp = socket.getClientIp();
        var clientPort = socket.getClientPort();

        var operation = Database.add(left, right, operationType, clientIp, clientPort);
        socket.write(String.valueOf(operation.getResult()));

        System.out.println(operation);
    }
}