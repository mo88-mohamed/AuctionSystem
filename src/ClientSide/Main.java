package ClientSide;

import Models.Bid;
import Models.Message;
import view.AuctionHall;
import view.Route;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Bid> bidsList = new ArrayList<>();
    public static boolean isListUpdated;
    public static void main(String[] args) throws SQLException, InterruptedException {
        try {
            // this is must be in a first line in your main program
            ServerConnection.getInstance().startSession();

            bidsList = ServerConnection.getInstance().getBidsList();

            Route.loginWindow();

            // this loop must be in main in client this checks every second if there is any update in the bid list
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message6;
                    List<Bid> resultList;
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        message6 = new Message("getAllBidsLive");
                        try {
                            message6 = (Message) ServerConnection.getInstance().sendMessage(message6);
                        } catch (IOException | ClassNotFoundException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }


                        isListUpdated = Boolean.parseBoolean(message6.getParams());
                        if (isListUpdated) {
                            isListUpdated = false;

                            System.out.println("There is an update");

                            bidsList = (List<Bid>) message6.getObject();

                            AuctionHall.Instance.redrawCards();

                        }
                    }
                }
            }).start();
            // close function must be called when user press on red x to close the gui and the gui mustn't be closed until this function return true
//            Message message5 = new Message("closeConnection");
//            message5 = (Message) ServerConnection.getInstance().sendMessage(message5);
//            boolean result5 = (boolean) message5.getObject();
//            if (result5) {
//                System.out.println("Client closed: " + result5);
//                System.exit(0);
//            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}