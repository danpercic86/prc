package client;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class Client {
    private static final Scanner in = new Scanner(System.in);
    private static QueueSender sender;
    private static QueueReceiver receiver;
    private static TextMessage message;
    private static QueueConnection connection;

    static int getMenuOption() {
        System.out.println("MENIU: ");
        System.out.println("1: Adunare");
        System.out.println("2: Scădere");
        System.out.println("3: Inmulțire");
        System.out.println("4: Impărțire");
        System.out.println("5: Informații operație");
        System.out.println("0: Ieșire");

        return in.nextInt();
    }

    private static void initJms() {
        try {
            InitialContext context = new InitialContext();
            QueueSession session = getQueueSession(context);
            sender = session.createSender((Queue) context.lookup("jms/queue"));
            message = session.createTextMessage();

            var tmpQueue = session.createTemporaryQueue();
            receiver = session.createReceiver(tmpQueue);
            message.setJMSReplyTo(tmpQueue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static QueueSession getQueueSession(InitialContext context) throws NamingException, JMSException {
        QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("connectionFactory");
        connection = factory.createQueueConnection();
        connection.start();

        return connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public static void main(String[] args) throws JMSException {
        initJms();
        int option = getMenuOption();

        while (option != 0) {
            switch (option) {
                case 1, 2, 3, 4 -> handleOperation(option);
                case 5 -> printOperationInfo();
                default -> System.out.println("Această opțiune nu există!");
            }

            option = getMenuOption();
        }

        connection.close();
    }

    private static void printOperationInfo() {
        System.out.println("Introduceți numărul operației:");

        send(5 + "," + in.nextInt());
    }

    private static void handleOperation(int option) {
        System.out.println("Introduceți numerele:");

        send(option + "," + in.nextDouble() + "," + in.nextDouble());
    }

    private static void send(String value) {
        try {
            message.setText(value);
            sender.send(message);
            var result = (TextMessage) receiver.receive();
            System.out.println(result.getText());
        } catch (JMSException e) {
            System.out.println(e.getMessage());
        }
    }
}