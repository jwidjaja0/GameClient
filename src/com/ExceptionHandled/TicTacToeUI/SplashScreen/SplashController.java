package com.ExceptionHandled.TicTacToeUI.SplashScreen;

import com.ExceptionHandled.GameMessages.Interfaces.Login;
import com.ExceptionHandled.GameMessages.Login.*;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.TicTacToeUI.SplashScreen.GetUserInfo.GetUserInfoController;
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
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class SplashController implements Controller {
    @FXML
    Button login;

    @FXML
    Button register;
    @FXML
    AnchorPane splashAnchor;

    private GetUserInfoController getUserInfoController;
    private JMetro jMetro;

    public void initialize(){
        jMetro = new JMetro(Style.DARK);
        splashAnchor.getStyleClass().add(JMetroStyleClass.BACKGROUND);
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
        getUserInfoController = new GetUserInfoController();

        FXMLLoader getUserInfo = new FXMLLoader(getClass().getResource("GetUserInfo/GetUserInfo.fxml"));
        getUserInfo.setController(getUserInfoController);
        Parent getUserInfoWindow = getUserInfo.load();
        Stage stage = new Stage();
        stage.setTitle("Enter Your Information");
        jMetro.setParent(getUserInfoWindow);
        stage.setScene(new Scene(jMetro.getParent()));
        getUserInfoController.setType(type);
        stage.showAndWait();
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


