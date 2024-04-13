package main.chatsystem.View;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import main.chatsystem.Model.Message;
import main.chatsystem.Model.PeopleLog;
import main.chatsystem.Viewmodel.ChatViewModel;

public class ChatViewController {
    @FXML private Label usernameLabel;
    @FXML private TextField messageField;
    @FXML private Button sendButton;
    @FXML private Button informationButton;
    @FXML private Button disconnectButton;
    @FXML private ListView<String> chatArea;
    private ViewHandler viewHandler;
    private ChatViewModel chatViewModel;
    private Region root;
    public void init(ViewHandler viewHandler, ChatViewModel chatViewModel, Region root)
    {

        this.viewHandler = viewHandler;
        this.chatViewModel = chatViewModel;
        this.root = root;


        chatViewModel.bindMessage(messageField.textProperty());
        chatViewModel.bindMessageList(chatArea.itemsProperty());

        chatViewModel.addPropertyChangeListener(evt -> {
            if ("UserLoggedIn".equals(evt.getPropertyName())) {
                usernameLabel.setText(chatViewModel.getNickname());
            }
        });



    }
    @FXML public void onSend()
    {
        chatViewModel.sendMessage();
        messageField.setText("");
    }
    @FXML public void onDisconnect()
    {
        chatViewModel.disconnect();
        viewHandler.closeView();
    }
    @FXML public void onInformation()
    {
        int size = PeopleLog.getInstance().getSize();
        chatViewModel.addMessage(String.valueOf(size));
    }
    public Region getRoot(){
        return root;
    }
}
