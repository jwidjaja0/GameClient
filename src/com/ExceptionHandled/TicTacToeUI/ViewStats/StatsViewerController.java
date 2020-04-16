package com.ExceptionHandled.TicTacToeUI.ViewStats;

import com.ExceptionHandled.GameMessages.Interfaces.Stats;
import com.ExceptionHandled.GameMessages.Stats.PlayerStatsInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class StatsViewerController {
    @FXML
    private Label wins;

    @FXML
    private Label ties;

    @FXML
    private Label losses;

    @FXML
    private ListView<String> gamesList;

    public void initialize(){
        
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

            }
        });
    }

}
