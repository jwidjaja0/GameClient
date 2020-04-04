import com.ExceptionHandled.Client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Client client;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        try{
//            //client = new Client();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Open Splash Screen
        FXMLLoader splashScreen = new FXMLLoader(getClass().getResource("/com/ExceptionHandled/SplashScreen/SplashScreen.fxml"));
        Parent splashWindow = splashScreen.load();
        Stage stage = new Stage();
        stage.setTitle("Welcome!");
        stage.setScene(new Scene(splashWindow));
        stage.show();
    }
}
