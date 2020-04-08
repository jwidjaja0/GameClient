package com.ExceptionHandled.TicTacToeUI.Lobby;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class LobbyController {
    @FXML
    ListView gamesList;

    @FXML
    Button createGameButton;

    @FXML
    Button joinGameButton;

    @FXML
    Button spectateButton;

    public void initialize(){
        createGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        joinGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        spectateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }




}
