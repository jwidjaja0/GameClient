package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Wrappers.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Receive implements Runnable {

    //TODO: Change to ChatMessage
    Socket connection;
    private BlockingQueue<Packet> incoming;

    public Receive(Socket connection, BlockingQueue<Packet> incoming){
        this.connection = connection;
        this.incoming = incoming;
        Thread self = new Thread(this);
        self.start();
    }

    @Override
    public void run() {

        try {
            ObjectInputStream fromServer = new ObjectInputStream(connection.getInputStream());
            while(true){
                incoming.put((Packet) fromServer.readObject());
            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("ClientReceive closing.");
        }
    }
}
