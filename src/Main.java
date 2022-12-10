import Models.Bid;
import Models.Message;
import Models.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static List<Bid> bidsList;
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    public static void main(String[] args) throws SQLException, InterruptedException {
        try {
            // this is must be in a first line in your main program
            ServerConnection.getInstance().startSession();

            // check isUserExist

            String email = "ali@gmail.com";
            Message message = new Message("isUserExist",""+email);
            message = (Message) ServerConnection.getInstance().sendMessage(message);
            boolean result = (boolean) message.getObject();
            System.out.println("Client: " + result);


            // createNewUser

            User user1 = new User("mohamed@gmail.com","Mohamed","123456");
            Message message2 = new Message("createNewUser", user1);
            message2 = (Message) ServerConnection.getInstance().sendMessage(message2);
            if (message2.getFunctionName().equals("createNewUser")) {
                boolean result2 = (boolean) message2.getObject();
                System.out.println("Client: " + result2);
            }



            // login

            User user = new User("ali@gmail.com","123456");
            Message message1 = new Message("login", user);
            message1 = (Message) ServerConnection.getInstance().sendMessage(message1);
            boolean result1 = (boolean) message1.getObject();
            System.out.println("Client: " + result1);


            // createBid

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
            LocalDateTime localDateTime = LocalDateTime.now();
            String now = dateTimeFormatter.format(localDateTime);
            Bid bid = new Bid(4000,400,"t4","d4","p4",now,"abdallah@gmail.com");
            Message message3 = new Message("createBid", bid);
            message3 = (Message) ServerConnection.getInstance().sendMessage(message3);
            boolean result3 = (boolean) message3.getObject();
            System.out.println("Client: " + result3);


            // getAllBids
            // call this function in the main to init the static bidsList from the database

            bidsList =  ServerConnection.getInstance().getBidsList();
            if (bidsList != null) {
                for (Bid bid1 : bidsList)
                    System.out.println("id: " + bid1.getId() + " price: " + bid1.getPrice());
            }


            // updatePrice

            int id = 16, price = 67000;
            // winner is the current user email
            String winner = "ali@gmail.com";
            Message message4 = new Message("updatePrice", "" + id + "," + price + "," + winner);
            message4 = (Message) ServerConnection.getInstance().sendMessage(message4);
            boolean result4 = (boolean) message4.getObject();
            System.out.println("Client: " + result4);


            // this loop must be in main in client this checks every second if there is any update in the bid list

            while (true) {
                // this to notify when update happens
                if (ServerConnection.getInstance().getIsListUpdated()) {
                    bidsList = ServerConnection.getInstance().getBidsList();
                    ServerConnection.getInstance().setIsListUpdated(false);
                    if (bidsList != null) {
                        System.out.println("The list is updated");
                        for (Bid bid1 : bidsList)
                            System.out.println("id: " + bid1.getId() + " price: " + bid1.getPrice());
                    }
                }
            }


            // close function must be called when user press on red x to close the gui and the gui mustn't be closed until this function return true
            /*
            Message message = new Message("closeConnection");
            boolean result = (boolean) ServerConnection.getInstance().sendMessage(message);
            if (result) {
                System.out.println("Client closed: " + result);
                System.exit(0);
            }
             */
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}