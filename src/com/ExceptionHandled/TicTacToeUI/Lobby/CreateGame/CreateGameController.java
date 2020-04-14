package com.ExceptionHandled.TicTacToeUI.Lobby.CreateGame;

import com.ExceptionHandled.GameMessages.MainMenu.NewGameRequest;
import com.ExceptionHandled.Client.MessageSender;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


public class CreateGameController {
    @FXML
    Button create;

    @FXML
    TextField gameName;

    @FXML
    TextField password;

    @FXML
    ComboBox<String> opponent;

    @FXML
    ComboBox<String> privateGame;

    public void initialize(){
        opponent.getItems().add("Human");
        opponent.getItems().add("EasyAI");
        opponent.getItems().add("MediumAI");
        opponent.getItems().add("HardAI");
        opponent.getSelectionModel().selectFirst();
        privateGame.getItems().add("Yes");
        privateGame.getItems().add("No");
        privateGame.getSelectionModel().selectFirst();


        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createGame();
            }
        });
    }

    private void createGame(){
        MessageSender.getInstance().sendMessage("NewGameRequest", new NewGameRequest(opponent.getValue(), gameName.getText(), (privateGame.getValue().equals("Yes")), password.getText()));
    }

}
