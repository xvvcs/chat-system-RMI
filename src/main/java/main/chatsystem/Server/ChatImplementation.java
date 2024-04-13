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
    private final RemotePropertyChangeSupport<Message> support;
    private String nickname;
    private File file;
    private FileLog fileLog;
    public ChatImplementation()
    {
        this.support = new RemotePropertyChangeSupport<>();
        this.file = new File("src/main/java/main/chatsystem/File/ChatLog");
        this.fileLog = FileLog.getInstance(file);

    }
    @Override
    public void disconnect() throws RemoteException, IOException {
        fileLog.log(nickname + "has disconnected");
    }

    @Override
    public void login(String username, String password) throws RemoteException, IOException {
        nickname = username;
        User userlogin = new User(username,password);
        Message message = new Message(null, userlogin);
        support.firePropertyChange("UserLoggedIn",null, message);
        fileLog.log(nickname + "has connected to the server");
    }

    @Override
    public void sendMessage(String message, User user) throws RemoteException, IOException {


        Message message1 = new Message(message, user);
        fileLog.log(user.nickname() + ":" + message);
        this.support.firePropertyChange("MessageSent", null, message1);


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
