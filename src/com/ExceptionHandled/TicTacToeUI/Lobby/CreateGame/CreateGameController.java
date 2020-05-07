package com.ExceptionHandled.TicTacToeUI.Lobby.CreateGame;

import com.ExceptionHandled.GameMessages.MainMenu.NewGameRequest;
import com.ExceptionHandled.Client.MessageSender;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetroStyleClass;


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

    @FXML
    AnchorPane createGameAnchor;

    public void initialize(){
        createGameAnchor.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        opponent.getItems().add("Human");
        opponent.getItems().add("AI");
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
        System.out.println(opponent.getValue());
        System.out.println(privateGame.getValue());
        MessageSender.getInstance().sendMessage("NewGameRequest", new NewGameRequest(opponent.getValue(), gameName.getText()));
        ((Stage)(create.getScene().getWindow())).close();
    }

}
