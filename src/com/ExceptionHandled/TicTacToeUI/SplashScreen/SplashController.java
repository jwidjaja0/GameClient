package com.ExceptionHandled.TicTacToeUI.SplashScreen;

import com.ExceptionHandled.GameMessages.Interfaces.Login;
import com.ExceptionHandled.GameMessages.Login.*;
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
    private Stage openStage;

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
        openStage = new Stage();
        openStage.setTitle("Enter Your Information");
        openStage.setScene(new Scene(getUserInfoWindow));

        if (type.equals("Register"))
            getUserInfoController.setToRegister();
        else
            getUserInfoController.setToLogin();


        openStage.showAndWait();
    }

    public void alert(Login loginMessage){
        if (loginMessage instanceof SignUpFail){
            getUserInfoController.signUpFail((SignUpFail) loginMessage);
    }
        else if (loginMessage instanceof LoginFail){
            getUserInfoController.loginFail((LoginFail) loginMessage);
        }
        else if (loginMessage instanceof SignUpSuccess){
            getUserInfoController.signUpSuccess((SignUpSuccess) loginMessage);
            openStage.close();
        }
        else if (loginMessage instanceof LoginSuccess){
            getUserInfoController.loginSuccess((LoginSuccess) loginMessage);
            openStage.close();
        }
    }

}


