package com.ExceptionHandled.Miscellaneous;

import com.ExceptionHandled.GameMessages.Wrappers.Packet;

import java.util.concurrent.BlockingQueue;

public class MessageSender {

    private static MessageSender instance = new MessageSender();
    private BlockingQueue<Packet> queue;

    private MessageSender(){

    }

    public static MessageSender getInstance(){
        return instance;
    }

    public void setQueue(BlockingQueue<Packet> queue){
        this.queue = queue;
    }

    public void sendMessage(Packet packet){
        queue.add(packet);
    }
}
