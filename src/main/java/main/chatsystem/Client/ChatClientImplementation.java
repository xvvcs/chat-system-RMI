package main.chatsystem.Client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.PeopleLog;
import main.chatsystem.Model.User;
import main.chatsystem.Server.StreamsFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;

public class ChatClientImplementation implements ChatClient {
    private final Socket socket;
    private final PrintWriter writer;
    private final BufferedReader reader;
    private final Gson gson;
    private final PropertyChangeSupport support;
    private final MessageListener listener;
    private String nickname;


    public ChatClientImplementation(String host, int port) throws RemoteException {
        socket = new Socket(host, port);
        writer = StreamsFactory.createWriter(socket);
        reader = StreamsFactory.createReader(socket);
        gson = new Gson();
        nickname = null;
        support = new PropertyChangeSupport(this);

        listener = new MessageListener(this, "230.0.0.0", 8888);
        Thread thread = new Thread(listener);
        thread.start();
    }

    @Override
    public synchronized void disconnect() throws RemoteException {
        writer.println("Disconnect");
        writer.flush();
        String reply = reader.readLine();
        String userLeft = nickname + " has left the chat.";

        if (!reply.equals("Disconnected")) {
            throw new RemoteException("Protocol failure");
        }

        Message message = new Message(userLeft);
        String messageJSON = gson.toJson(message);
        writer.println(messageJSON);
        writer.flush();

        socket.close();
    }

    @Override
    public synchronized boolean login(String username, String password) throws RemoteException {
        writer.println("connect");
        writer.flush();
        String reply = reader.readLine();
        if (!reply.equals("login required")) {
            throw new IOException("Protocol failure");
        }
        User userLogin = new User(username, password);
        PeopleLog.getInstance().addUser(userLogin);
        nickname = userLogin.nickname();
        String loginJSON = gson.toJson(userLogin);
        writer.println(loginJSON);
        writer.flush();
        reply = reader.readLine();

        support.firePropertyChange("UserAdded", null, userLogin);
        support.firePropertyChange("UserLoggedIn", null, userLogin);
        return reply.equals("Approved");
    }

    @Override
    public synchronized void sendMessage(String messageContent, User user) throws RemoteException {

        writer.println("Send message");
        writer.flush();


        if (user == null) {
            System.out.println("user null");
            throw new IllegalArgumentException("Error while sending message: user is null");
        }
        if (messageContent == null) {
            System.out.println("message null");
            throw new IllegalArgumentException("Error while sending message: message is empty");
        }
        String reply = reader.readLine();
        if (!reply.equals("Provide message content")) {
            System.out.println("Sending message protocol failure");
        }

        Message message = new Message(user.nickname() + " : " + messageContent);

        String messageJSON = gson.toJson(message);
        writer.println(messageJSON);
        writer.flush();

        this.support.firePropertyChange("MessageSent", null, message.message());

    }

    @Override
    public void addUser(User user) throws RemoteException {
        String userJSON = gson.toJson(user + "joined chat with ip: " + socket.getInetAddress());

        writer.println(userJSON);
        writer.flush();


        support.firePropertyChange("UserAdded", null, user.nickname());
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) throws RemoteException {
        support.addPropertyChangeListener(listener);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) throws RemoteException{
        support.removePropertyChangeListener(listener);
    }

    @Override
    public synchronized void receiveBroadcast(String message) throws RemoteException {
        try {

            Message messageObject = gson.fromJson(message, Message.class);


            support.firePropertyChange("broadcast", null, messageObject);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }
}
