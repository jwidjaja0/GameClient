package com.ExceptionHandled.TicTacToeUI.BoardUI;

import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.GameMessages.Game.*;
import com.ExceptionHandled.GameMessages.Interfaces.Game;
import com.ExceptionHandled.GameMessages.MainMenu.JoinGameSuccess;
import com.ExceptionHandled.GameMessages.MainMenu.NewGameSuccess;
import com.ExceptionHandled.GameMessages.MainMenu.SpectateSuccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetroStyleClass;

import java.io.Serializable;

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

    private TTTBoardController tttBoardController;

    public GameBoardController2(NewGameSuccess newGame){
        player = "x";
        gameID = newGame.getGameId();
        gameName = newGame.getGameName();
        player1Label = new Label();
        player1Label.setText("You");

        FXMLLoader TTTloader = new FXMLLoader(getClass().getResource("com/ExceptionHandled/TicTacToeUI/BoardUI/TTTBoard.fxml"));
        tttBoardController = TTTloader.getController();

    }

    public GameBoardController2(JoinGameSuccess joinGame){
        player = "o";
        gameID = joinGame.getGameID();
        gameName = joinGame.getGameName();
        player1Label = new Label();
        player1Label.setText(joinGame.getOtherPlayerName());
        player2Label = new Label();
        player2Label.setText("You");
    }

    public GameBoardController2(SpectateSuccess spectateGame) {
        player = "Spectator";
        gameID = spectateGame.getGameID();
        gameName = spectateGame.getGameName();
        player1Label = new Label();
        player1Label.setText(spectateGame.getPlayer1Name());
        player2Label = new Label();
        player2Label.setText(spectateGame.getPlayer2Name());
        tttBoardController.disableAllPanels();
    }

    public void initialize(){
        playArea.getStyleClass().add(JMetroStyleClass.BACKGROUND);
    }

    public void incomingMessage(Game gameMessage){
        if (gameMessage instanceof GameOverLoss || gameMessage instanceof GameOverWin || gameMessage instanceof GameOverTie){
            messageProcessor(gameMessage);
        }
        else if (gameMessage instanceof MoveValid){
            displayMove((MoveValid)gameMessage);
        }
        else if (gameMessage instanceof MoveInvalid){
            (new AlertFactory(gameMessage.toString())).displayAlert();
        }
        else if (gameMessage instanceof RematchRespond){
            //TODO: Restart game
        }
        else if(gameMessage instanceof WhoseTurn){
            //TODO: if it is this player's turn, enable the board to be clickable
        }
    }

    //TODO: Done
    private Button getButton(int row, int col){
        return tttBoardController.getButtons().get(3*row+col);
    }

    private void displayMove(MoveValid move){
        if(move.getPlayer().equals("X")){
            setXImage(getButton(move.getxCoord(), move.getyCoord()));
        }
        else {
            setOImage(getButton(move.getxCoord(), move.getyCoord()));
        }
    }

    private void setXImage(Button tc){
        Image image = new Image(getClass().getResourceAsStream("../Graphics/XShape.png"));
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(iconSize);
        imageView.setFitWidth(iconSize);
        tc.setGraphic(imageView);
        tc.setMouseTransparent(true);
    }

    private void setOImage(Button tc){
        Image image = new Image(getClass().getResourceAsStream("../Graphics/WhiteCircle.png"));
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(iconSize);
        imageView.setFitWidth(iconSize);
        tc.setGraphic(imageView);
        tc.setMouseTransparent(true);
    }

    public void messageProcessor(Game gameMessage){
        if (gameMessage instanceof GameOverLoss){
            gameOver("Loss", (GameOverLoss)gameMessage);
        }
        else if (gameMessage instanceof GameOverTie){
            gameOver("Tie", (GameOverTie)gameMessage);
        }
        else if (gameMessage instanceof GameOverWin){
            gameOver("Win", (GameOverWin)gameMessage);
        }
    }

    private void gameOver(String status, Serializable message){
        tttBoardController.disableAllPanels();
        if (status.equals("Loss")){
            addPlayerScore(((GameOverLoss)message).getPlayer());
            //TODO: Display Loss Banner
        }
        else if (status.equals("Win")){
            addPlayerScore(((GameOverLoss)message).getPlayer());
            //TODO: Display Win Banner
        }
        else if (status.equals("Tie")){
            //TODO: Display Tie game banner
        }
    }
    private void addPlayerScore(String player){
        if (player.equals("X")){
            increasePlayer1Score();
        }
        else if (player.equals("O")){
            increasePlayer2Score();
        }
    }

    private void increasePlayer1Score(){
        int currentScore = Integer.parseInt(player1Score.getText());
        ++currentScore;
        player1Score.setText(String.valueOf(currentScore));
    }
    private void increasePlayer2Score(){
        int currentScore = Integer.parseInt(player2Score.getText());
        ++currentScore;
        player2Score.setText(String.valueOf(currentScore));
    }

    @FXML
    public void exitPrgm(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
        //TODO: Send disconnect message to server
    }

    //TODO: Keep but modify
    public void restartGame(ActionEvent actionEvent) {
        for(Button b: tttBoardController.getButtons()){
            b.setGraphic(null);
        }
        //TODO:Send Signal to Server to restartgame
        tttBoardController.enableAllPanels();
    }
}
