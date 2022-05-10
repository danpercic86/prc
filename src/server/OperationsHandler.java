package server;

import common.IOperationsHandler;
import common.Operation;
import common.OperationType;
import jakarta.jms.*;

public class OperationsHandler implements IOperationsHandler, MessageListener {

    private final QueueSession session;

    public OperationsHandler(QueueSession session) {
        super();
        this.session = session;
    }

    public String performOperation(int option, double left, double right) {
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
        var operation = Database.add(left, right, operationType);
        System.out.println(operation);
        return String.valueOf(operation.getResult());
    }

    public String getOperationInfo(int operationNumber) {
        Operation operation = Database.get(operationNumber);
        return operation != null ? operation.toString() : "Această operatie nu există!";
    }

    @Override
    public void onMessage(Message message) {
        try {
            var text = ((TextMessage) message).getText();
            System.out.println(text);
            var response = getResponse(text);
            try (var sender = session.createSender((TemporaryQueue) message.getJMSReplyTo())) {
                sender.send(session.createTextMessage(response));
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private String getResponse(String text) {
        var request = text.split(",");
        var option = Integer.parseInt(request[0]);
        System.out.println(option);

        if (option == 5) {
            return getOperationInfo(Integer.parseInt(request[1]));
        } else {
            var left = Double.parseDouble(request[1]);
            var right = Double.parseDouble(request[2]);
            return performOperation(option, left, right);
        }
    }
}
