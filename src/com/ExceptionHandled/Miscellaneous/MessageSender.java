package com.ExceptionHandled.Miscellaneous;

import com.ExceptionHandled.GameMessages.Interfaces.*;
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

    public void setQueue(BlockingQueue<Packet> queue){
        this.queue = queue;
    }

    public void sendMessage(String wrapperType, Serializable message){
        queue.add(new Packet(wrapperType, playerID, message));
    }

    public void setPlayerID(String playerID){
        this.playerID = playerID;
    }
}
