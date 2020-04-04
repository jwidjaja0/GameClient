package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Wrappers.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Send implements Runnable {
    private Socket connection;
    private BlockingQueue<Packet> outgoing;

    public Send(Socket connection, BlockingQueue<Packet> outgoing){
        this.connection = connection;//Reference to socket connection
        this.outgoing = outgoing;// Reference to outgoing blocking queue
        Thread self = new Thread(this);
        self.start();
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(connection.getOutputStream());
            while(true){
                System.out.println("Sending message to server");
                toServer.writeObject(outgoing.take());//Pull from blocking queue
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("ClientSend closing.");
        }
    }
}
