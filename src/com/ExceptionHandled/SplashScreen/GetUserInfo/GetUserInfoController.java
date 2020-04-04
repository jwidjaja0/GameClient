package com.ExceptionHandled.SplashScreen.GetUserInfo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


//TODO: Add error checking
public class GetUserInfoController{
    @FXML
    Button action;

    @FXML
    TextField username;

    @FXML
    TextField password;

    private String[] info;

    public void initialize(){
        info = new String[2];
        action.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                info[0] = username.getText();
                info[1] = password.getText();
            }
        });
    }

    public void setToRegister(){
        action.setText("Register");
    }

    public void setToLogin(){
        action.setText("Login");
    }

    public String[] getInfo(){
        return info;
    }



}
