package ServerSide;

;

import ClientSide.BidsHandler;
import Models.Bid;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHandler {
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public static volatile List<Bid> bidsList = new ArrayList<>();
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ServerSocket serverSocket = null;
        try {
            // server is listening on port 1234
            serverSocket = new ServerSocket(12345);
            serverSocket.setReuseAddress(true);

            BidsHandler bidsHandler = new BidsHandler();
            // init the bids list from database when server runs first time
            bidsList = bidsHandler.getAllBids();

            // running infinite loop for getting
            // client request
            while (true) {
                // socket object to receive incoming client
                // requests
                Socket clientSocket = serverSocket.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected"
                        + clientSocket.getInetAddress()
                        .getHostAddress());

                // create a new thread object
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientHandlers);
                clientHandler.setClientHandler(clientHandler);
                // This thread will handle the client
                // separately
                executorService.execute(clientHandler);
                clientHandlers.add(clientHandler);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
        /*
        ServerSocket server = new ServerSocket(12345);

        while (true) {
            Socket client = server.accept();
            System.out.println("New client connected: " + client.getInetAddress().getHostAddress());

            ClientHandler clientSocket = new ClientHandler(client, clientHandlers);
            executorService.execute(clientSocket);
            clientHandlers.add(clientSocket);
        }

    }

         */
}
