package com.ExceptionHandled.TicTacToeUI.SplashScreen;

import com.ExceptionHandled.GameMessages.Login.*;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.TicTacToeUI.GetUserInfo.GetUserInfoController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class SplashController implements Controller {
    @FXML
    Button login;

    @FXML
    Button register;
    @FXML
    AnchorPane splashAnchor;

    private GetUserInfoController getUserInfoController;

    public void initialize(){
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    login();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    register();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void login() throws IOException {
        showInfoScreen("Login");
    }

    private void register() throws IOException {
        showInfoScreen("Register");
    }

    private void showInfoScreen(String type) throws IOException {
        FXMLLoader getUserInfo = new FXMLLoader(getClass().getResource("/com/ExceptionHandled/TicTacToeUI/GetUserInfo/GetUserInfo.fxml"));
        Parent getUserInfoWindow = getUserInfo.load();
        getUserInfoController = getUserInfo.getController();
        getUserInfoController.setType(type);
        Stage stage = new Stage();
        stage.setTitle("Enter Your Information");
        stage.setScene(new Scene(getUserInfoWindow));
        stage.show();
    }

    @Override
    public void messageProcessor(Serializable message) {
        getUserInfoController.messageProcessor(message);
        if (message instanceof LoginSuccess || message instanceof SignUpSuccess){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ((Stage) login.getScene().getWindow()).close();
                }
            });
        }
    }
}


