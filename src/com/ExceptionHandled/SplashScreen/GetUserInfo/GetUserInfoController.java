package com.ExceptionHandled.SplashScreen.GetUserInfo;
import com.ExceptionHandled.GameMessages.Login.SignUpRequest;
import com.ExceptionHandled.GameMessages.Wrappers.Login;
import com.ExceptionHandled.InternalWrapper.InternalPacket;
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

    public void initialize(){
        info = new String[4];
        action.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                info[0] = username.getText();
                info[1] = password.getText();
                info[2] = firstName.getText();
                info[3] = lastName.getText();
            }
        });
    }



    public void setToRegister(){
        action.setText("Register");
    }

    public void setToLogin(){
        action.setText("Login");
        firstNameLabel.setVisible(false);
        lastNameLabel.setVisible(false);
        firstName.setVisible(false);
        lastName.setVisible(false);
    }

    public void signUpFail(){

    }

    public void loginFail(){

    }

    public void success(){

    }

    private void checkValues(){
        setChanged();
        notifyObservers(new InternalPacket("Login", "Outgoing", new Login("SignUpRequest", new SignUpRequest(info[0], info[1], info[2], info[3]))));
    }

    public String[] getInfo(){
        return info;
    }



}
