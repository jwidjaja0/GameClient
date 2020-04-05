import com.ExceptionHandled.Client.Client;
import com.ExceptionHandled.Interfaces.Controller;
import com.ExceptionHandled.InternalWrapper.InternalPacket;
import com.ExceptionHandled.SplashScreen.SplashController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Main extends Application implements Observer {
    private Client client;
    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        try{
//            //client = new Client();
              //client.addObserver(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Open Splash Screen
        FXMLLoader splashScreen = new FXMLLoader(getClass().getResource("/com/ExceptionHandled/SplashScreen/SplashScreen.fxml"));
        controller = splashScreen.getController();
        ((SplashController)controller).addObserver(this);
        Parent splashWindow = splashScreen.load();
        Stage stage = new Stage();
        stage.setTitle("Welcome!");
        stage.setScene(new Scene(splashWindow));
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        InternalPacket packet = (InternalPacket)arg;
        if (packet.getDirection().equals("Outgoing")){
            client.sendOut(packet);
        }
        if (packet.getDirection().equals("ToUI")){
            if (packet.getMessageType().equals("Login")){
                ((SplashController)controller).alert(packet);
            }
        }
    }
}
