module main.chatsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;
    requires java.rmi;
    requires remoteobserver;


    opens main.chatsystem to javafx.fxml,remoteobserver;
    opens main.chatsystem.View to javafx.fxml;

    opens main.chatsystemResource to javafx.fxml;

    opens main.chatsystem.Model to javafx.base, com.google.gson;
    opens main.chatsystem.Viewmodel to javafx.base;

    exports main.chatsystem.Client;
    exports main.chatsystem;
    exports main.chatsystem.Model;
}