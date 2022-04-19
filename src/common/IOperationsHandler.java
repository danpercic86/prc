package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IOperationsHandler extends Remote {
    String getOperationInfo(int operationName) throws RemoteException;

    String performOperation(int option, double left, double right) throws RemoteException;
}
