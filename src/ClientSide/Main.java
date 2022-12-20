package ClientSide;

import Models.Bid;
import Models.Message;
import view.AuctionHall;
import view.Product;
import view.Route;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Bid> bidsList = new ArrayList<>();
    public static boolean isListUpdated, isServerOpen;
    public static void main(String[] args) throws SQLException, InterruptedException {
        try {
            // this is must be in a first line in your main program
            ServerConnection.getInstance().startSession();
            isServerOpen = true;

            bidsList = ServerConnection.getInstance().getBidsList();

            Route.loginWindow();

            // this loop must be in main in client this checks every second if there is any update in the bid list
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message;
                    List<Bid> resultList;
                    while (isServerOpen) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        message = new Message("getAllBidsLive");
                        try {
                            message = (Message) ServerConnection.getInstance().sendMessage(message);
                        } catch (IOException | ClassNotFoundException | InterruptedException e) {
                            Thread.interrupted();
                        }


                        isListUpdated = Boolean.parseBoolean(message.getParams());
                        if (isListUpdated) {
                            isListUpdated = false;

                            System.out.println("There is an update");

                            bidsList = (List<Bid>) message.getObject();

                            System.out.println("Redraw");
                            AuctionHall.Instance.redrawCards();
                            try {
                                for (Bid bid1 :bidsList) {
                                    if(bid1.getId()==Product.in.getCurrentBid().getId()){
                                        System.out.println("done");
                                        Product.in.updateGui(bid1);
                                        break;
                                    }
                                }
                            } catch (Exception ignore) {

                            }
                        }
                    }
                }
            }).start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onApplicationExit(){
        //close function must be called when user press on red x to close the gui and the gui mustn't be closed until this function return true
        Message message = new Message("closeConnection");
        try{
            message = (Message) ServerConnection.getInstance().sendMessage(message);

            boolean result = (boolean) message.getObject();

            if (result) {
                isServerOpen = false;
                System.out.println("Client closed: " + result);
                System.exit(0);
            }
        } catch (Exception ignored){
        }
    }

}