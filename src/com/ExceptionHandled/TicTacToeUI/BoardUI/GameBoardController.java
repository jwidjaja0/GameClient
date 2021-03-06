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


import java.io.Serializable;
import java.util.Observable;

public class GameBoardController extends Observable implements Controller {

    double iconSize = 70.0;
    @FXML public Button btnExit;

    @FXML public Button panel1;

    @FXML public Button panel2;

    @FXML public Button panel3;

    @FXML public Button panel4;

    @FXML public Button panel5;

    @FXML public Button panel6;

    @FXML public Button panel7;

    @FXML public Button panel8;

    @FXML public Button panel9;

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

    }

    @Override
    public void messageProcessor(Serializable message){
        if (message instanceof GameOverOutcome){
            System.out.println("Game " + gameID + " received GameOverOutcome message.");
            gameOver(((GameOverOutcome) message).getPlayer());
            //Send server a request to get new games list
            MessageSender.getInstance().sendMessage("MainMenu", new ListActiveGamesRequest());
        }
        else if (message instanceof MoveValid){
            System.out.println("Game " + gameID + " received MoveValid message.");
            displayMove((MoveValid)message);//Display the move for the player
            setBoard((MoveValid) message);//Set the board, in case of game vsAI
        }
        else if (message instanceof MoveInvalid){
            System.out.println("Game " + gameID + " received MoveInvalid message.");
            if (((MoveInvalid)message).getPlayer().equals(player))//Only display the alert if you are the player who made an invalid move.
                AlertFactory.getInstance().displayCommonAlert(message.toString());
        }
        else if (message instanceof WhoseTurn){
            WhoseTurn whoseTurn = (WhoseTurn)message;
            System.out.println("Game " + gameID + " received WhoseTurn message.");
            System.out.println("Player " + whoseTurn.getWhoseTurn() + "'s turn.");;
            if (whoseTurn.getWhoseTurn().equals(player))
                enableAllPanels();
            else {
                if (vsAI && !player.equals("Spectator")) {//If it is a game vs the AI, tell the AI to make a move
                    AI.getInstance().makeMove(board, gameID);
                }
                disableAllPanels();
            }
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
                AlertFactory.getInstance().displayCommonAlert(playerJoined.toString());
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
        player1Label.setText("You");
        fillBoard();
        vsAI = newGame.getOpponent().equals("AI");
        if(vsAI){
            player2Label.setText("AI");
        }
        else{
            player2Label.setText("TBD");
        }
        //Disable panels until other player joins
        disableAllPanels();
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
        player1Label.setText(joinGame.getOtherPlayerName());
        player2Label.setText("You");
        //Display Moves made
        for (MoveValid mv : joinGame.getMovesMade()){
            displayMove(mv);
        }
        vsAI = false;
        fillBoard();
        //Disable panels until other player joins
        disableAllPanels();
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
        player1Label.setText(spectateGame.getPlayer1Name());
        player2Label.setText(spectateGame.getPlayer2Name());
        //Display Moves made
        for (MoveValid mv : spectateGame.getMovesMade()){
            displayMove(mv);
        }
        vsAI = false;
        fillBoard();
        //Disable panels until other player joins
        disableAllPanels();
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
            setXImage(getButton(move.getXCoord(), move.getYCoord()));
            System.out.println("Placing X at " + move.getXCoord() + " " + move.getYCoord() );
        }
        else {
            setOImage(getButton(move.getXCoord(), move.getYCoord()));
            System.out.println("Placing O at " + move.getXCoord() + " " + move.getYCoord() );
        }
    }

    private void setXImage(Button tc){
        Image image = new Image(getClass().getResourceAsStream("/com/ExceptionHandled/TicTacToeUI/Graphics/XShape.png"));
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
        Image image = new Image(getClass().getResourceAsStream("/com/ExceptionHandled/TicTacToeUI/Graphics/WhiteCircle.png"));
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



    private void gameOver(String winningPlayer){
        disableAllPanels();
        if (winningPlayer.equals("d")){
            displayTieNotification();
        }
        else{
            displayWinnerNotification(winningPlayer);
        }
    }

    //TODO: Done
    private Button getButton(int row, int col){
        Button[][] buttons = {{panel1, panel2, panel3}, {panel4, panel5, panel6}, {panel7, panel8, panel9}};
        return buttons[row][col];
    }



    @FXML
    private void exitGame() {
        Stage stage = (Stage) playArea.getScene().getWindow();
        setChanged();
        notifyObservers(new RemoveGame(this));
        //Close window
        stage.close();


    }

    private void displayWinnerNotification(String winner){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    String winnerGraphic = "";
                    if (winner.equals("x")){
                        winnerGraphic = "/com/ExceptionHandled/TicTacToeUI/Graphics/XShape.png";
                    }
                    else{
                        winnerGraphic = "/com/ExceptionHandled/TicTacToeUI/Graphics/WhiteCircle.png";
                    }
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ExceptionHandled/TicTacToeUI/WinnerNotification/WinnerNotification.fxml"));
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
        });

    }

    private void displayTieNotification(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/com/ExceptionHandled/TicTacToeUI/DrawNotification/DrawNotification.fxml"));
                    Stage pStage = new Stage();
                    pStage.setScene(new Scene(root));

                    //Following block of code is to have tie window slowly fade away
//                    Timeline timeline = new Timeline();
//                    KeyFrame key = new KeyFrame(Duration.seconds(2), new KeyValue(pStage.opacityProperty(), 0));
//                    timeline.getKeyFrames().add(key);
//                    timeline.play();

                    pStage.show();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });
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
        board[move.getXCoord()][move.getYCoord()] = move.getPlayer();
        System.out.println("Board set at " + move.getXCoord() + " " + move.getYCoord());
    }

}
