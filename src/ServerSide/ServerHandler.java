package ServerSide;

import Models.Bid;
import Models.Message;
import Models.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerHandler {
    private static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public static int num;
    public static void main(String[] args) throws ClassNotFoundException {
        ServerSocket server = null;
        try {
            // server is listening on port 12345
            server = new ServerSocket(12345);
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {
                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected: "
                        + client.getInetAddress().getHostAddress());

                // create a new thread object
                ClientHandler clientSocket = new ClientHandler(client, clientHandlers);
                clientHandlers.add(clientSocket);
                // This thread will handle the client
                // separately
                new Thread(clientSocket).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // ClientHandler class
    /*
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private Authentication authentication = new Authentication();
        private BidsHandler bidsHandler = new BidsHandler();

        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }
        public void run() {
            OutputStream outputStream = null;
            ObjectOutputStream objectOutputStream = null;
            InputStream inputStream = null;
            ObjectInputStream objectInputStream = null;
            Message message = null;
            try {
                outputStream = clientSocket.getOutputStream();
                objectOutputStream = new ObjectOutputStream(outputStream);
                inputStream = clientSocket.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);

                message = (Message) objectInputStream.readObject();

                if (message.getFunctionName().equals("isUserExist"))
                    objectOutputStream.writeObject(authentication.isUserExist(message.getParams()));
                else if (message.getFunctionName().equals("createNewUser")) {
                    User user = (User) message.getObject();
                    objectOutputStream.writeObject(authentication.createNewUser(user));
                } else if (message.getFunctionName().equals("login")) {
                    User user = (User) message.getObject();
                    objectOutputStream.writeObject(authentication.logIn(user));
                } else if (message.getFunctionName().equals("createBid")) {
                    Bid bid = (Bid) message.getObject();
                    objectOutputStream.writeObject(bidsHandler.createBid(bid));
                } else if (message.getFunctionName().equals("getAllBids")) {
                    List<Bid> bidsList = (List<Bid>) message.getObject();
                    objectOutputStream.writeObject(bidsHandler.getAllBids());
                } else if (message.getFunctionName().equals("updatePrice")) {
                    List<String> params = Arrays.asList(message.getParams().split(","));
                    objectOutputStream.writeObject(bidsHandler.updatePrice(Integer.parseInt(params.get(0)),Integer.parseInt(params.get(1)),params.get(2)));
                } else
                    objectOutputStream.writeObject("Wrong Request");

            } catch (IOException | ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    if (objectInputStream != null) {
                        objectInputStream.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}
