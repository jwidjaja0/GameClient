package com.ExceptionHandled.SplashScreen;

import com.ExceptionHandled.SplashScreen.GetUserInfo.GetUserInfoController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SplashController {
    @FXML
    Button login;

    @FXML
    Button register;

    private String[] registrationInfo;

    public void initialize(){
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    getRegistrationInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getRegistrationInfo() throws IOException {
        GetUserInfoController getUserInfoController = new GetUserInfoController();
        FXMLLoader getUserInfo = new FXMLLoader(getClass().getResource("GetUserInfo/GetUserInfo.fxml"));
        getUserInfo.setController(getUserInfoController);
        Parent getUserInfoWindow = getUserInfo.load();
        Stage stage = new Stage();
        stage.setTitle("Enter Your Information");
        stage.setScene(new Scene(getUserInfoWindow));
        stage.show();
        registrationInfo = getUserInfoController.getInfo();
    }
}
