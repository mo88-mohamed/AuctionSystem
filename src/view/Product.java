package view;

import ClientSide.ServerConnection;
import Helper.Countdown;
import Helper.Img;
import Models.Bid;
import Models.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Product extends Window {
    private JLabel productImage;
    private JLabel productName;
    private JLabel currentProductPrice;
    private JTextField newBid;
    private JPanel root;
    private JButton newBidBtn;
    public JLabel countdown;
    private JButton backBtn;
    private JTextArea description;

    private String winnerEmail;
    public static Product in;
    Countdown count;
    private  Bid bid;
    Product(Bid bid, int width, int height, int defaultCloseOperation) {
        super(bid.getTitle(), width, height, defaultCloseOperation);
        this.bid=bid;
        in=this;
        // TODO: make function get Bid with id

        initData();
        initializeGui();
        Date creationDate = null;

        try {
            creationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(bid.getCreation_date());

            long start=creationDate.getTime();
            long endTime = start + (bid.getDuration()*60*60*1000);
            count=new Countdown(countdown,start, endTime);
            count.startCountdown();

            if (count.getTimeLeft() <= 0){
                newBidBtn.setEnabled(false);
                newBid.setEnabled(false);
            }
        } catch (ParseException ignored) {
        }

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Route.auctionHallWindow();
                dispose();
            }
        });

        newBidBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = bid.getId();
                int price = 999999999;

                if (count.getTimeLeft() <= 0) {
                    timeError();
                } else {

                    try {
                        price = getPrice();

                        // winner is the current user email
                        setWinnerAsCurrentUser();

                        if (!updatePrice(id, price, winnerEmail)) {
                            bidError();
                        } else
                            setCurrentProductPrice(""+price);
                    } catch (NumberFormatException exception) {
                        bidError();
                    }
                }
            }
        });
    }

    private void initData(){
        setProductImage(bid.getImage_path());
        setProductName(bid.getTitle());
        setCurrentProductPrice(String.valueOf(bid.getPrice()));
        setDescription(bid.getDescription());
    }
    public void updateGui(Bid bid){
        this.bid=bid;
        initData();
    }
    public Bid getCurrentBid(){
        return bid;
    }
    private void setWinnerAsCurrentUser(){
        winnerEmail = Login.CurrentUserEmail;
    }

    private int getPrice(){
        return Integer.parseInt(newBid.getText());
    }

    private boolean updatePrice(int id, int price, String winnerEmail){
        try{
            Message message = new Message("updatePrice", "" + id + "," + price + "," + winnerEmail);
            message = (Message) ServerConnection.getInstance().sendMessage(message);
            if (message.getFunctionName().equals("updatePrice")){
                return (boolean) message.getObject();
            }
        }catch (Exception ignored){
        }

        return false;
    }

    private void initializeGui(){
        productImage.setText("");
        productImage.setSize(new Dimension(200,200));

        countdown.setSize(new Dimension(200,200));
        countdown.setFont(new Font(countdown.getFont().getFontName(),Font.BOLD,30));

        productName.setFont(new Font(productName.getFont().getFontName(),Font.BOLD,25));

        currentProductPrice.setFont(new Font(currentProductPrice.getFont().getFontName(),Font.BOLD,15));
        ContentPanel.add(root);
        StyleComponents(root);
        this.setVisible(true);
    }

    public void setProductName(String proudctName) {
        this.productName.setText( proudctName);
    }
    public void setCurrentProductPrice(String price){
        this.currentProductPrice.setText(price);
    }

    public void setProductImage(String imgPath) {
        productImage.setSize(new Dimension(200,200));
        productImage.setIcon(Img.createImageIcon( Img.readImage(imgPath,new Dimension(productImage.getWidth(),productImage.getHeight()))));
        System.out.println("imgPath: " + imgPath);
    }

    public void setDescription(String text){
        this.description.setText(text);
    }

    private void bidError(){
        JOptionPane.showMessageDialog(null,"Bid Error");
    }
    private void timeError(){
        JOptionPane.showMessageDialog(null,"time ended");
    }
}
