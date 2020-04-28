package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Wrappers.Packet;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

public class MessageSender {

    private static MessageSender instance = new MessageSender();
    private String playerID;
    private BlockingQueue<Packet> queue;

    private MessageSender(){
        playerID = null;
    }

    public static MessageSender getInstance(){
        return instance;
    }

    void setQueue(BlockingQueue<Packet> queue){
        this.queue = queue;
    }

    public void sendMessage(String wrapperType, Serializable message){
        sendMessage(wrapperType, playerID, message);
    }

    public void sendMessage(String wrapperType, String id, Serializable message){
        try {
            queue.put(new Packet(wrapperType, id, message));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void setPlayerID(String playerID){
        this.playerID = playerID;
        System.out.println("Setting player ID in MessageSender");
    }

    void logOut(){
        playerID = null;
    }
}
