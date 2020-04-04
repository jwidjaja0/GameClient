package com.ExceptionHandled.Client;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private Socket serverConnection;
    private String username;

    public Client() throws IOException {
        serverConnection = new Socket("localhost", 8000);// Create initial connection
        //TODO: Send connection confirmation to server
        //TODO: Create ClientSend, ClientReceive, ClientPublish classes
    }
}
