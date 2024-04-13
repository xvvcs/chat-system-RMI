package main.chatsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import main.chatsystem.Client.ChatClient;
import main.chatsystem.Client.ChatClientImplementation;
import main.chatsystem.Model.Model;
import main.chatsystem.Model.ModelManager;
import main.chatsystem.View.ViewHandler;
import main.chatsystem.Viewmodel.ViewModelFactory;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Start extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        ChatClient client = (ChatClient) registry.lookup("chat");
        Model model = new ModelManager();
        ViewModelFactory viewModelFactory = new ViewModelFactory(model);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start(primaryStage);
    }
}
