package com.ExceptionHandled.InternalWrapper;

import java.io.Serializable;

public class InternalPacket {
    private String messageType;
    private String direction;
    private Serializable message;

    public InternalPacket(String messageType, String direction, Serializable message) {
        this.messageType = messageType;
        this.direction = direction;
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public Serializable getMessage() {
        return message;
    }

    public String getDirection(){
        return direction;
    }
}
