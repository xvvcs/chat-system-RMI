package main.chatsystem.Server;

import main.chatsystem.Client.ChatClientImplementation;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer {
    public static void main(String args[]) throws Exception
    {

        System.out.println("Starting Server...");
        System.out.println("Server IP: " + InetAddress.getLocalHost().getHostAddress() + " with port: 5678");

        Registry registry = LocateRegistry.createRegistry(1099);
        ChatClientImplementation chat = new ChatClientImplementation(); // >>
        Remote stub = UnicastRemoteObject.exportObject(chat,5678);
        registry.bind("chat",stub);
        System.out.println("Server is running");

    }
}
