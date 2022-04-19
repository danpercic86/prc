package server;

import common.IOperationsHandler;
import common.Operation;
import common.OperationType;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class OperationsHandler extends UnicastRemoteObject implements IOperationsHandler {

    public OperationsHandler() throws RemoteException {
        super();
    }

    public synchronized String performOperation(int option, double left, double right) {
        switch (option) {
            case 1 -> {
                return addOperation(left, right, OperationType.ADD);
            }
            case 2 -> {
                return addOperation(left, right, OperationType.SUBTRACT);
            }
            case 3 -> {
                return addOperation(left, right, OperationType.MULTIPLY);
            }
            case 4 -> {
                return addOperation(left, right, OperationType.DIVIDE);
            }
            default -> {
                return "Această opțiune nu există!";
            }
        }
    }

    private String addOperation(double left, double right, OperationType operationType) {
        var host = "";
        try {
            host = getClientHost();
            System.out.println(host);
        } catch (ServerNotActiveException e) {
            return "Eroare la obținerea adresei IP a clientului!";
        }
        var operation = Database.add(left, right, operationType, host);
        System.out.println(operation);
        return String.valueOf(operation.getResult());
    }

    public String getOperationInfo(int operationNumber) {
        Operation operation = Database.get(operationNumber);
        return operation != null ? operation.toString() : "Această operatie nu există!";
    }
}
