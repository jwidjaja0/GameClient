package com.ExceptionHandled.TicTacToeUI.MenuLayout;



import com.ExceptionHandled.GameMessages.Wrappers.Login;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.TicTacToeUI.SplashScreen.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class MenuLayoutController {

    @FXML
    MenuItem loginRegisterButton;

    @FXML
    MenuBar menuBar1;

    private Controller controller;

    public void initialize(){
        loginRegisterButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                loginRegister();
            }
        });
    }

    public void loginRegister(){
        try{
            FXMLLoader splashScreen = new FXMLLoader(getClass().getResource("../SplashScreen/SplashScreen.fxml"));
            controller = new SplashController();
            splashScreen.setController(controller);
            Parent splashWindow = splashScreen.load();
            Stage stage = new Stage();
            stage.setTitle("Welcome!");
            stage.setScene(new Scene(splashWindow));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void alert(Serializable message){
        if (message instanceof Login){
            ((SplashController) controller).alert((Login) message);
        }
    }
}
