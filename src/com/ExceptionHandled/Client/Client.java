package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Connection.*;
import com.ExceptionHandled.GameMessages.Login.*;
import com.ExceptionHandled.GameMessages.UserUpdate.UserDeleteSuccess;
import com.ExceptionHandled.GameMessages.Wrappers.*;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client extends Observable implements Runnable {
    private Socket serverConnection;
    private String username;
    private Send send;
    private Receive receive;
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

        MessageSender.getInstance().setQueue(outgoing);
        MessageSender.getInstance().sendMessage("Connection", new ConnectionRequest());

        Thread self = new Thread(this);
        self.start();
    }

    @Override
    public void run() {
        // Grabs available messages from incoming and processes it and sends to relevant object
        try {
            while (true) {
                Packet packet = incoming.take();// Get the packet from the incoming queue
                if (packet.getMessage() instanceof LoginSuccess){
                    System.out.println(((LoginSuccess) packet.getMessage()).getPlayerID());
                }
                messageProcessor(packet);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Client closing.");
        }
    }

    private void messageProcessor(Packet packet){
        String messageType = packet.getMessageType();
        System.out.println("Received " + messageType + " message.");
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
        notifyObservers(packet);
    }

}
