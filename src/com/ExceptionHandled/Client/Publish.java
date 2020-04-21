package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Login.LoginSuccess;
import com.ExceptionHandled.GameMessages.Wrappers.Packet;


import java.util.Observable;
import java.util.concurrent.BlockingQueue;

public class Publish extends Observable implements Runnable{
    private BlockingQueue<Packet> incoming;

    public Publish(BlockingQueue<Packet> incoming){
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
                System.out.println("Received " + packet.getMessageType() + " message.");
                System.out.println("PlayerID: " + packet.getPlayerID());
                if (packet.getMessage() instanceof LoginSuccess){
                    System.out.println(((LoginSuccess) packet.getMessage()).getPlayerID());
                }
                packetFilter(packet);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("ClientPublish closing.");
        }
    }

    private void packetFilter(Packet packet){
        setChanged();
        notifyObservers(packet);
    }

}
