package com.ExceptionHandled.TicTacToeUI.Lobby;


import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.GameMessages.Interfaces.*;
import com.ExceptionHandled.GameMessages.MainMenu.*;
import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.TicTacToeUI.BoardUI.GameBoardController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LobbyController {
    @FXML
    ListView<ActiveGameHeader> gamesList;

    @FXML
    Button createGameButton;

    @FXML
    Button joinGameButton;

    @FXML
    Button spectateButton;

    @FXML
    Button refreshGames;

    private Stage openStage;
    private ArrayList<GameBoardController> openGames;

    public void initialize(){
        openGames = new ArrayList<>();

        requestGamesListRefresh();

        createGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    createGameRequest();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

        refreshGames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }

    private void requestGamesListRefresh(){
        MessageSender.getInstance().sendMessage("MainMenu", new ListActiveGamesRequest());
    }

    public void refreshGamesList(ListActiveGames games){
        //TODO: If games dont show properly, check this function
        gamesList.getItems().setAll(games.getActiveGameHeaderList());
    }

    private void createGameRequest() throws IOException {
        FXMLLoader createGameUI = new FXMLLoader(getClass().getResource("CreateGame/CreateGame.fxml"));
        Parent ui = createGameUI.load();
        Stage openStage = new Stage();
        openStage.setTitle("Create New Game");
        openStage.setScene(new Scene(ui));
        openStage.showAndWait();
    }

    private void createGame(NewGameSuccess newGame) throws IOException {
        GameBoardController gbc = new GameBoardController(newGame);
        openGameWindow(gbc, newGame.getGameName());
    }

    private void joinGame(JoinGameSuccess joinGame) throws IOException {
        GameBoardController gbc = new GameBoardController(joinGame);
        openGameWindow(gbc, joinGame.getGameName());
    }

    private void openGameWindow(GameBoardController controller, String gameName) throws IOException {
        openGames.add(controller);
        FXMLLoader game = new FXMLLoader(getClass().getResource("../BoardUI/gameBoardScene.fxml"));
        game.setController(controller);
        Parent gameWindow = game.load();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = new Stage();
                stage.setTitle(gameName);
                stage.setScene(new Scene(gameWindow));
                stage.show();
            }
        });

    }

    public void messageProcessor(Game message){

    }

    public void messageProcessor(MainMenu message){
        //Display Alert
        (new AlertFactory(message.toString())).displayAlert();

        if (message instanceof NewGameSuccess){
            //openStage.close();
            try {
                createGame((NewGameSuccess)message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(message instanceof NewGameFail){

        }
        else if (message instanceof JoinGameFail){

        }
        else if (message instanceof JoinGameSuccess){
            try {
                joinGame((JoinGameSuccess)message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (message instanceof SpectateFail){

        }
        else if (message instanceof SpectateSuccess){
            //TODO: Open game
        }
    }


}
