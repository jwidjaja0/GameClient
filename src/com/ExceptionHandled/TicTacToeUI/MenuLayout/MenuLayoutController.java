package com.ExceptionHandled.TicTacToeUI.MenuLayout;



import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.GameMessages.Login.*;
import com.ExceptionHandled.GameMessages.Stats.PlayerStatsInfo;
import com.ExceptionHandled.GameMessages.Stats.PlayerStatsRequest;
import com.ExceptionHandled.GameMessages.UserUpdate.UserDeleteFail;
import com.ExceptionHandled.GameMessages.UserUpdate.UserDeleteRequest;
import com.ExceptionHandled.GameMessages.UserUpdate.UserDeleteSuccess;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.TicTacToeUI.SplashScreen.*;
import com.ExceptionHandled.TicTacToeUI.SplashScreen.GetUserInfo.GetUserInfoController;
import com.ExceptionHandled.TicTacToeUI.ViewStats.StatsViewerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;


import java.io.IOException;
import java.io.Serializable;

public class MenuLayoutController implements Controller {

    @FXML MenuItem loginRegisterButton;

    @FXML MenuItem personalRecordButton;

    @FXML MenuItem changeProfileInfo;

    @FXML MenuBar menuBar1;

    @FXML MenuItem logout;

    @FXML MenuItem deleteAccount;



    private Controller userInfoController;
    private Controller userStatsController;
    private JMetro jMetro;

    public void initialize(){
        jMetro = new JMetro(Style.DARK);
        menuBar1.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        logout.setDisable(true);
        deleteAccount.setDisable(true);

        loginRegisterButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                loginRegister();
            }
        });

        personalRecordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                statsRequest();
            }
        });

        changeProfileInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeUserInfo();
            }
        });

        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logoutAccount();
            }
        });

        deleteAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteUserAccount();
            }
        });
    }

    @Override
    public void messageProcessor(Serializable message){
        if (message instanceof UserDeleteSuccess){
            AlertFactory.getInstance().displayAlert(message.toString());
            logoutAccount();//Log out
        }
        else if (message instanceof UserDeleteFail){
            AlertFactory.getInstance().displayAlert(message.toString());
        }
        else if (message instanceof LogoutSuccess){
            AlertFactory.getInstance().displayAlert(message.toString());
            logout.setDisable(true);
            deleteAccount.setDisable(true);
        }
        else if (message instanceof LogoutFail){
            AlertFactory.getInstance().displayAlert(message.toString());
        }
        else if (message instanceof PlayerStatsInfo){
            showStats((PlayerStatsInfo) message);
        }
        else{
            userInfoController.messageProcessor(message);//Send it first because alerts handled in subsequent screens
            if (message instanceof LoginSuccess || message instanceof SignUpSuccess){
                logout.setDisable(false);
                deleteAccount.setDisable(false);
            }
        }

    }

    private void showStats(PlayerStatsInfo stats){
        try{
            userStatsController = new StatsViewerController();
            FXMLLoader statsScreen = new FXMLLoader(getClass().getResource("../ViewStats/StatsViewer.fxml"));
            userStatsController.messageProcessor(stats);
            statsScreen.setController(userStatsController);
            Parent statsScreenWindow = statsScreen.load();
            Stage stage = new Stage();
            stage.setTitle("Player Game History");
            stage.setScene(new Scene(statsScreenWindow));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void changeUserInfo(){
        try{
            userInfoController = new GetUserInfoController();
            FXMLLoader getUserInfo = new FXMLLoader(getClass().getResource("../SplashScreen/GetUserInfo/GetUserInfo.fxml"));
            getUserInfo.setController(userInfoController);
            Parent getUserInfoWindow = getUserInfo.load();
            Stage stage = new Stage();
            stage.setTitle("Enter Your Information");
            jMetro.setParent(getUserInfoWindow);

            stage.setScene(new Scene(jMetro.getParent()));
            ((GetUserInfoController) userInfoController).setType("Change");
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginRegister(){
        try{
            FXMLLoader splashScreen = new FXMLLoader(getClass().getResource("../SplashScreen/SplashScreen.fxml"));
            userInfoController = new SplashController();
            splashScreen.setController(userInfoController);
            Parent splashWindow = splashScreen.load();
            Stage stage = new Stage();
            stage.setTitle("Welcome!");

            jMetro.setParent(splashWindow);
            stage.setScene(new Scene(jMetro.getParent()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void statsRequest(){
        //Send request to server
        MessageSender.getInstance().sendMessage("PlayerStatsRequest", new PlayerStatsRequest());
    }

    private void logoutAccount(){
        MessageSender.getInstance().sendMessage("Login", new LogoutRequest());
    }

    private void deleteUserAccount(){
        MessageSender.getInstance().sendMessage("UserUpdate", new UserDeleteRequest());
    }

}
