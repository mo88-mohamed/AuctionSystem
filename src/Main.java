import Models.Bid;
import Models.Message;
import Models.User;
import ServerSide.Authentication;
import ServerSide.BidsHandler;
import ServerSide.ClientHandler;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static List<Bid> bidsList;
    public static void main(String[] args) throws SQLException, InterruptedException {
        try (Socket socket = new Socket("localhost", 12345)) {
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            // check isUserExist
            /*
            String email = "ahmed@gmail.com";
            Message message = new Message("isUserExist",""+email);
            objectOutputStream.writeObject(message);
            boolean result = (boolean) objectInputStream.readObject();
            System.out.println("Client: " + result);
            */

            // createNewUser
            /*
            User user = new User("ali@gmail.com","Ali Mohamed","123456");
            Message message = new Message("createNewUser", user);
            objectOutputStream.writeObject(message);
            boolean result = (boolean) objectInputStream.readObject();
            System.out.println("Client: " + result);
            */

            // login
            /*
            User user = new User("ali@gmail.com","123456");
            Message message = new Message("login", user);
            objectOutputStream.writeObject(message);
            boolean result = (boolean) objectInputStream.readObject();
            System.out.println("Client: " + result);
            */

            // createBid
            /*
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
            LocalDateTime localDateTime = LocalDateTime.now();
            String now = dateTimeFormatter.format(localDateTime);
            Bid bid = new Bid(7000,3,"t3","d3","p3",now,"ali@gmail.com");
            Message message = new Message("createBid", bid);
            objectOutputStream.writeObject(message);
            boolean result = (boolean) objectInputStream.readObject();
            System.out.println("Client: " + result);
            */

            // getAllBids
            /*
            Message message = new Message("getAllBids");
            objectOutputStream.writeObject(message);
            List<Bid> bidsList = (List<Bid>) objectInputStream.readObject();
            for (Bid bid : bidsList)
                System.out.println("Client: " + bid.getTitle());
            */

            // updatePrice

            int id = 12, price = 37000;
            // winner is the current user email
            String winner = "ali@gmail.com";
            Message message = new Message("updatePrice", "" + id + "," + price + "," + winner);
            objectOutputStream.writeObject(message);
            boolean result = (boolean) objectInputStream.readObject();
            System.out.println("Client: " + result);


            // getAllBidsLive
            /*
            Message message = new Message("getAllBidsStatic","3");
            objectOutputStream.writeObject(message);
            System.out.println(objectInputStream.readObject());
            */
            while (true) {
                try {
                    Thread.sleep(2000);
                    System.out.println(objectInputStream.readObject());
                    if (objectOutputStream == null)
                        System.out.println("NULLLLLLL");
                    else
                        System.out.println(objectOutputStream);
                    message = (Message) objectInputStream.readObject();
                    if (message.getFunctionName().equals("setAllBidsStatic")) {
                        bidsList = (List<Bid>) message.getObject();
                        for (Bid bid:bidsList) {
                            System.out.println("New Client");
                            System.out.println("id: " + bid.getId() + " price: " + bid.getPrice());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Failed");
                }


            }



        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Thread.sleep(100000);
    }
}