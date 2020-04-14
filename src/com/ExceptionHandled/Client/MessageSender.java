package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Wrappers.Packet;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

public class MessageSender {

    private static MessageSender instance = new MessageSender();
    private String playerID;
    private BlockingQueue<Packet> queue;

    private MessageSender(){

    }

    public static MessageSender getInstance(){
        return instance;
    }

    void setQueue(BlockingQueue<Packet> queue){
        this.queue = queue;
    }

    public void sendMessage(String wrapperType, Serializable message){
        queue.add(new Packet(wrapperType, playerID, message));
    }

    void setPlayerID(String playerID){
        this.playerID = playerID;
    }
}
