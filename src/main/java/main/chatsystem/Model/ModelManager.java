
package main.chatsystem.Model;

import javafx.application.Platform;
import main.chatsystem.Client.ChatClient;
import main.chatsystem.Client.Listener;
import main.chatsystem.Server.ChatImplementation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class ModelManager implements Model, PropertyChangeListener {
    private final PropertyChangeSupport support;
    private final ChatClient client;
    private User user;
    private Listener listener;

    public ModelManager(ChatClient client) throws IOException {
        this.client = client;
        this.listener = new Listener(client);
        this.support = new PropertyChangeSupport(this);
        listener.addPropertyChangeListener(this);
    }

    @Override
    public void disconnect(User user) throws IOException {
        try{
            this.client.disconnect(user);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public void login(String username, String password) throws IOException {
        this.user = new User(username, password);
        client.login(username, password);
    }

    @Override
    public void sendMessage(String content, User user) throws IOException, InterruptedException {
        try{
            Message message = new Message(content, this.user);
            client.sendMessage(message.message(), this.user);
        } catch (Exception e){
            e.printStackTrace();
        }

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
            if ("UserLeft".equals(evt.getPropertyName())) {
                support.firePropertyChange("UserLeft", null, evt.getNewValue());
            } else if ("MessageSent".equals(evt.getPropertyName())) {
                System.out.println("Message MODEl");
                support.firePropertyChange("MessageSent", null, evt.getNewValue());
            } else if ("UserLoggedIn".equals(evt.getPropertyName())){
                System.out.println("User MODEL:");// DOESN'T EVEN GET CALLED
                support.firePropertyChange("UserLoggedIn", null, evt.getNewValue());
            }
        });
    }

}
