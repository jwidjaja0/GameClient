package com.ExceptionHandled.TicTacToeUI.BoardUI;


import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.GameMessages.Game.*;
import com.ExceptionHandled.GameMessages.Interfaces.Game;
import com.ExceptionHandled.GameMessages.MainMenu.JoinGameSuccess;
import com.ExceptionHandled.GameMessages.MainMenu.NewGameSuccess;
import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.GameMessages.MainMenu.SpectateSuccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class GameBoardController {



    double iconSize = 70.0;


    @FXML
    public Button btnExit;

    @FXML
    public Button btnRestart;

    @FXML
    public Button panel1;

    @FXML
    public Button panel2;

    @FXML
    public Button panel3;

    @FXML
    public Button panel4;

    @FXML
    public Button panel5;

    @FXML
    public Button panel6;

    @FXML
    public Button panel7;

    @FXML
    public Button panel8;

    @FXML
    public Button panel9;

    @FXML
    public Label player1Score;
    @FXML
    public Label player2Score;

    @FXML
    public Label player1Label;

    @FXML
    public Label player2Label;


    private ArrayList<Button> buttons;
    private String gameID;
    private String gameName;
    private String player;
    private Stage thisStage;


    public GameBoardController(NewGameSuccess newGame){
        player = "x";
        gameID = newGame.getGameId();
        gameName = newGame.getGameName();
        player1Label = new Label();
        player1Label.setText("You");
    }

    public GameBoardController(JoinGameSuccess joinGame){
        player = "o";
        gameID = joinGame.getGameID();
        gameName = joinGame.getGameName();
        player1Label = new Label();
        player1Label.setText(joinGame.getOtherPlayerName());
        player2Label = new Label();
        player2Label.setText("You");
    }

    public GameBoardController(SpectateSuccess spectateGame) {
        player = "Spectator";
        gameID = spectateGame.getGameID();
        gameName = spectateGame.getGameName();
        player1Label = new Label();
        player1Label.setText(spectateGame.getPlayer1Name());
        player2Label = new Label();
        player2Label.setText(spectateGame.getPlayer2Name());
        disableAllPanels();
    }


    /**
     * initialize()
     * Starts every instance of the window with a game versus the AI
     */
    public void initialize() throws IOException {
        buttons = new ArrayList<>();
        buttons.add(panel1);
        buttons.add(panel2);
        buttons.add(panel3);
        buttons.add(panel4);
        buttons.add(panel5);
        buttons.add(panel6);
        buttons.add(panel7);
        buttons.add(panel8);
        buttons.add(panel9);

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

    public void setStage(Stage thisStage){
        this.thisStage = thisStage;
    }


    public String getGameID(){
        return gameID;
    }

    /**
     * disableAllPanels()
     * Prevents any clicking of the game board
     */
    private void disableAllPanels(){
        for(Button b: buttons){
            b.setMouseTransparent(true);
        }
    }

    /**
     * enableAllPanels()
     * Enables clicking of the game board
     */
    private void enableAllPanels(){
        for(Button b: buttons){
            b.setMouseTransparent(false);
        }
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

    @FXML
    public void panelClick(ActionEvent event){
        Button button = (Button) event.getSource();
        int row = GridPane.getRowIndex(button);
        int column = GridPane.getColumnIndex(button);
        //Send the move to server
        MessageSender.getInstance().sendMessage("MoveMade", new MoveMade(gameID, player, row, column));
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
        disableAllPanels();
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



    //TODO: Done
    private Button getButton(int row, int col){
        return buttons.get(3 * row + col);
    }

    //TODO: Done
    private void addPlayerScore(String player){
        if (player.equals("X")){
            increasePlayer1Score();
        }
        else if (player.equals("O")){
            increasePlayer2Score();
        }
    }

    //TODO: Done
    private void increasePlayer1Score(){
        // Get text from player1Score
        // Convert to integer
        int currentScore = Integer.parseInt(player1Score.getText());
        // Increase by 1
        ++currentScore;
        // Convert to string
        // Pass to player1Score.setText(...);
        player1Score.setText(String.valueOf(currentScore));

    }

    //TODO: Done
    private void increasePlayer2Score(){
        // Get text from player2Score
        // Convert to integer
        int currentScore = Integer.parseInt(player2Score.getText());
        // Increase by 1
        ++currentScore;
        // Convert to string
        // Pass to player2Score.setText(...);
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



        for(Button b: buttons){
            b.setGraphic(null);
        }
        //TODO:Send Signal to Server to restartgame
        enableAllPanels();
    }
}
