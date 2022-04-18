package server;

import common.Operation;
import common.OperationType;

public class OperationsHandler implements Runnable {
    private final ServerSocketHelper socket;

    public OperationsHandler(ServerSocketHelper socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        int option = socket.readInt();

        while (option != 0) {
            System.out.println(option);

            if (option == 5) {
                sendOperationInfo();
            } else synchronized (this) {
                selectOperationToAdd(option);
            }

            option = socket.readInt();
        }

        socket.close();
    }

    private void selectOperationToAdd(int option) {
        switch (option) {
            case 1 -> addOperation(OperationType.ADD);
            case 2 -> addOperation(OperationType.SUBTRACT);
            case 3 -> addOperation(OperationType.MULTIPLY);
            case 4 -> addOperation(OperationType.DIVIDE);
            default -> socket.write("Această opțiune nu există!");
        }
    }

    void addOperation(OperationType operationType) {
        var left = socket.readDouble();
        var right = socket.readDouble();
        var clientIp = socket.getClientIp();
        var clientPort = socket.getClientPort();

        var operation = Database.add(left, right, operationType, clientIp, clientPort);
        socket.write(String.valueOf(operation.getResult()));

        System.out.println(operation);
    }

    void sendOperationInfo() {
        int operationNumber = socket.readInt();
        Operation operation = Database.get(operationNumber);

        if (operation != null) {
            socket.write(operation.toString());
        } else {
            socket.write("Nu există această operație!");
        }
    }
}
