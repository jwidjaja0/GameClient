package com.ExceptionHandled.TicTacToeUI.SplashScreen.GetUserInfo;
import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.GameMessages.Login.*;
import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.GameMessages.MainMenu.ListActiveGamesRequest;
import com.ExceptionHandled.GameMessages.UserUpdate.*;
import com.ExceptionHandled.Interfaces.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.Observable;


//TODO: Add error checking
public class GetUserInfoController extends Observable implements Controller {
    @FXML
    Button action;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    TextField firstName;

    @FXML
    TextField lastName;

    @FXML
    Label firstNameLabel;

    @FXML
    Label lastNameLabel;

    private String[] info;
    boolean isLogin;
    boolean isChange;


    public void initialize(){
        info = new String[4];
        action.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                info[0] = username.getText();
                info[1] = password.getText();
                info[2] = firstName.getText();
                info[3] = lastName.getText();
                checkValues();
            }
        });
    }

    private void close(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ((Stage) action.getScene().getWindow()).close();
            }
        });
    }


    public void setType(String type){
        if (type.equals("Register"))
            setToRegister();
        else if (type.equals("Login"))
            setToLogin();
        else {
            setToChange();
        }
    }

    private void setToRegister(){
        action.setText("Register");
        isLogin = false;
        isChange = false;
    }

    private void setToLogin(){
        action.setText("Login");
        firstNameLabel.setVisible(false);
        lastNameLabel.setVisible(false);
        firstName.setVisible(false);
        lastName.setVisible(false);
        isLogin = true;
        isChange = false;
    }

    private void setToChange(){
        action.setText("Change Profile Information");
        firstNameLabel.setVisible(false);
        lastNameLabel.setVisible(false);
        firstName.setVisible(false);
        lastName.setVisible(false);
        isLogin = false;
        isChange = true;
    }

    private void signUpFail(SignUpFail fail){
        (new AlertFactory(fail.toString())).displayAlert();
        info = new String[4];
    }

    private void loginFail(LoginFail fail){
        (new AlertFactory(fail.toString())).displayAlert();
        info = new String[4];
    }

    private void loginSuccess(LoginSuccess success){
        (new AlertFactory(success.toString())).displayAlert();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                close();
            }
        });
        MessageSender.getInstance().sendMessage("MainMenu", new ListActiveGamesRequest());
    }

    private void signUpSuccess(SignUpSuccess success){
        (new AlertFactory(success.toString())).displayAlert();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                close();
            }
        });
    }

    private void updateUserSuccess(UserUpdateSuccess success){
        (new AlertFactory(success.toString())).displayAlert();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                close();
            }
        });
    }

    private void userDeleteSuccess(UserUpdateSuccess success){
        (new AlertFactory(success.toString())).displayAlert();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                close();
            }
        });
    }

    public void messageProcessor(Serializable message){
        if (message instanceof SignUpFail){
            signUpFail((SignUpFail) message);
        }
        else if (message instanceof LoginFail){
            loginFail((LoginFail) message);
        }
        //TODO: Add UserUpdateFail
        else if (message instanceof UserDeleteFail){

        }
        else if (message instanceof SignUpSuccess){
            signUpSuccess((SignUpSuccess) message);
        }
        else if (message instanceof LoginSuccess){
            loginSuccess((LoginSuccess) message);
        }
        else if (message instanceof UserUpdateSuccess){
            updateUserSuccess((UserUpdateSuccess) message);
        }
        else if (message instanceof UserDeleteSuccess){

        }
    }

    private void checkValues(){
        if (isLogin && !isChange){
            MessageSender.getInstance().sendMessage("Login", new LoginRequest(info[0], info[1]));
        }
        else if (!isLogin && !isChange){
            MessageSender.getInstance().sendMessage("Login", new SignUpRequest(info[0], info[1], info[2], info[3]));
        }
        else {
            MessageSender.getInstance().sendMessage("UserUpdate", new UserUpdateRequest(info[0], info[1], info[2], info[3]));
        }

    }


}
