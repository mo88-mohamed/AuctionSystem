package ServerSide;

import Models.Bid;
import Models.Message;
import Models.User;

import java.io.*;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerHandler {
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        ServerSocket serverSocket = null;
        try {
            // server is listening on port 1234
            serverSocket = new ServerSocket(12345);
            serverSocket.setReuseAddress(true);

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
        } catch (IOException e) {
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
