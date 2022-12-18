package view;

import javax.swing.*;

public class Route {
    Route(){}

    public static void auctionHallWindow(){
        AuctionHall auctionHall =new AuctionHall("Auction System",500, 500, JFrame.EXIT_ON_CLOSE) ;
    }

    public static void addProductWindow(){
        BidView bidView =new BidView("Auction System",500,500,JFrame.EXIT_ON_CLOSE);
    }

    public static void loginWindow(){
        Login login=new Login("Auction System",310, 150, JFrame.EXIT_ON_CLOSE);
    }

    public static  void registerWindow(){
        Register register =new Register("Auction System",310, 150, JFrame.DISPOSE_ON_CLOSE);
    }
}
