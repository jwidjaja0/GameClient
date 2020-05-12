package com.ExceptionHandled.TicTacToeUI.GetUserInfo;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.Observable;


//TODO: Add error checking
public class GetUserInfoController extends Observable implements Controller {
    @FXML Button action;

    @FXML TextField username;

    @FXML PasswordField password;

    @FXML TextField firstName;

    @FXML TextField lastName;

    @FXML TextField confirmPassword;

    @FXML Label firstNameLabel;

    @FXML Label lastNameLabel;

    @FXML Label confirmPasswordLabel;

    @FXML AnchorPane getInfoAnchor;

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
                if (!isLogin && !isChange){
                    if (confirmPassword.getText().equals(info[1])){//Check password for match
                        checkValues();
                    }
                    else{
                        info = new String[4];//Clear out the container holding values
                        AlertFactory.getInstance().displayCommonAlert("Passwords do not match.");
                        password.clear();
                        confirmPassword.clear();
                    }
                }
                else{
                    checkValues();
                }

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
            userDeleteSuccess((UserDeleteSuccess) message);
        }
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
        confirmPassword.setVisible(false);
        confirmPasswordLabel.setVisible(false);
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
        AlertFactory.getInstance().displayCommonAlert(fail.toString());
        info = new String[4];
    }

    private void loginFail(LoginFail fail){
        AlertFactory.getInstance().displayCommonAlert(fail.toString());
        info = new String[4];
    }

    private void loginSuccess(LoginSuccess success){
        AlertFactory.getInstance().displayCommonAlert(success.toString());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                close();
            }
        });
        MessageSender.getInstance().sendMessage("MainMenu", new ListActiveGamesRequest());
    }

    private void signUpSuccess(SignUpSuccess success){
        AlertFactory.getInstance().displayCommonAlert(success.toString());
        //Send Login Request to Server
        MessageSender.getInstance().sendMessage("Login", new LoginRequest(info[0], info[1]));
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                close();
            }
        });
    }

    private void updateUserSuccess(UserUpdateSuccess success){
        AlertFactory.getInstance().displayCommonAlert(success.toString());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                close();
            }
        });
    }

    private void userDeleteSuccess(UserDeleteSuccess success){
        AlertFactory.getInstance().displayCommonAlert(success.toString());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                close();
            }
        });
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
