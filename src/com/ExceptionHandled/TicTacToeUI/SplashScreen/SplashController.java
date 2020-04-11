package com.ExceptionHandled.TicTacToeUI.SplashScreen;

import com.ExceptionHandled.GameMessages.Login.*;
import com.ExceptionHandled.GameMessages.Wrappers.Login;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.TicTacToeUI.SplashScreen.GetUserInfo.GetUserInfoController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class SplashController implements Controller {
    @FXML
    Button login;

    @FXML
    Button register;

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
        getUserInfoController = new GetUserInfoController();
        FXMLLoader getUserInfo = new FXMLLoader(getClass().getResource("GetUserInfo/GetUserInfo.fxml"));
        getUserInfo.setController(getUserInfoController);
        Parent getUserInfoWindow = getUserInfo.load();
        Stage stage = new Stage();
        stage.setTitle("Enter Your Information");
        stage.setScene(new Scene(getUserInfoWindow));

        if (type.equals("Register"))
            getUserInfoController.setToRegister();
        else
            getUserInfoController.setToLogin();


        stage.showAndWait();
    }

    public void alert(Login loginMessage){
        if (loginMessage.getMessageType().equals("SignUpFail")){
            getUserInfoController.signUpFail((SignUpFail) (loginMessage.getMessage()));
    }
        else if (loginMessage.getMessageType().equals("LoginFail")){
            getUserInfoController.loginFail((LoginFail) (loginMessage.getMessage()));
        }
        else if (loginMessage.getMessageType().equals("SignUpSuccess")){
            getUserInfoController.signUpSuccess((SignUpSuccess) (loginMessage.getMessage()));
            //TODO: Close the stage
        }
        else if (loginMessage.getMessageType().equals("LoginSuccess")){
            getUserInfoController.loginSuccess((LoginSuccess) (loginMessage.getMessage()));
            //TODO: Close the stage
        }
    }

}


