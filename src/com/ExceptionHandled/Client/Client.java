package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Wrappers.Packet;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Client {
    private Socket serverConnection;
    private String username;
    private Send send;
    private Receive receive;
    private Publish publish;

    //TODO: Change to packet
    private BlockingQueue<Packet> outgoing;
    private BlockingQueue<Packet> incoming;


    public Client() throws IOException {
        serverConnection = new Socket("localhost", 8000);// Create initial connection, need server to be up
        //TODO: Send connection confirmation to server
        //TODO: Create ClientSend, ClientReceive, ClientPublish classes
    }
}
