package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

//   socket object that listen to the clients
    private ServerSocket serverSocket;

//   create a constructor to serverSocket

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

//    keep server running
    public void startServer() {
//        use try catch to maintain input output error handling
        try {
//            server is running while server socket is open
            while (!serverSocket.isClosed()) {

//        waiting for client
            Socket socket = serverSocket.accept();
            System.out.println("A Client has connected!!!");
            ClientHandler clientHandler = new ClientHandler(socket);

            Thread thread = new Thread(clientHandler);
            thread.start();

            }
        }
        catch (IOException e) {

        }
    }

    public void closeServerSocket() {
//        make sure socket is not null
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException{

        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
//        to keep server running
        server.startServer();
    }
}
