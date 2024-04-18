package main.chatsystem.Client;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import javafx.application.Platform;
import main.chatsystem.Model.Message;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//TODO: Probably there is an issue with this listener that User is null all the time.
// Probably propertyChange and support.fire... don't push info about user further.

public class Listener implements RemotePropertyChangeListener {
    private PropertyChangeSupport support;

    public Listener(ChatClient chat) throws RemoteException {
        RemotePropertyChangeListener exported = (RemotePropertyChangeListener) UnicastRemoteObject.exportObject(this,0);
        chat.addPropertyChangeListener(exported);
        this.support = new PropertyChangeSupport(this);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }
    @Override
    public void propertyChange(RemotePropertyChangeEvent evt) throws RemoteException {
        Platform.runLater(() -> {
            if ("UserLeft".equals(evt.getPropertyName())) {
                support.firePropertyChange("UserLeft", null, evt.getNewValue());
            } else if ("MessageSent".equals(evt.getPropertyName())) {
                System.out.println("message sent LISTENER: " + evt.getNewValue());
                support.firePropertyChange("MessageSent", null, evt.getNewValue());
            } else if ("UserLoggedIn".equals(evt.getPropertyName())){
                System.out.println("UserAdded LISTENER: " + evt.getNewValue());
                support.firePropertyChange("UserLoggedIn", null, evt.getNewValue());
            }
        });

    }
}
