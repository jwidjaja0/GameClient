import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Open Splash Screen
        FXMLLoader splashScreen = new FXMLLoader(getClass().getResource("/com/ExceptionHandled/SplashScreen/SplashScreen.fxml"));
        Parent splashWindow = splashScreen.load();
        Stage stage = new Stage();
        stage.setTitle("Welcome!");
        stage.setScene(new Scene(splashWindow));
        stage.show();
    }
}
