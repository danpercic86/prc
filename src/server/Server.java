package server;

import jakarta.jms.*;

import javax.naming.InitialContext;
import java.util.Scanner;

public class Server {

    public static QueueSession session;
    private static QueueReceiver receiver;
    private static QueueConnection connection;

    public static void main(String[] args) throws JMSException, InterruptedException {
        initJms();
        OperationsHandler handler = new OperationsHandler(session);
        receiver.setMessageListener(handler);

        System.out.println("Server started");
        System.out.println("Enter 'q' to shutdown...");
        Scanner in = new Scanner(System.in);

        while (!in.next().equals("q")) {
            System.out.println("Enter 'q' to shutdown...");
            Thread.sleep(500);
        }

        connection.close();
    }

    private static void initJms() {
        try {
            InitialContext context = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("connectionFactory");
            connection = factory.createQueueConnection();
            connection.start();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            receiver = session.createReceiver((Queue) context.lookup("jms/queue"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}