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


    }
}
