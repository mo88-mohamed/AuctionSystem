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
    private ArrayList<ClientHandler> clientHandlers;
    private Authentication authentication = new Authentication();
    private BidsHandler bidsHandler = new BidsHandler();
    private OutputStream outputStream = null;
    private ObjectOutputStream objectOutputStream = null;
    private InputStream inputStream = null;
    private ObjectInputStream objectInputStream = null;
    private Message message = null;

    // Constructor
    public ClientHandler(Socket socket, ArrayList<ClientHandler> clientHandlers) throws IOException, ClassNotFoundException {
        this.clientSocket = socket;
        this.clientHandlers = clientHandlers;
        outputStream = clientSocket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        inputStream = clientSocket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        message = (Message) objectInputStream.readObject();
    }
    public void run() {
        try {


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
                objectOutputStream.writeObject(bidsHandler.getAllBids());


            } else if (message.getFunctionName().equals("updatePrice")) {
                List<String> params = Arrays.asList(message.getParams().split(","));
                if (bidsHandler.updatePrice(Integer.parseInt(params.get(0)),Integer.parseInt(params.get(1)),params.get(2))) {
                    objectOutputStream.writeObject(true);
                    List<Bid> bidsList = bidsHandler.getAllBids();
                    for (Bid bid:bidsList) {
                        System.out.println("New Client");
                        System.out.println("id: " + bid.getId() + " price: " + bid.getPrice());
                    }
                    message = new Message("setAllBidsStatic", bidsList);
                    for (ClientHandler clientHandler:clientHandlers)
                        //clientHandler.objectOutputStream.writeObject(message);
                        clientHandler.objectOutputStream.writeObject("I am Here Baby");
                } else
                    objectOutputStream.writeObject(false);
            }
//            else if (message.getFunctionName().equals("getAllBidsStatic")) {
//                ServerHandler.num += Integer.parseInt(message.getParams());
//                objectOutputStream.writeObject(ServerHandler.num);
//            }

            else
                objectOutputStream.writeObject("Wrong Request");

        } catch (IOException | SQLException e) {
            System.out.println("Catch");
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
}
