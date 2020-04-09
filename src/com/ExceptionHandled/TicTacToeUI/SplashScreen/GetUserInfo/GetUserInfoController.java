package com.ExceptionHandled.TicTacToeUI.SplashScreen.GetUserInfo;
import com.ExceptionHandled.GameMessages.Login.*;
import com.ExceptionHandled.GameMessages.Wrappers.Login;
import com.ExceptionHandled.GameMessages.Wrappers.Packet;
import com.ExceptionHandled.InternalWrapper.InternalPacket;
import com.ExceptionHandled.Miscellaneous.MessageSender;
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
    }

    public void setToLogin(){
        action.setText("Login");
        firstNameLabel.setVisible(false);
        lastNameLabel.setVisible(false);
        firstName.setVisible(false);
        lastName.setVisible(false);
        isLogin = true;
    }

    public void signUpFail(SignUpFail fail){

    }

    public void loginFail(LoginFail fail){

    }

    public void loginSuccess(LoginSuccess success){

    }

    public void signUpSuccess(SignUpSuccess success){

    }

    private void checkValues(){
        if (info[2].equals(null) && info[3].equals(null)){
            MessageSender.getInstance().sendMessage(new Packet("Login", new Login("LoginRequest", new LoginRequest(info[0], info[1]))));
        }
        else{
            MessageSender.getInstance().sendMessage(new Packet("Login", new Login("SignUpRequest", new SignUpRequest(info[0], info[1], info[2], info[3]))));
        }


    }

    public String[] getInfo(){
        return info;
    }


}
