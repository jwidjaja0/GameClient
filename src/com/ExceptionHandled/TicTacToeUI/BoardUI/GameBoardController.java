package com.ExceptionHandled.TicTacToeUI.BoardUI;


import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.GameMessages.Game.*;
import com.ExceptionHandled.GameMessages.MainMenu.*;
import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.InternalPacketsAndWrappers.RemoveGame;
import com.ExceptionHandled.TicTacToeUI.AI.AI;
import com.ExceptionHandled.TicTacToeUI.WinnerNotification.WinnerNotificationController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.styles.jmetro.JMetroStyleClass;

import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;

public class GameBoardController extends Observable implements Controller {

    double iconSize = 70.0;
    @FXML public Button btnExit;

    @FXML public Button btnRestart;

    @FXML public Button panel1;

    @FXML public Button panel2;

    @FXML public Button panel3;

    @FXML public Button panel4;

    @FXML public Button panel5;

    @FXML public Button panel6;

    @FXML public Button panel7;

    @FXML public Button panel8;

    @FXML public Button panel9;

    @FXML public Label player1Score;

    @FXML public Label player2Score;

    @FXML public Label player1Label;

    @FXML public Label player2Label;

    @FXML GridPane playArea;

    @FXML GridPane tttPane;


    private String gameID;
    private String player;
    private String[][] board;
    private boolean vsAI;



