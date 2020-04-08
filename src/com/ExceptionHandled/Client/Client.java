package com.ExceptionHandled.Client;

import com.ExceptionHandled.GameMessages.Connection.*;
import com.ExceptionHandled.GameMessages.Wrappers.*;
import com.ExceptionHandled.InternalWrapper.InternalPacket;
import com.ExceptionHandled.Miscellaneous.MessageSender;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Client extends Observable implements Observer {
    private Socket serverConnection;
    private String username;
    private Send send;
    private Receive receive;
    private Publish publish;

    //TODO: Change to packet
    private BlockingQueue<Packet> outgoing;
    private BlockingQueue<Packet> incoming;


    public Client() throws IOException {
        serverConnection = new Socket("localhost", 8000);// Create initial connection, need server to be up

        outgoing = new ArrayBlockingQueue<>(100);
        incoming = new ArrayBlockingQueue<>(100);

        send = new Send(serverConnection, outgoing);
        receive = new Receive(serverConnection, incoming);
        publish = new Publish(incoming);
        publish.addObserver(this);
        outgoing.add(new Packet("Connection", new Connection("ConnectionRequest",new ConnectionRequest())));

        MessageSender.getInstance().setQueue(outgoing);
    }


    @Override
    public void update(Observable o, Object arg) {
        InternalPacket packet = (InternalPacket)arg;
        if (packet.getDirection().equals("ToUI")){
            setChanged();
            notifyObservers(packet);
        }
    }
}
