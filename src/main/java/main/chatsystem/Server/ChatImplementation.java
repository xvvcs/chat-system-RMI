package main.chatsystem.Server;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import main.chatsystem.Client.ChatClient;
import main.chatsystem.Client.Listener;
import main.chatsystem.File.FileLog;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.User;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

public class ChatImplementation implements ChatClient {
    private final RemotePropertyChangeSupport support;

    private File file;
    private FileLog fileLog;

    private int count;
    public ChatImplementation()
    {
        this.support = new RemotePropertyChangeSupport();
        this.file = new File("src/main/java/main/chatsystem/File/ChatLog");
        this.fileLog = FileLog.getInstance(file);
    }
    @Override
    public void disconnect(User user) throws RemoteException, IOException {
        support.firePropertyChange("UserLeft",null,user);
        count--;
        support.firePropertyChange("UserCount",null,count);
        fileLog.log(user.nickname() + "has disconnected");
    }

    @Override
    public void login(String username, String password) throws RemoteException, IOException {
        try{
            User userlogin = new User(username,password);
            support.firePropertyChange("UserLoggedIn",null, userlogin);
            count++;
            support.firePropertyChange("UserCount",null,count);

            System.out.println(userlogin + "user logged in");; // works, we can see username and pasword
            fileLog.log(username + " has connected to the server");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void sendMessage(String message, User user) throws RemoteException, IOException { //Doesn't work, user is null all the time
        try {
            if (user != null) {
                Message message1 = new Message(message, user);
                System.out.println(user);
                fileLog.log(user.nickname() + ":" + message);
                support.firePropertyChange("MessageSent", null, message1);
            } else {
                System.err.println("User is null. Cannot send message.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPropertyChangeListener(RemotePropertyChangeListener<Message> listener) throws RemoteException {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(RemotePropertyChangeListener<Message> listener) throws RemoteException {
        support.removePropertyChangeListener(listener);
    }
}
