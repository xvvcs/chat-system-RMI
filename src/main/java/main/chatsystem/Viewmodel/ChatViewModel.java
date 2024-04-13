package main.chatsystem.Viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.Model;
import main.chatsystem.Model.User;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ChatViewModel implements PropertyChangeListener {
    private final Model model;
    private User user;
    private Message messageObject;
    private final ListProperty<String> messages;
    private final SimpleStringProperty message;
    private StringProperty error;
    private final PropertyChangeSupport support;
    public ChatViewModel(Model model)
    {

        this.model = model;
        this.user = null;
        this.error = new SimpleStringProperty("");
        this.message = new SimpleStringProperty("");
        this.messages = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.model.addPropertyChangeListener(this);
        this.model.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("UserLoggedIn")){
                user = (User) evt.getNewValue();
            }
        });
        this.support = new PropertyChangeSupport(this);
    }

    public String getNickname(){
        if(user!= null){
            return user.nickname();
        }
        else{
            return "";
        }
    }
    public void sendMessage()
    {
        try
        {
            model.sendMessage(message.get(), user);
        }
        catch (Exception e)
        {
            error.setValue(e.getMessage());
        }
    }
    public void addMessage(String message){
        //Message messageNew = new Message(message,user);
        messages.add(message);
    }
    public void disconnect()
    {
        try
        {
            model.disconnect();
            message.set("");
        }
        catch (Exception e)
        {
            error.setValue(e.getMessage());
        }
    }
    public void bindMessageList(ObjectProperty<ObservableList<String>> property)
    {
        messages.bind(property);
    }

    public StringProperty errorProperty() {
        return error;
    }

    public void bindMessage(StringProperty property)
    {
        message.bindBidirectional(property);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if (evt.getPropertyName().equals("UserLoggedIn")){
                user = (User) evt.getNewValue();
                support.firePropertyChange("UserLoggedIn", null, user);
            }
            else if (evt.getPropertyName().equals(("MessageSent"))){
                messageObject = (Message) evt.getNewValue();
                messages.add(messageObject.message());
            }

        });
    }

}
