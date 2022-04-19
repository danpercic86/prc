package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws RemoteException {
        OperationsHandler handler = new OperationsHandler();
        Registry registry = LocateRegistry.createRegistry(1099);
        System.out.println("Server started");
        registry.rebind("operations", handler);
    }
}