    private void fillBoard(){
        this.board = new String[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.board[i][j] = " ";
            }
        }
    }

    /**
     * initialize()
     * Starts every instance of the window with a game versus the AI
     */
    public void initialize() {
        //instantiatePanels();
        //btnExit = new Button();
        //btnRestart = new Button();
        //playArea = new GridPane();
        playArea.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        panel1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                panelClick(0,0);
            }
        });

        panel2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                panelClick(0,1);
            }
        });

        panel3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                panelClick(0,2);
            }
        });

        panel4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                panelClick(1,0);
            }
        });

        panel5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                panelClick(1,1);
            }
        });

        panel6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                panelClick(1,2);
            }
        });

        panel7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                panelClick(2,0);
            }
        });

        panel8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                panelClick(2,1);
            }
        });

        panel9.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                panelClick(2,2);
            }
        });

        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exitGame();
            }
        });

        btnRestart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                restartGame();
            }
        });

    }

    @Override
    public void messageProcessor(Serializable message){
        if (message instanceof GameOverOutcome){
            System.out.println("Game " + gameID + " received GameOverOutcome message.");
            String winningPlayer = ((GameOverOutcome) message).getPlayer();
            if (winningPlayer.equals(player)){
                gameOver("Win", winningPlayer);
            }
            else if (winningPlayer.equals("none")){
                gameOver("Tie", winningPlayer);
            }
            else{
                gameOver("Loss", winningPlayer);
            }
        }
        else if (message instanceof MoveValid){
            System.out.println("Game " + gameID + " received MoveValid message.");
            displayMove((MoveValid)message);//Display the move for the player
            setBoard((MoveValid) message);//Set the board, in case of game vsAI
        }
        else if (message instanceof MoveInvalid){
            System.out.println("Game " + gameID + " received MoveInvalid message.");
            if (((MoveInvalid)message).getPlayer().equals(player))//Only display the alert if you are the player who made an invalid move.
                AlertFactory.getInstance().displayAlert(message.toString());
        }
        else if (message instanceof RematchRespond){
            System.out.println("Game " + gameID + " received RematchRespond message.");
            //TODO: Restart game
        }
        else if (message instanceof WhoseTurn){
            WhoseTurn whoseTurn = (WhoseTurn)message;
            System.out.println("Game " + gameID + " received WhoseTurn message.");
            System.out.println("Player " + whoseTurn.getWhoseTurn() + "'s turn.");;
            if (whoseTurn.getWhoseTurn().equals(player))
                enableAllPanels();
            else
                if (vsAI){//If it is a game vs the AI, tell the AI to make a move
                    AI.getInstance().makeMove(board, gameID);
                }
                disableAllPanels();
        }
        else if (message instanceof PlayerJoined){
            System.out.println("Game " + gameID + " received PlayerJoined message.");
            setPlayer2((PlayerJoined) message);
        }
    }

    private void setPlayer2(PlayerJoined playerJoined){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                player2Label.setText(playerJoined.getOtherPlayerName());
                AlertFactory.getInstance().displayAlert(playerJoined.toString());
            }
        });
    }

    /**
     * GameBoardController(NewGameSuccess newGame)
     * @param newGame: NewGameSuccess message
     * This constructor is used when the client makes a new game
     */
    public void setDetails(NewGameSuccess newGame){
        //Set member variables
        player = "x";
        gameID = newGame.getGameId();
        //Set labels
        //player1Label = new Label();
        player1Label.setText("You");
        //player2Label = new Label();
        player2Label.setText("TBD");
        fillBoard();
        vsAI = !newGame.getOpponent().equals("Human");
        disableAllPanels();
        //player1Score = new Label();
        player1Score.setText("0");
        //player2Score = new Label();
        player2Score.setText("0");
    }

    /**
     * GameBoardController(JoinGameSuccess newGame)
     * @param joinGame: JoinGameSuccess message
     * This constructor is used when the client joins a game
     */
    public void setDetails(JoinGameSuccess joinGame){
        //Set member variables
        player = "o";
        gameID = joinGame.getGameID();
        //Set labels
        player1Label = new Label();
        player1Label.setText(joinGame.getOtherPlayerName());
        player2Label = new Label();
        player2Label.setText("You");
        //Display Moves made
        for (MoveValid mv : joinGame.getMovesMade()){
            displayMove(mv);
        }
        vsAI = false;
        fillBoard();
        player1Score = new Label();
        player1Score.setText("0");
        player2Score = new Label();
        player2Score.setText("0");
    }

    /**
     * GameBoardController(SpectateSuccess newGame)
     * @param spectateGame: SpectateSuccess message
     * This constructor is used when the client joins a game as a spectator
     */
    public void setDetails(SpectateSuccess spectateGame) {
        //Set member variables
        player = "Spectator";
        gameID = spectateGame.getGameID();
        //Set labels
        player1Label = new Label();
        player1Label.setText(spectateGame.getPlayer1Name());
        player2Label = new Label();
        player2Label.setText(spectateGame.getPlayer2Name());
        disableAllPanels();
        //Display Moves made
        for (MoveValid mv : spectateGame.getMovesMade()){
            displayMove(mv);
        }
        vsAI = false;
        fillBoard();
        player1Score = new Label();
        player1Score.setText("0");
        player2Score = new Label();
        player2Score.setText("0");
    }

    private void instantiatePanels(){
        panel1 = new Button();
        panel2 = new Button();
        panel3 = new Button();
        panel4 = new Button();
        panel5 = new Button();
        panel6 = new Button();
        panel7 = new Button();
        panel8 = new Button();
        panel9 = new Button();
    }

    public String getGameID(){
        return gameID;
    }

    /**
     * disableAllPanels()
     * Prevents any clicking of the game board
     */
    private void disableAllPanels(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                panel1.setMouseTransparent(true);
                panel2.setMouseTransparent(true);
                panel3.setMouseTransparent(true);
                panel4.setMouseTransparent(true);
                panel5.setMouseTransparent(true);
                panel6.setMouseTransparent(true);
                panel7.setMouseTransparent(true);
                panel8.setMouseTransparent(true);
                panel9.setMouseTransparent(true);
            }
        });

    }

    /**
     * enableAllPanels()
     * Enables clicking of the game board
     */
    private void enableAllPanels(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                panel1.setMouseTransparent(false);
                panel2.setMouseTransparent(false);
                panel3.setMouseTransparent(false);
                panel4.setMouseTransparent(false);
                panel5.setMouseTransparent(false);
                panel6.setMouseTransparent(false);
                panel7.setMouseTransparent(false);
                panel8.setMouseTransparent(false);
                panel9.setMouseTransparent(false);
            }
        });

    }

    private void displayMove(MoveValid move){
        if(move.getPlayer().equals("x")){
            setXImage(getButton(move.getxCoord(), move.getyCoord()));
            System.out.println("Placing X at " + move.getxCoord() + " " + move.getyCoord() );
        }
        else {
            setOImage(getButton(move.getxCoord(), move.getyCoord()));
            System.out.println("Placing O at " + move.getxCoord() + " " + move.getyCoord() );
        }
    }

    private void setXImage(Button tc){
        Image image = new Image(getClass().getResourceAsStream("../Graphics/XShape.png"));
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(iconSize);
        imageView.setFitWidth(iconSize);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tc.setGraphic(imageView);
            }
        });

    }

    private void setOImage(Button tc){
        Image image = new Image(getClass().getResourceAsStream("../Graphics/WhiteCircle.png"));
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(iconSize);
        imageView.setFitWidth(iconSize);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tc.setGraphic(imageView);
            }
        });
    }


    @FXML
    public void panelClick(int row, int column){
        System.out.println("Panel " + row + " " + column + " clicked.");
        //Send the move to server
        MessageSender.getInstance().sendMessage("MoveMade", new MoveMade(gameID, player, row, column));
    }



    private void gameOver(String status, String winningPlayer){
        disableAllPanels();
        if (status.equals("Loss")){
            addPlayerScore(winningPlayer);
            displayWinnerNotification(winningPlayer);
        }
        else if (status.equals("Win")){
            addPlayerScore(winningPlayer);
            displayWinnerNotification(winningPlayer);
        }
        else if (status.equals("Tie")){
            displayTieNotification();
        }
    }

    //TODO: Done
    private Button getButton(int row, int col){
        Button[][] buttons = {{panel1, panel2, panel3}, {panel4, panel5, panel6}, {panel7, panel8, panel9}};
        return buttons[row][col];
    }

    //TODO: Done
    private void addPlayerScore(String player){
        if (player.equals("x")){
            increasePlayer1Score();
        }
        else if (player.equals("o")){
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
    private void exitGame() {
        Stage stage = (Stage) playArea.getScene().getWindow();
        if (!player.equals("Spectator")){
            //TODO: Temporary Implementation, remove later
            //Send ForfeitGame message
            MessageSender.getInstance().sendMessage("Game", new ForfeitGame(gameID));
            //Tell Main to remove game from list
            setChanged();
            notifyObservers(new RemoveGame(this));

            //TODO: Implement Below:
            //Check if game is still going on,
            //If game is still going on, display alert asking if player wants to resume game later
            //If yes, send GamePause message
            //Else send ForfeitGame message
            //Tell Main to remove game from list
        }
        //Close window
        stage.close();


    }

    /**
     * restartGame(ActionEvent actionEvent)
     * Sends a message to the server to request a new game. This option forfeits the current game. The game will prompt
     * users to rematch on a finished game already. This button is meant for in-progress game.
     */
    private void restartGame() {
        //Send message to server to forfeit game
        MessageSender.getInstance().sendMessage("Game", new ForfeitGame(gameID));
        //Send message to request rematch
        MessageSender.getInstance().sendMessage("Game", new RematchRequest(gameID));
        //Reset Board
        resetBoard();
    }

    private void rematchRequest(){
        //TODO: Display Alert
        //Send rematch request
        MessageSender.getInstance().sendMessage("Game", new RematchRequest(gameID));
    }

    private void resetBoard(){
        resetAllPanelGraphics();
        enableAllPanels();
    }

    private void displayWinnerNotification(String winner){
        String winnerGraphic = "";
        if (winner.equals(player)){
            winnerGraphic = "../Graphics/XShape.png";
        }
        else{
            winnerGraphic = "../Graphics/WhiteCircle.png";
        }
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/WinnerNotification.fxml"));
            WinnerNotificationController wnc = new WinnerNotificationController(new Image(getClass().getResourceAsStream(winnerGraphic)));
            loader.setController(wnc);
            Parent winNotification = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(winNotification));
            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void displayTieNotification(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/DrawNotification.fxml"));
            Stage pStage = new Stage();
            pStage.setScene(new Scene(root));

            Timeline timeline = new Timeline();
            KeyFrame key = new KeyFrame(Duration.seconds(2), new KeyValue(pStage.opacityProperty(), 0));
            timeline.getKeyFrames().add(key);

            timeline.play();

            pStage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * setTTTPaneStyle()
     * Sets the CSS style sheet for the game window
     */
    private void setTTTPaneStyle(){
        System.out.println("SetTTTPaneStyle called");
        setPanelStyle(panel1);
        setPanelStyle(panel2);
        setPanelStyle(panel3);
        setPanelStyle(panel4);
        setPanelStyle(panel5);
        setPanelStyle(panel6);
        setPanelStyle(panel7);
        setPanelStyle(panel8);
        setPanelStyle(panel9);
    }

    private void setPanelStyle(Button panel) {
        panel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void resetAllPanelGraphics(){
        panel1.setGraphic(null);
        panel2.setGraphic(null);
        panel3.setGraphic(null);
        panel4.setGraphic(null);
        panel5.setGraphic(null);
        panel6.setGraphic(null);
        panel7.setGraphic(null);
        panel8.setGraphic(null);
        panel9.setGraphic(null);
    }

    private void setBoard(MoveValid move){
        board[move.getxCoord()][move.getyCoord()] = move.getPlayer();
        System.out.println("Board set at " + move.getxCoord() + " " + move.getyCoord());
    }

}
