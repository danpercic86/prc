package server;

import common.Operation;
import common.OperationType;

import java.util.ArrayList;
import java.util.List;

public class Database {
    static final List<Operation> operations = new ArrayList<>();

    private Database() {
    }

    public static synchronized Operation add(double left, double right, OperationType operationType) {
        var id = operations.size() + 1;
        var operation = new Operation(id, left, right, operationType);
        operations.add(operation);
        return operation;
    }

    public static Operation get(long operationNumber) {
        return operations.stream().filter(o -> o.getId() == operationNumber).findFirst().orElse(null);
    }
}
