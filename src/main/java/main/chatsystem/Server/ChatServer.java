package main.chatsystem.Server;

import main.chatsystem.Client.Listener;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatServer {
    public static void main(String args[]) throws Exception
    {

        System.out.println("Starting Server...");
        Registry registry = LocateRegistry.createRegistry(1099);
        ChatImplementation chat = new ChatImplementation(); // >>
        Remote stub = UnicastRemoteObject.exportObject(chat,0);
        registry.bind("chat",stub);
        System.out.println("Server is running");

    }
}
