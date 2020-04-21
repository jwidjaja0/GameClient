import com.ExceptionHandled.Client.Client;
import com.ExceptionHandled.GameMessages.Interfaces.*;
import com.ExceptionHandled.GameMessages.Wrappers.*;
import com.ExceptionHandled.TicTacToeUI.Lobby.LobbyController;
import com.ExceptionHandled.TicTacToeUI.MenuLayout.MenuLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Main extends Application implements Observer {
    private Client client;
    private LobbyController lbc;
    private MenuLayoutController mlc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            client = new Client();
              client.addObserver(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Instantiate the FXML Loader to load the game board UI
        FXMLLoader lobbyUI = new FXMLLoader(getClass().getResource("com/ExceptionHandled/TicTacToeUI/Lobby/Lobby.fxml"));
        lbc = new LobbyController();
        lobbyUI.setController(lbc);
        Parent lobby = lobbyUI.load();

        JMetro jLobby = new JMetro(Style.DARK);
        jLobby.setParent(lobby);

        // Instantiate the FXML Loader to load the top menu
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("com/ExceptionHandled/TicTacToeUI/MenuLayout/MenuLayout.fxml"));
        mlc = new MenuLayoutController();
        menuLoader.setController(mlc);
        Parent menu = menuLoader.load();

        JMetro jMenu = new JMetro(Style.LIGHT);
        jMenu.setParent(menu);

        // Create the main vBox that holds both the menu and the game board
        //VBox mainScreen = new VBox(menu, lobby);
        VBox mainScreen = new VBox(jMenu.getParent(), jLobby.getParent());

        // Create the main application window
        primaryStage.setTitle("TicTacToe Board");
        Scene scene1 = new Scene(mainScreen);
        scene1.getStylesheets().add("com/ExceptionHandled/TicTacToeUI/CSS/structStyle.css");
        primaryStage.setScene(scene1);

        primaryStage.show();

    }

    @Override
    public void update(Observable o, Object arg) {
        Packet packet = (Packet) arg;
        String messageType = packet.getMessageType();
        if(messageType.equals("Login")){
            mlc.messageProcessor(packet.getMessage());
        }
        else if (messageType.equals("MainMenu")){
            lbc.messageProcessor((MainMenu)packet.getMessage());
        }
        else if (messageType.equals("Game")){
            lbc.messageProcessor((Game)packet.getMessage());
        }
    }
}
