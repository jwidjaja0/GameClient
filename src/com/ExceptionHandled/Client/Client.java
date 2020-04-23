package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Connection.*;
import com.ExceptionHandled.GameMessages.Interfaces.Login;
import com.ExceptionHandled.GameMessages.Login.LoginSuccess;
import com.ExceptionHandled.GameMessages.Login.LogoutSuccess;
import com.ExceptionHandled.GameMessages.Login.SignUpSuccess;
import com.ExceptionHandled.GameMessages.UserUpdate.UserDeleteSuccess;
import com.ExceptionHandled.GameMessages.Wrappers.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client extends Observable implements Observer {
    private Socket serverConnection;
    private String username;
    private Send send;
    private Receive receive;
    private Publish publish;
    private String playerID;

    //TODO: Change to packet
    private BlockingQueue<Packet> outgoing;
    private BlockingQueue<Packet> incoming;


    public Client() throws IOException {
        serverConnection = new Socket("localhost", 8000);// Create initial connection, need server to be up

        outgoing = new ArrayBlockingQueue<>(100);
        incoming = new ArrayBlockingQueue<>(100);

        send = new Send(serverConnection, outgoing);
        receive = new Receive(serverConnection, incoming);
        publish = new Publish(incoming);
        publish.addObserver(this);
        MessageSender.getInstance().setQueue(outgoing);
        MessageSender.getInstance().sendMessage("Connection", new ConnectionRequest());
    }

    @Override
    public void update(Observable o, Object arg) {
        Packet packet = (Packet)arg;
        String messageType = packet.getMessageType();
        Serializable message = packet.getMessage();
        if (messageType.equals("Login")) {
            System.out.println("Received Login Message");
            if (message instanceof LoginSuccess) {
                playerID = ((LoginSuccess) message).getPlayerID();//Sets the player id in MessageSender
                System.out.println("Player Logged In. ID : " + playerID);
                MessageSender.getInstance().setPlayerID(playerID);
            } else if (message instanceof LogoutSuccess) {
                System.out.println("Received Logout Success message. Removing PlayerID from MessageSender.");
                MessageSender.getInstance().logOut();//Sets playerID in MessageSender to null
            }
        }
        else if (messageType.equals("UserUpdate")){
            if (message instanceof UserDeleteSuccess){
                System.out.println("Received User Delete Success. Removing PlayerID from MessageSender.");
            }
        }
        setChanged();
        notifyObservers(arg);
    }
}
