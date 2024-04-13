
package main.chatsystem.Model;

import javafx.application.Platform;
import main.chatsystem.Client.ChatClient;
import main.chatsystem.Server.ChatImplementation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class ModelManager implements Model, PropertyChangeListener {
    private final PropertyChangeSupport support;
    private final ChatClient client;

    public ModelManager(ChatClient client) throws IOException {
        this.client = client;
        this.support = new PropertyChangeSupport(this);
    }

    @Override
    public void disconnect() throws IOException {
        try{
            this.client.disconnect();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public void login(String username, String password) throws IOException {

        client.login(username, password);
    }

    @Override
    public void sendMessage(String content, User user) throws IOException, InterruptedException {
        Message message = new Message(content, user);
        client.sendMessage(message.message(), user);
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
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if ("UserAdded".equals(evt.getPropertyName())) {
                support.firePropertyChange("UserAdded", null, evt.getNewValue());
            } else if ("MessageSent".equals(evt.getPropertyName())) {
                support.firePropertyChange("MessageSent", null, evt.getNewValue());
            } else if ("UserLoggedIn".equals(evt.getPropertyName())){
                support.firePropertyChange("UserLoggedIn", null, evt.getNewValue());
            } else if("broadcast".equals(evt.getPropertyName())){
                support.firePropertyChange("broadcast",null,evt.getNewValue());
            }
        });
    }

}
