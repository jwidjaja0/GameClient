package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Connection.*;
import com.ExceptionHandled.GameMessages.Wrappers.*;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client implements Runnable{
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

        outgoing = new ArrayBlockingQueue<>(100);
        incoming = new ArrayBlockingQueue<>(100);

        send = new Send(serverConnection, outgoing);
        receive = new Receive(serverConnection, incoming);
        publish = new Publish(incoming);
        outgoing.add(new Packet("Connection", new Connection("ConnectionRequest",new ConnectionRequest())));
    }

    @Override
    public void run() {
        while(true){

        }
    }
}
