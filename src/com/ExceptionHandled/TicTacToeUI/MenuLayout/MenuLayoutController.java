package com.ExceptionHandled.TicTacToeUI.MenuLayout;



import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.GameMessages.Interfaces.*;
import com.ExceptionHandled.GameMessages.Stats.PlayerStatsRequest;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.TicTacToeUI.SplashScreen.*;
import com.ExceptionHandled.TicTacToeUI.SplashScreen.GetUserInfo.GetUserInfoController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;


import java.io.IOException;
import java.io.Serializable;

public class MenuLayoutController {

    @FXML
    MenuItem loginRegisterButton;

    @FXML
    MenuItem personalRecordButton;

    @FXML
    MenuItem changeProfileInfo;

    @FXML
    MenuBar menuBar1;

    private Controller controller;
    private JMetro jMetro;

    public void initialize(){
        jMetro = new JMetro(Style.DARK);
        menuBar1.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        loginRegisterButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                loginRegister();
            }
        });

        personalRecordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showStats();
            }
        });

        changeProfileInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeUserInfo();
            }
        });

    }

    private void changeUserInfo(){
        try{
            controller = new GetUserInfoController();
            FXMLLoader getUserInfo = new FXMLLoader(getClass().getResource("../SplashScreen/GetUserInfo/GetUserInfo.fxml"));
            getUserInfo.setController(controller);
            Parent getUserInfoWindow = getUserInfo.load();
            Stage stage = new Stage();
            stage.setTitle("Enter Your Information");
            jMetro.setParent(getUserInfoWindow);

            stage.setScene(new Scene(jMetro.getParent()));
            ((GetUserInfoController) controller).setType("Change");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginRegister(){
        try{
            FXMLLoader splashScreen = new FXMLLoader(getClass().getResource("../SplashScreen/SplashScreen.fxml"));
            controller = new SplashController();
            splashScreen.setController(controller);
            Parent splashWindow = splashScreen.load();
            Stage stage = new Stage();
            stage.setTitle("Welcome!");

            jMetro.setParent(splashWindow);
            stage.setScene(new Scene(jMetro.getParent()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showStats(){
        //Send request to server
        MessageSender.getInstance().sendMessage("PlayerStatsRequest", new PlayerStatsRequest());
    }

    public void messageProcessor(Serializable message){
        controller.messageProcessor(message);
    }
}
