package com.ExceptionHandled.TicTacToeUI.SplashScreen.GetUserInfo;
import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.GameMessages.Login.*;
import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.GameMessages.UserUpdate.UserUpdateRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Observable;


//TODO: Add error checking
public class GetUserInfoController extends Observable {
    @FXML
    Button action;

    @FXML
    TextField username;

    @FXML
    TextField password;

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



    public void setToRegister(){
        action.setText("Register");
        isLogin = false;
        isChange = false;
    }

    public void setToLogin(){
        action.setText("Login");
        firstNameLabel.setVisible(false);
        lastNameLabel.setVisible(false);
        firstName.setVisible(false);
        lastName.setVisible(false);
        isLogin = true;
        isChange = false;
    }

    public void setToChange(){
        action.setText("Change Profile Information");
        firstNameLabel.setVisible(false);
        lastNameLabel.setVisible(false);
        firstName.setVisible(false);
        lastName.setVisible(false);
        isLogin = false;
        isChange = true;
    }

    public void signUpFail(SignUpFail fail){
        (new AlertFactory(fail.toString())).displayAlert();
        info = new String[4];
    }

    public void loginFail(LoginFail fail){
        (new AlertFactory(fail.toString())).displayAlert();
        info = new String[4];
    }

    public void loginSuccess(LoginSuccess success){
        (new AlertFactory(success.toString())).displayAlert();
        //TODO: Close the stage
    }

    public void signUpSuccess(SignUpSuccess success){
        (new AlertFactory(success.toString())).displayAlert();
        //TODO: Close the stage
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

    public String[] getInfo(){
        return info;
    }


}
