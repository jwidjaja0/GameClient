package com.ExceptionHandled.TicTacToeUI.GameDetailViewer;

import com.ExceptionHandled.GameMessages.Game.MoveValid;
import com.ExceptionHandled.GameMessages.Stats.*;
import com.ExceptionHandled.GameMessages.UserInfo;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.util.Date;
import java.util.List;

public class GameDetailController {
    @FXML private Text gameIDText;
    @FXML private Text gameNameText;
    @FXML private Text player1Text;
    @FXML private Text player2Text;
    @FXML private Text matchResultText;
    @FXML private Text startTimeText;
    @FXML private Text endTimeText;

    @FXML  TableView<MoveValid> moveHistoryView;
    @FXML  TableColumn<MoveValid, String> playerCol;
    @FXML  TableColumn<MoveValid, Integer> xCol;
    @FXML  TableColumn<MoveValid, Integer> yCol;
    @FXML  TableColumn<MoveValid, Date> timeCol;

    @FXML private TableView<UserInfo> viewersView;
    @FXML private TableColumn<UserInfo,String> vidCol;
    @FXML private TableColumn<UserInfo,String> userCol;
    @FXML private TableColumn<UserInfo,String> fNameCol;
    @FXML private TableColumn<UserInfo,String> lNameCol;

    public GameDetailController() {
    }

    public void initialize(){
        playerCol.setCellValueFactory(new PropertyValueFactory<>("player"));
        xCol.setCellValueFactory(new PropertyValueFactory<>("xCoord"));
        yCol.setCellValueFactory(new PropertyValueFactory<>("yCoord"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        vidCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    }


    public void setInfo(GameHistoryDetail gameDetail){
        GameHistorySummary summary = gameDetail.getGameHistorySummary();
        gameIDText.setText(summary.getGameID());
        gameNameText.setText(summary.getGameName());
        player1Text.setText(summary.getPlayer1());
        player2Text.setText(summary.getPlayer2());
        matchResultText.setText(summary.getMatchResult());
        startTimeText.setText(summary.getStartDate().toString());
        if(summary.getEndDate().getTime() < summary.getStartDate().getTime()){
            endTimeText.setText("-");
        }
        else{
            endTimeText.setText(summary.getEndDate().toString());
        }

        populateMoveList(gameDetail.getMoveMadeList());
        //populateViewers(gameDetail.getViewersInfo());
    }

    private void populateMoveList(List<MoveValid> moveList){
        moveHistoryView.getItems().addAll(moveList);
    }

    private void populateViewers(List<UserInfo> viewerList){
        viewersView.getItems().addAll(viewerList);
    }
}
