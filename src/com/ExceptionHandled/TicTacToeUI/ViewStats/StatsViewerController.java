package com.ExceptionHandled.TicTacToeUI.ViewStats;

import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.GameMessages.Stats.GameHistoryDetail;
import com.ExceptionHandled.GameMessages.Stats.GameHistoryRequest;
import com.ExceptionHandled.GameMessages.Stats.GameHistorySummary;
import com.ExceptionHandled.GameMessages.Stats.PlayerStatsInfo;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.TicTacToeUI.GameDetailViewer.GameDetailController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class StatsViewerController implements Controller {
    @FXML private Label wins;

    @FXML private Label ties;

    @FXML private Label losses;

    @FXML private TableView<GameHistorySummary> gamesList;

    @FXML private TableColumn<GameHistorySummary, String> gameID;

    @FXML private TableColumn<GameHistorySummary, String> player1;

    @FXML private TableColumn<GameHistorySummary, String> player2;

    @FXML private TableColumn<GameHistorySummary, String> outcome;



    public void initialize(){
        //Sets the Cell Value Factory identifiers for each column
        gameID.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        player1.setCellValueFactory(new PropertyValueFactory<>("player1"));
        player2.setCellValueFactory(new PropertyValueFactory<>("player2"));
        outcome.setCellValueFactory(new PropertyValueFactory<>("matchResult"));

        gamesList.setRowFactory(tv -> {
            TableRow<GameHistorySummary> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    GameHistorySummary summary = row.getItem();
                    //Send request to server
                    MessageSender.getInstance().sendMessage("GameHistoryRequest", new GameHistoryRequest(summary.getGameID()));
                }
            });
            return row;
        });
}

    @Override
    public void messageProcessor(Serializable message){
        if (message instanceof PlayerStatsInfo){
            updateStats((PlayerStatsInfo)message);
        }
        else if (message instanceof GameHistoryDetail){
            showGameHistory((GameHistoryDetail)message);
        }
    }

    private void updateStats(PlayerStatsInfo info){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //Set wins
                wins.setText(String.valueOf(info.getWinAmount()));
                //Set ties
                ties.setText(String.valueOf(info.getDrawAmount()));
                //Set losses
                losses.setText(String.valueOf(info.getLossAmount()));
                //Display game history
                gamesList.getItems().addAll(info.getGameStatsInfoList());
            }
        });
    }

    private void showGameHistory(GameHistoryDetail detail){
        try{
            FXMLLoader gameDetails = new FXMLLoader(getClass().getResource("../GameDetailViewer/GameDetail.fxml"));
            Parent gameDetailsWindow = gameDetails.load();
            ((GameDetailController)gameDetails.getController()).setInfo(detail);
            Stage stage = new Stage();
            stage.setTitle("Player Game History");
            stage.setScene(new Scene(gameDetailsWindow));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
