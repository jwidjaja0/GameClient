package com.ExceptionHandled.TicTacToeUI.Lobby.CreateGame;

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
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createGame();
            }
        });
    }

    private void createGame(){

    }

}
