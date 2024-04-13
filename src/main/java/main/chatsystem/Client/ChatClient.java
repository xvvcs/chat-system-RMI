package main.chatsystem.Client;

import main.chatsystem.Model.User;

import java.io.IOException;
import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
    void disconnect() throws RemoteException;
    boolean login(String username, String password) throws RemoteException;
    void sendMessage(String message, User user) throws RemoteException;

    void addUser(User user) throws RemoteException;
    void addPropertyChangeListener(PropertyChangeListener listener ) throws RemoteException;
    void removePropertyChangeListener(PropertyChangeListener listener) throws RemoteException;
    void receiveBroadcast(String message) throws RemoteException;
}
