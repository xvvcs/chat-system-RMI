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
import java.io.IOException;

public class ChatViewModel implements PropertyChangeListener {
    private final Model model;
    private int size;
    private Message messageObject;
    private final ListProperty<String> messages;
    private final SimpleStringProperty message;
    private StringProperty error;
    private final PropertyChangeSupport support;
    public ChatViewModel(Model model)
    {
        this.model = model;
        this.error = new SimpleStringProperty("");
        this.message = new SimpleStringProperty("");
        this.messages = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.model.addPropertyChangeListener(this);
        this.support = new PropertyChangeSupport(this);
    }

    public String getNickname() {
        System.out.println(model.getUser().nickname() + " to nick");
        if(model.getUser()!= null){
            return model.getUser().nickname();
        }
        else{
            return "";
        }
    }

    public void sendMessage()
    {
        try
        {
            model.sendMessage(message.get(), model.getUser());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            error.setValue(e.getMessage());
        }
    }
    public void addMessage(String message){
        messages.add(message);
    }
    public void disconnect()
    {
        try
        {
            model.disconnect(model.getUser());
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


    public void bindMessage(StringProperty property)
    {
        message.bindBidirectional(property);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public int getSize()
    {
        return size;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if (evt.getPropertyName().equals("UserLoggedIn")){
                messages.add(model.getUser().nickname() + " has joined the chat");
                support.firePropertyChange("UserLoggedIn", null, evt.getNewValue());
            }
            else if (evt.getPropertyName().equals(("MessageSent"))){
                messageObject = (Message) evt.getNewValue();
                messages.add(messageObject.user().nickname() + " : " +  messageObject.message());
            } else if(evt.getPropertyName().equals("UserLeft")){
                messages.add(model.getUser().nickname() + " has left the chat");
            } else if ("UserCount".equals(evt.getPropertyName())) {
            support.firePropertyChange("UserCount",null,evt.getNewValue());
            size = (int)evt.getNewValue();
        }

        });
    }

}
