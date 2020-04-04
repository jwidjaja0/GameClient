package SplashScreen.GetUserInfo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.util.Observable;

//TODO: Add error checking
public class GetUserInfoController extends Observable {
    @FXML
    Button register;

    @FXML
    TextField username;

    @FXML
    TextField password;

    private String[] info;

    public void initialize(){
        info = new String[2];
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                info[0] = username.getText();
                info[1] = password.getText();
            }
        });
    }

    public String[] getInfo(){
        return info;
    }



}
