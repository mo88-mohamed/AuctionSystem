package view;

import javax.swing.*;

public class Route {
    Route(){}




    public static void auctionHall(){
        AuctionHall auctionHall =new AuctionHall("Auction System",500, 500, JFrame.EXIT_ON_CLOSE) ;
    }


//    public static void productPage(String imgPath,String productName,String price){
//        Product product=new Product(productName,500,500,JFrame.EXIT_ON_CLOSE);
//        product.setProductImage(imgPath);
//        product.setProductName(productName);
//        product.setCurrentProductPrice(price);
//    }

    public static void addProudct(){
        BidView bidView =new BidView("Auction System",500,500,JFrame.EXIT_ON_CLOSE);
    }
    public static void login(){
        Login login=new Login("Auction System",310, 150, JFrame.EXIT_ON_CLOSE);
    }
    public static  void register(){
        Register register =new Register("Auction System",310, 150, JFrame.EXIT_ON_CLOSE);

    }
}
