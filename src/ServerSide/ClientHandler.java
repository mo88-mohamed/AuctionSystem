package ServerSide;

import Models.Bid;
import Models.Message;
import Models.User;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private ClientHandler clientHandler;
    private ArrayList<ClientHandler> clientHandlers;
    private OutputStream outputStream;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;
    private Authentication authentication;
    private BidsHandler bidsHandler;
    private Message message;

    // Constructor
    public ClientHandler(Socket socket, ArrayList<ClientHandler> clientHandlers) throws IOException {
        this.clientSocket = socket;
        this.clientHandlers = clientHandlers;

        outputStream = clientSocket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        inputStream = clientSocket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        authentication = new Authentication();
        bidsHandler = new BidsHandler();
    }
    public void run() {
        try {
            boolean isSocketOpen = true;
            while (isSocketOpen) {
                System.out.println("Server True");
                try {
                    message = (Message) objectInputStream.readObject();
                    if (message == null)
                        System.out.println("Nulll");
                    else
                        System.out.println("Not null");
                    System.out.println("Try");
                } catch (Exception e) {
                    System.out.println("No messega from client");
                    continue;
                }
                if (message.getFunctionName().equals("isUserExist")) {
                    message.setObject(authentication.isUserExist(message.getParams()));
                    objectOutputStream.writeObject(message);
                } else if (message.getFunctionName().equals("createNewUser")) {
                    User user = (User) message.getObject();
                    message.setObject(authentication.createNewUser(user));
                    objectOutputStream.writeObject(message);
                } else if (message.getFunctionName().equals("login")) {
                    User user = (User) message.getObject();
                    message.setObject(authentication.logIn(user));
                    objectOutputStream.writeObject(message);
                } else if (message.getFunctionName().equals("createBid")) {
                    Bid bid = (Bid) message.getObject();
                    message.setObject(bidsHandler.createBid(bid));
                    objectOutputStream.writeObject(message);
                } else if (message.getFunctionName().equals("getAllBids")) {
                    message.setObject(bidsHandler.getAllBids());
                    objectOutputStream.writeObject(message);
                } else if (message.getFunctionName().equals("updatePrice")) {
                    List<String> params = Arrays.asList(message.getParams().split(","));
                    if (bidsHandler.updatePrice(Integer.parseInt(params.get(0)), Integer.parseInt(params.get(1)), params.get(2))) {
                        message.setObject(true);
                        objectOutputStream.writeObject(message);
                        System.out.println("Updated");
                        ServerHandler.bidsList = bidsHandler.getAllBids();
                    } else {
                        message.setObject(false);
                        objectOutputStream.writeObject(message);
                        System.out.println("Not up dated");
                    }
                } else if (message.getFunctionName().equals("getAllBidsLive")) {
                    message.setObject(ServerHandler.bidsList);
                    objectOutputStream.writeObject(message);
                    System.out.println("Array list is sent");
                } else if (message.getFunctionName().equals("closeConnection")) {
                    System.out.println("This socket will be closed");
                    message.setObject(true);
                    objectOutputStream.writeObject(message);
                    clientSocket.close();
                    objectInputStream.close();
                    objectOutputStream.close();
                    clientHandlers.remove(clientHandler);
                    isSocketOpen = false;
                    break;
                }
                else
                    objectOutputStream.writeObject("Wrong Request");
                Thread.sleep(1000);
            }
        } catch (IOException | SQLException | InterruptedException e) {
            System.out.println("Catch");
            e.printStackTrace();
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

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }
}
