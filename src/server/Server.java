package server;

import common.Operation;
import common.OperationType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static final SocketServer socket;
    static final List<Operation> operations = new ArrayList<>();

    static {
        try {
            socket = new SocketServer(9797);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        int option = socket.readInt();

        while (option != 0) {
            System.out.println(option);

            if (option == 5) {
                sendOperationInfo();
            } else {
                switch (option) {
                    case 1 -> addOperation(OperationType.ADD);
                    case 2 -> addOperation(OperationType.SUBTRACT);
                    case 3 -> addOperation(OperationType.MULTIPLY);
                    case 4 -> addOperation(OperationType.DIVIDE);
                    default -> socket.write("Această opțiune nu există!");
                }
            }
            option = socket.readInt();
        }

        socket.close();
    }

    static void sendOperationInfo() {
        int operationNumber = socket.readInt();
        Operation operation = operations.stream().filter(o -> o.getId() == operationNumber).findFirst().orElse(null);

        if (operation != null) {
            socket.write(operation.toString());
        } else {
            socket.write("Nu există această operație!");
        }
    }

    static void addOperation(OperationType operationType) {
        var id = operations.size() + 1;
        var left = socket.readDouble();
        var right = socket.readDouble();
        var clientIp = socket.getClientIp();
        var clientPort = socket.getClientPort();
        var operation = new Operation(id, left, right, operationType, clientIp, clientPort);

        operations.add(operation);
        socket.write(String.valueOf(operation.getResult()));
        
        System.out.println(operation);
    }
}