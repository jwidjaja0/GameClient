package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Wrappers.Packet;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.BlockingQueue;

public class Publish extends Observable implements Runnable{
    //private ArrayList<ClientChatroom> chatrooms;
    private BlockingQueue<Packet> incoming;

    //public Publish(ArrayList<ClientChatroom> chatrooms, BlockingQueue<Packet> incoming){
    public Publish(BlockingQueue<Packet> incoming){
        //this.chatrooms = chatrooms;
        this.incoming = incoming;
        Thread self = new Thread(this);
        self.start();
    }


    @Override
    public void run() {
        // Grabs available messages from incoming and processes it and sends to relevant object
        try {
            while (true) {
                Packet packet = incoming.take();// Get the packet from the incoming queue
                //TODO: Filterpackets here
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("ClientPublish closing.");
        }
    }
}
