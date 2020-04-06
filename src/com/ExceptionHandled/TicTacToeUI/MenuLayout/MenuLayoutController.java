package com.ExceptionHandled.TicTacToeUI.MenuLayout;



import com.ExceptionHandled.SplashScreen.SplashController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MenuLayoutController extends Observable implements Observer {

    @FXML
    MenuItem loginRegisterButton;
    @FXML
    MenuBar menuBar1;

    private Observable controller;

    public void loginRegister(ActionEvent event){
        try{
            FXMLLoader splashScreen = new FXMLLoader(getClass().getResource("../../SplashScreen/SplashScreen.fxml"));
            controller = new SplashController();
            splashScreen.setController(controller);
            controller.addObserver(this);
            Parent splashWindow = splashScreen.load();
            Stage stage = new Stage();
            stage.setTitle("Welcome!");
            stage.setScene(new Scene(splashWindow));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
