package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Connection.*;
import com.ExceptionHandled.GameMessages.Interfaces.Login;
import com.ExceptionHandled.GameMessages.Login.LoginSuccess;
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
        
        if (packet.getMessageType().equals("Login")){
            System.out.println("Received Login Message");
            if (packet.getMessage() instanceof LoginSuccess){
                playerID = packet.getPlayerID();
                System.out.println("Player ID: " + playerID);
                MessageSender.getInstance().setPlayerID(playerID);
            }
        }
        setChanged();
        notifyObservers(arg);
    }
}
