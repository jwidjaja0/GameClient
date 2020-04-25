package com.ExceptionHandled.TicTacToeUI.BoardUI;


import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.GameMessages.Game.*;
import com.ExceptionHandled.GameMessages.Interfaces.Game;
import com.ExceptionHandled.GameMessages.MainMenu.JoinGameSuccess;
import com.ExceptionHandled.GameMessages.MainMenu.NewGameSuccess;
import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.GameMessages.MainMenu.SpectateSuccess;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.InternalPacketsAndWrappers.RemoveGame;
import com.ExceptionHandled.TicTacToeUI.WinnerNotification.WinnerNotificationController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import java.util.ArrayList;
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


    private ArrayList<Button> buttons;
    private String gameID;
    private String player;


    /**
     * GameBoardController(NewGameSuccess newGame)
     * @param newGame: NewGameSuccess message
     * This constructor is used when the client makes a new game
     */
    public GameBoardController(NewGameSuccess newGame){
        //Set CSS stle
        setTTTPaneStyle();
        //Set member variables
        player = "x";
        gameID = newGame.getGameId();
        setGameName(newGame.getGameName());
        //Set labels
        player1Label = new Label();
        player1Label.setText("You");
    }

    /**
     * GameBoardController(JoinGameSuccess newGame)
     * @param joinGame: JoinGameSuccess message
     * This constructor is used when the client joins a game
     */
    public GameBoardController(JoinGameSuccess joinGame){
        //Set CSS stle
        setTTTPaneStyle();
        //Set member variables
        player = "o";
        gameID = joinGame.getGameID();
        setGameName(joinGame.getGameName());
        //Set labels
        player1Label = new Label();
        player1Label.setText(joinGame.getOtherPlayerName());
        player2Label = new Label();
        player2Label.setText("You");
        //Display Moves made
        for (MoveValid mv : joinGame.getMovesMade()){
            displayMove(mv);
        }
    }

    /**
     * GameBoardController(SpectateSuccess newGame)
     * @param spectateGame: SpectateSuccess message
     * This constructor is used when the client joins a game as a spectator
     */
    public GameBoardController(SpectateSuccess spectateGame) {
        //Set CSS stle
        setTTTPaneStyle();
        //Set member variables
        player = "Spectator";
        gameID = spectateGame.getGameID();
        setGameName(spectateGame.getGameName());
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
    }

    /**
     * setTTTPaneStyle()
     * Sets the CSS style sheet for the game window
     */
    private void setTTTPaneStyle(){
        System.out.println("SetTTTPaneStyle called");
        for(Button b: buttons){
            b.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
    }

    /**
     * initialize()
     * Starts every instance of the window with a game versus the AI
     */
    public void initialize() throws IOException {
        playArea.getStyleClass().add(JMetroStyleClass.BACKGROUND);
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
            displayMove((MoveValid)message);
        }
        else if (message instanceof MoveInvalid){
            if (((MoveInvalid)message).getPlayer().equals(player))//Only display the alert if you are the player who made an invalid move.
                (new AlertFactory(message.toString())).displayAlert();
        }
        else if (message instanceof RematchRespond){
            //TODO: Restart game
        }
        else if (message instanceof WhoseTurn){
            if (((WhoseTurn)message).getWhoseTurn().equals(player))
                enableAllPanels();
            else
                disableAllPanels();
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

    //Moved to TTTBC
    @FXML
    public void panelClick(ActionEvent event){
        Button button = (Button) event.getSource();
        int row = GridPane.getRowIndex(button);
        int column = GridPane.getColumnIndex(button);
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

    private void setGameName(String gameName){
        //TODO: Give an fxID to the anchorpane and use that instead
        Stage stage = (Stage) btnExit.getScene().getWindow();//Picked a random button to get the stage
        stage.setTitle(gameName);
    }

    private void rematchRequest(){
        //TODO: Display Alert
        //Send rematch request
        MessageSender.getInstance().sendMessage("Game", new RematchRequest(gameID));
    }

    private void resetBoard(){
        for(Button b: buttons){
            b.setGraphic(null);
        }
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
}
