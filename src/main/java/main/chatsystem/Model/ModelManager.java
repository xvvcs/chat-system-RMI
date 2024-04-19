
package main.chatsystem.Model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import javafx.application.Platform;
import main.chatsystem.Client.ChatClient;
import main.chatsystem.Client.Listener;
import main.chatsystem.Server.ChatImplementation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ModelManager implements Model, RemotePropertyChangeListener {
    private final PropertyChangeSupport support;
    private final ChatClient server;
    private User user;

    public ModelManager(ChatClient server) throws IOException {
        this.server = server;
        this.support = new PropertyChangeSupport(this);
        RemotePropertyChangeListener exported = (RemotePropertyChangeListener) UnicastRemoteObject.exportObject(this,0);
        this.server.addPropertyChangeListener(exported);
    }

    @Override
    public void disconnect(User user) throws IOException {
        try{
            this.server.disconnect(user);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public void login(String username, String password) throws IOException {
        user = new User(username, password);
        server.login(username, password);
    }

    @Override
    public void sendMessage(String content, User user) throws IOException, InterruptedException {
        try{
            Message message = new Message(content, this.user);
            server.sendMessage(message.message(), this.user);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(RemotePropertyChangeEvent evt) throws RemoteException {
        Platform.runLater(() -> {
            System.out.println("wejkgfjhwgui3wghrt");
            if ("UserLeft".equals(evt.getPropertyName())) {
                support.firePropertyChange("UserLeft", null, evt.getNewValue());
            } else if ("MessageSent".equals(evt.getPropertyName())) {
                System.out.println("message sent LISTENER: " + evt.getNewValue());
                support.firePropertyChange("MessageSent", null, evt.getNewValue());
            } else if ("UserLoggedIn".equals(evt.getPropertyName())){
                System.out.println("UserAdded LISTENER: " + evt.getNewValue());
                support.firePropertyChange("UserLoggedIn", null, evt.getNewValue());
            } else if ("UserCount".equals(evt.getPropertyName())) {
                support.firePropertyChange("UserCount",null,evt.getNewValue());
            }
        });
    }
}
