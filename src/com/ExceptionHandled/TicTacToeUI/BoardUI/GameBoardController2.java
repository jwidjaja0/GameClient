package com.ExceptionHandled.TicTacToeUI.BoardUI;

import com.ExceptionHandled.GameMessages.MainMenu.JoinGameSuccess;
import com.ExceptionHandled.GameMessages.MainMenu.NewGameSuccess;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetroStyleClass;

public class GameBoardController2 {
    double iconSize = 70.0;
    @FXML Button btnExit;
    @FXML Button btnRestar;
    @FXML Label player1Score;
    @FXML Label player2Score;
    @FXML Label player1Label;
    @FXML Label player2Label;
    @FXML GridPane playArea;

    private String gameID;
    private String gameName;
    private String player;
    private Stage thisStage;

    public GameBoardController2(NewGameSuccess newGame){
        player = "x";
        gameID = newGame.getGameId();
        gameName = newGame.getGameName();
        player1Label = new Label();
        player1Label.setText("You");

        FXMLLoader TTTloader = new FXMLLoader(getClass().getResource("com/ExceptionHandled/TicTacToeUI/BoardUI/TTTBoard.fxml"));

    }

//    public GameBoardController2(JoinGameSuccess joinGameSuccess){
//        player = "Spectator";
//        gameID = spectateGame.getGameID();
//        gameName = spectateGame.getGameName();
//        player1Label = new Label();
//        player1Label.setText(spectateGame.getPlayer1Name());
//        player2Label = new Label();
//        player2Label.setText(spectateGame.getPlayer2Name());
//        disableAllPanels();
//    }

    public void initialize(){
        playArea.getStyleClass().add(JMetroStyleClass.BACKGROUND);
    }
}
