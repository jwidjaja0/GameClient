package com.ExceptionHandled.InternalWrapper;

import java.io.Serializable;

public class InternalPacket {
    private String messageType;
    private Serializable message;

    public InternalPacket(String messageType, Serializable message) {
        this.messageType = messageType;
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public Serializable getMessage() {
        return message;
    }
}
