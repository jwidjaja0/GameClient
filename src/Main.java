import com.ExceptionHandled.Client.Client;
import com.ExceptionHandled.Client.MessageSender;
import com.ExceptionHandled.GameMessages.Interfaces.*;
import com.ExceptionHandled.GameMessages.Login.LogoutRequest;
import com.ExceptionHandled.GameMessages.Wrappers.*;
import com.ExceptionHandled.TicTacToeUI.Lobby.LobbyController;
import com.ExceptionHandled.TicTacToeUI.MenuLayout.MenuLayoutController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
        Parent lobby = lobbyUI.load();
        lbc = lobbyUI.getController();


        // Instantiate the FXML Loader to load the top menu
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("com/ExceptionHandled/TicTacToeUI/MenuLayout/MenuLayout.fxml"));
        Parent menu = menuLoader.load();
        mlc = menuLoader.getController();

        // Create the main vBox that holds both the menu and the game board
        VBox mainScreen = new VBox(menu, lobby);

        // Create the main applicaion window
        primaryStage.setTitle("TicTacToe Board");
        primaryStage.setScene(new Scene(mainScreen));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                //TODO: SEND LOGOUT REQUEST IF LOGGED IN
                if(!client.getPlayerID().equals(-1)){
                    MessageSender.getInstance().sendMessage("Login", client.getPlayerID(), new LogoutRequest());
                }
                System.exit(2);
            }
        });

        primaryStage.show();
    }

    @Override
    public void update(Observable o, Object arg) {

        Packet packet = (Packet) arg;
        String messageType = packet.getMessageType();
        System.out.println("Main received " + messageType + " message.");
        if(messageType.equals("Login") || messageType.equals("UserUpdate") || messageType.equals("Stats")){
            System.out.println("Main passing message to MLC");
            mlc.messageProcessor(packet.getMessage());
        }
        else if (messageType.equals("MainMenu") || messageType.equals("Game")){
            System.out.println("Main passing message to LBC");
            lbc.messageProcessor(packet.getMessage());
        }
    }
}
