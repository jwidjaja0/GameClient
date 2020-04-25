package com.ExceptionHandled.TicTacToeUI.Lobby;


import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.GameMessages.Game.*;
import com.ExceptionHandled.GameMessages.Interfaces.*;
import com.ExceptionHandled.GameMessages.MainMenu.*;
import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.InternalPacketsAndWrappers.RemoveGame;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class LobbyController implements Controller, Observer {
    @FXML ListView<ActiveGameHeader> gamesList;
    @FXML Button createGameButton;
    @FXML Button joinGameButton;
    @FXML Button spectateButton;
    @FXML Button refreshGames;
    @FXML AnchorPane lobbyAnchor;

    private Stage openStage;
    private ArrayList<GameBoardController> openGames;
    private JMetro jMetro;

    public void initialize(){
        jMetro = new JMetro(Style.DARK);
        //Set background for JMetro skin
        lobbyAnchor.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        openGames = new ArrayList<>();

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
                spectateGameRequest(gamesList.getSelectionModel().getSelectedItem());
            }
        });

        refreshGames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                requestGamesListRefresh();
            }
        });
    }

    @Override
    public void messageProcessor(Serializable message){
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
            //Refresh the list of games
            requestGamesListRefresh();
        }
        else if (message instanceof JoinGameFail){
            //Refresh the list of games
            requestGamesListRefresh();
        }
        else if (message instanceof JoinGameSuccess){
            try {
                joinGame((JoinGameSuccess)message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (message instanceof SpectateFail){
            //Refresh the list of games
            requestGamesListRefresh();
        }
        else if (message instanceof SpectateSuccess){
            try {
                spectateGame((SpectateSuccess)message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (message instanceof Game){
            gameMessageProcessor(message);
        }
        else if (message instanceof ListActiveGames){
            refreshGamesList((ListActiveGames) message);
        }
    }

    private void spectateGameRequest(ActiveGameHeader game){
        MessageSender.getInstance().sendMessage("MainMenu", new SpectateRequest(game.getGameID()));
    }

    private void requestGamesListRefresh(){
        MessageSender.getInstance().sendMessage("MainMenu", new ListActiveGamesRequest());
    }

    public void refreshGamesList(ListActiveGames games){
        //Remove all items from current list
        gamesList.getItems().clear();
        //Add new items
        for(ActiveGameHeader h : games.getActiveGameHeaderList()){
            gamesList.getItems().add(h);
        }
    }

    private void createGameRequest() throws IOException {
        FXMLLoader createGameUI = new FXMLLoader(getClass().getResource("CreateGame/CreateGame.fxml"));
        Parent ui = createGameUI.load();

        jMetro.setParent(ui);
        Stage openStage = new Stage();
        openStage.setTitle("Create New Game");
        openStage.setScene(new Scene(jMetro.getParent()));
        openStage.showAndWait();
    }

    private void createGame(NewGameSuccess newGame) throws IOException {
        //Create Game Controller
        GameBoardController gbc = new GameBoardController(newGame);
        //Add controller to lobby's list of active games
        openGames.add(gbc);
        //Add lobby to controller's list of observers
        gbc.addObserver(this);
        //Open the game window
        openGameWindow(gbc, newGame.getGameName());
    }

    private void joinGame(JoinGameSuccess joinGame) throws IOException {
        GameBoardController gbc = new GameBoardController(joinGame);
        //Add controller to lobby's list of active games
        openGames.add(gbc);
        //Add lobby to controller's list of observers
        gbc.addObserver(this);
        //Open the game window
        openGameWindow(gbc, joinGame.getGameName());
    }

    private void spectateGame(SpectateSuccess spectateGame) throws IOException {
        GameBoardController gbc = new GameBoardController(spectateGame);
        //Add controller to lobby's list of active games
        openGames.add(gbc);
        //Add lobby to controller's list of observers
        gbc.addObserver(this);
        //Open the game window
        openGameWindow(gbc, spectateGame.getGameName());
    }

    private void openGameWindow(GameBoardController controller, String gameName) throws IOException {
        openGames.add(controller);
        FXMLLoader game = new FXMLLoader(getClass().getResource("../BoardUI/gameBoardScene.fxml"));
        game.setController(controller);
        Parent gameWindow = game.load();
        jMetro.setParent(gameWindow);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = new Stage();
                stage.setTitle(gameName);
                stage.setScene(new Scene(jMetro.getParent()));
                stage.show();
            }
        });
    }

    private void gameMessageProcessor(Serializable message){
        String gameID = ((Game)message).getGameID();
        //Find the game
        for (GameBoardController gbc: openGames){
            if (gbc.getGameID().equals(gameID)){
                //Pass on the message
                gbc.messageProcessor(message);
            }
        }
    }

    private void removeGame(RemoveGame game){
        openGames.remove(game.getController());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof RemoveGame){
            removeGame((RemoveGame) arg);
        }
    }
}
