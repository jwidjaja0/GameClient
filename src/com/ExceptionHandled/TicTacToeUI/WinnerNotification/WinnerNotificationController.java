package com.ExceptionHandled.TicTacToeUI.WinnerNotification;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinnerNotificationController {
    Image winnerImage;

    @FXML
    ImageView winnerImageView;

    public WinnerNotificationController(Image winner) {
        winnerImage = winner;
    }

    public void initialize(){
        winnerImageView.setImage(winnerImage);
    }
}
