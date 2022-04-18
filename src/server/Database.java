package server;

import common.Operation;
import common.OperationType;

import java.util.ArrayList;
import java.util.List;

public class Database {
    static final List<Operation> operations = new ArrayList<>();

    private Database() {
    }

    public static Operation add(double left, double right, OperationType operationType, String clientIp, int clientPort) {
        var id = operations.size() + 1;
        var operation = new Operation(id, left, right, operationType, clientIp, clientPort);
        operations.add(operation);
        return operation;
    }

    public static Operation get(long operationNumber) {
        return operations.stream().filter(o -> o.getId() == operationNumber).findFirst().orElse(null);
    }
}
