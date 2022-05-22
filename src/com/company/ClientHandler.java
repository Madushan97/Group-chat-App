package com.company;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

//    adding properties
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

//    creating constructor for ClientHandler
    public ClientHandler(Socket socket) {
        try{

            this.socket = socket;
//        we are going to send things
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) ;
//        we are going to read things
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        to get input (sout)
            this.clientUsername = bufferedReader.readLine();
//        add client to the arrayList(GroupChat)
            clientHandlers.add(this);
//        enter broadcast message
            broadcastMessage("SERVER: "+ clientUsername + " Has entered the chat!");
        }
        catch (IOException e) {

            closeEveryThing(socket, bufferedReader, bufferedWriter);
        }

    }

    @Override
    public void run() {

        String messageFromClient;

        while (socket.isConnected()) {

            try {
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            }
            catch (IOException e) {

                closeEveryThing(socket, bufferedWriter, bufferedReader);
                break;
            }
        }
    }
}
