package main.chatsystem.Client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import main.chatsystem.Model.Message;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//TODO: Probably there is an issue with this listener that User is null all the time.
// Probably propertyChange and support.fire... don't push info about user further.

public class Listener implements RemotePropertyChangeListener {
    protected Listener(ChatClient chat) throws RemoteException {
        Listener exported = (Listener) UnicastRemoteObject.exportObject(this,0);
        chat.addPropertyChangeListener(exported);
    }
    @Override
    public void propertyChange(RemotePropertyChangeEvent remotePropertyChangeEvent) throws RemoteException {
        Platform.runLater(() -> {
            try {
                System.out.println("Server received " + remotePropertyChangeEvent.getNewValue());
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error processing remote property change event: " + e.getMessage());
            }
        });

    }
}
