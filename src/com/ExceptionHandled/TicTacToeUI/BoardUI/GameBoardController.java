package com.ExceptionHandled.TicTacToeUI.BoardUI;


import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.GameMessages.Game.*;
import com.ExceptionHandled.GameMessages.Wrappers.Game;
import com.ExceptionHandled.GameMessages.Wrappers.Packet;
import com.ExceptionHandled.Miscellaneous.MessageSender;
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
import java.util.Observable;
import java.util.UUID;

public class GameBoardController extends Observable {

    private String gameID;
    private String player;

    double iconSize = 70.0;

    //TODO: Find a way to add buttons to a list
    @FXML
    public Button btnExit;
    public Button btnRestart;
    public Button panel1;
    public Button panel2;
    public Button panel3;
    public Button panel4;
    public Button panel5;
    public Button panel6;
    public Button panel7;
    public Button panel8;
    public Button panel9;

    //Score board displays
    public Label player1Score;
    public Label player2Score;


    ArrayList<Button> buttons;


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
        String messageType = gameMessage.getMessageType();
        if (messageType.equals("GameOverLoss") || messageType.equals("GameOverWin") || messageType.equals("GameOverTie")){
            messageProcessor(gameMessage);
        }
        else if (messageType.equals("MoveValid")){
            displayMove((MoveValid)gameMessage.getMessage());
        }
        else if (messageType.equals("MoveInvalid")){
            (new AlertFactory(((MoveInvalid)gameMessage.getMessage()).toString())).displayAlert();
        }
        else if (messageType.equals("RematchRespond")){
            //TODO: Restart game
        }
        else if(messageType.equals("WhoseTurn")){
            //TODO: if it is this player's turn, enable the board to be clickable
        }
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

    //TODO: Done
    private void displayMove(MoveValid move){
        if(move.getPlayer().equals("X")){
            setXImage(getButton(move.getxCoord(), move.getyCoord()));
        }
        else {
            setOImage(getButton(move.getxCoord(), move.getyCoord()));
        }
    }


    //TODO: Done
    private void setXImage(Button tc){
        Image image = new Image(getClass().getResourceAsStream("../Graphics/XShape.png"));
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(iconSize);
        imageView.setFitWidth(iconSize);
        tc.setGraphic(imageView);
        tc.setMouseTransparent(true);
    }

    //TODO: Done
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
        MessageSender.getInstance().sendMessage(new Packet("MoveMade", new MoveMade(gameID, player, row, column)));
    }

    public void messageProcessor(Game gameMessage){
        if (gameMessage.getMessageType().equals("GameOverLoss")){
            gameOver("Loss", gameMessage.getMessage());
        }
        else if (gameMessage.getMessageType().equals("GameOverTie")){
            gameOver("Tie", gameMessage.getMessage());
        }
        else if (gameMessage.getMessageType().equals("GameOverWin")){
            gameOver("Win", gameMessage.getMessage());
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

    //TODO: Done
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
