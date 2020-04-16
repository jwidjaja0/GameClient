package com.ExceptionHandled.TicTacToeUI.ViewStats;

import com.ExceptionHandled.GameMessages.Interfaces.Game;
import com.ExceptionHandled.GameMessages.Interfaces.Stats;
import com.ExceptionHandled.GameMessages.Stats.GameHistorySummary;
import com.ExceptionHandled.GameMessages.Stats.PlayerStatsInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class StatsViewerController {
    @FXML
    private Label wins;

    @FXML
    private Label ties;

    @FXML
    private Label losses;

    @FXML
    private TableView<GameHistorySummary> gamesList;

    @FXML
    private TableColumn<GameHistorySummary, String> gameID;

    @FXML
    private TableColumn<GameHistorySummary, String> player1;

    @FXML
    private TableColumn<GameHistorySummary, String> player2;

    @FXML
    private TableColumn<GameHistorySummary, String> matchResult;

    public void initialize(){
        //Sets the Cell Value Factory identifiers for each column
        gameID.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        player1.setCellValueFactory(new PropertyValueFactory<>("player1"));
        player2.setCellValueFactory(new PropertyValueFactory<>("player2"));
        matchResult.setCellValueFactory(new PropertyValueFactory<>("matchResult"));
    }


    public void messageProcessor(Stats message){
        if (message instanceof PlayerStatsInfo){
            updateStats((PlayerStatsInfo)message);
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

}
