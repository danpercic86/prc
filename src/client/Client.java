package client;

import common.IOperationsHandler;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    private static IOperationsHandler handler;

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

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        handler = (IOperationsHandler) Naming.lookup("rmi://localhost:1099/operations");
        int option = getMenuOption();

        while (option != 0) {
            switch (option) {
                case 1, 2, 3, 4 -> handleOperation(option);
                case 5 -> printOperationInfo();
                default -> System.out.println("Această opțiune nu există!");
            }

            option = getMenuOption();
        }
    }

    private static void printOperationInfo() {
        System.out.println("Introduceți numărul operației:");

        Scanner in = new Scanner(System.in);
        try {
            var result = handler.getOperationInfo(in.nextInt());
            System.out.println(result);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleOperation(int option) {
        System.out.println("Introduceți numerele:");

        Scanner in = new Scanner(System.in);
        try {
            var result = handler.performOperation(option, in.nextDouble(), in.nextDouble());
            System.out.println(result);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
}