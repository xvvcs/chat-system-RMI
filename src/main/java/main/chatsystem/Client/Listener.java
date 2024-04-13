package main.chatsystem.Client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import main.chatsystem.Model.Message;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Listener implements RemotePropertyChangeListener<Message> {
    protected Listener(ChatClient chat) throws RemoteException {
        Listener exported = (Listener) UnicastRemoteObject.exportObject(this,0);
        chat.addPropertyChangeListener(exported);
    }
    @Override
    public void propertyChange(RemotePropertyChangeEvent<Message> remotePropertyChangeEvent) throws RemoteException {
        Platform.runLater(() -> {
            System.out.println("Server received " + remotePropertyChangeEvent.getNewValue());
        });

    }
}
