package com.ExceptionHandled.TicTacToeUI.DrawNotification;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DrawNotificationController {

    Image player1Image;
    Image player2Image;

    @FXML
    ImageView player1ImageView;
    @FXML
    ImageView player2ImageView;

    public DrawNotificationController() {
        player1Image = new Image(getClass().getResourceAsStream("../Graphics/XShape.png"));
        player2Image = new Image(getClass().getResourceAsStream("../Graphics/WhiteCircle.png"));
    }

    public void initialize(){
        System.out.println("Initialize called");
        player1ImageView.setImage(player1Image);
        player2ImageView.setImage(player2Image);
    }

    public void setImage(char player, Image newImage){
        if(player == 'X'){
            player1Image = newImage;
        }
        else if(player == 'O'){
            player2Image = newImage;
        }
    }
}
