package com.ExceptionHandled.TicTacToeUI.MenuLayout;



import com.ExceptionHandled.Alerts.AlertFactory;
import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.GameMessages.Login.*;
import com.ExceptionHandled.GameMessages.Stats.GameHistoryDetail;
import com.ExceptionHandled.GameMessages.Stats.PlayerStatsInfo;
import com.ExceptionHandled.GameMessages.Stats.PlayerStatsRequest;
import com.ExceptionHandled.GameMessages.UserUpdate.UserDeleteFail;
import com.ExceptionHandled.GameMessages.UserUpdate.UserDeleteRequest;
import com.ExceptionHandled.GameMessages.UserUpdate.UserDeleteSuccess;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.TicTacToeUI.GetUserInfo.GetUserInfoController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;


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


    public void initialize(){
        logout.setDisable(true);
        deleteAccount.setDisable(true);
        personalRecordButton.setDisable(true);
        changeProfileInfo.setDisable(true);

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

        //Open login screen automatically upon startup
        loginRegister();
    }

    @Override
    public void messageProcessor(Serializable message){
        if (message instanceof UserDeleteSuccess){
            AlertFactory.getInstance().displayCommonAlert(message.toString());
            logoutAccount();//Log out
        }
        else if (message instanceof UserDeleteFail){
            AlertFactory.getInstance().displayCommonAlert(message.toString());
        }
        else if (message instanceof LogoutSuccess){
            AlertFactory.getInstance().displayCommonAlert(message.toString());
            logout.setDisable(true);
            deleteAccount.setDisable(true);
            personalRecordButton.setDisable(true);
            changeProfileInfo.setDisable(true);
            loginRegisterButton.setDisable(false);
        }
        else if (message instanceof LogoutFail){
            AlertFactory.getInstance().displayCommonAlert(message.toString());
        }
        else if (message instanceof PlayerStatsInfo){
            showStats((PlayerStatsInfo) message);
        }
        else if (message instanceof GameHistoryDetail){
            userStatsController.messageProcessor(message);
        }
        else {
            userInfoController.messageProcessor(message);//Send it first because alerts handled in subsequent screens
            if (message instanceof LoginSuccess || message instanceof SignUpSuccess){
                logout.setDisable(false);
                deleteAccount.setDisable(false);
                loginRegisterButton.setDisable(true);
                personalRecordButton.setDisable(false);
                changeProfileInfo.setDisable(false);
            }
        }
    }

    private void showStats(PlayerStatsInfo stats){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    FXMLLoader statsScreen = new FXMLLoader(getClass().getResource("../ViewStats/StatsViewer.fxml"));
                    Parent statsScreenWindow = statsScreen.load();
                    userStatsController = statsScreen.getController();
                    userStatsController.messageProcessor(stats);
                    Stage stage = new Stage();
                    stage.setTitle("Player Game History");
                    stage.setScene(new Scene(statsScreenWindow));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void changeUserInfo(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    FXMLLoader getUserInfo = new FXMLLoader(getClass().getResource("../GetUserInfo/GetUserInfo.fxml"));
                    Parent getUserInfoWindow = getUserInfo.load();
                    userInfoController = getUserInfo.getController();
                    Stage stage = new Stage();
                    stage.setTitle("Enter Your Information");

                    stage.setScene(new Scene(getUserInfoWindow));
                    ((GetUserInfoController) userInfoController).setType("Change");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loginRegister(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    FXMLLoader splashScreen = new FXMLLoader(getClass().getResource("../SplashScreen/SplashScreen.fxml"));
                    Parent splashWindow = splashScreen.load();
                    userInfoController = splashScreen.getController();
                    Stage stage = new Stage();
                    stage.setTitle("Welcome!");
                    stage.setScene(new Scene(splashWindow));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void statsRequest(){
        //Send request to server
        MessageSender.getInstance().sendMessage("PlayerStatsRequest", new PlayerStatsRequest());
    }

    private void logoutAccount(){
        MessageSender.getInstance().sendMessage("Login", new LogoutRequest());
    }

    private void deleteUserAccount(){
        if (AlertFactory.getInstance().displayConfirmationAlert("Are you sure you want to delete your account?"))
            MessageSender.getInstance().sendMessage("UserUpdate", new UserDeleteRequest());
    }

}
