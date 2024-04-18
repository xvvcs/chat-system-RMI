package main.chatsystem.Client;

import dk.via.remote.observer.RemotePropertyChangeListener;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.User;

import java.io.IOException;
import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
    void disconnect(User user) throws RemoteException, IOException;
   void login(String username, String password) throws RemoteException, IOException;
    void sendMessage(String message, User user) throws RemoteException, IOException;

    void addPropertyChangeListener(RemotePropertyChangeListener<Message> listener) throws RemoteException;

    void removePropertyChangeListener(RemotePropertyChangeListener<Message> listener) throws RemoteException;

}
