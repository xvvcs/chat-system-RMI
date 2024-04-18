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
    private int size;
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
        this.support = new PropertyChangeSupport(this);
        this.model.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("UserLoggedIn")){
                System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                 user = (User) evt.getNewValue();
            }
        });
    }

    public String getNickname(){
        System.out.println(user.nickname() + " to nick");
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
            model.disconnect(user);
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
                messages.add(user.nickname() + " has joined the chat");
                support.firePropertyChange("UserLoggedIn", null, evt.getNewValue());
            }
            else if (evt.getPropertyName().equals(("MessageSent"))){
                messageObject = (Message) evt.getNewValue();
                messages.add(messageObject.user().nickname() + " : " +  messageObject.message());
            } else if(evt.getPropertyName().equals("UserLeft")){
                messages.add(user.nickname() + " has left the chat");
            } else if ("UserCount".equals(evt.getPropertyName())) {
            support.firePropertyChange("UserCount",null,evt.getNewValue());
            size = (int)evt.getNewValue();
        }

        });
    }

}
