package view;

import ClientSide.ServerConnection;
import Database.Database;
import Helper.Countdown;
import Helper.Img;
import Models.Bid;
import Models.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Product extends Window {
    private JLabel productImage;
    private JLabel proudctName;
    private JLabel currentProductPrice;
    private JTextField newBid;
    private JTable table1;
    private JPanel root;
    private JButton newBidButton;
    public JLabel countdown;
    private JButton back;
    private JLabel winner;
    private JTextArea description;
    private Timer timer;
    Product(Bid bid, int width, int height, int defaultCloseOperation) {
        super(bid.getTitle(), width, height, defaultCloseOperation);
        // TODO: make function get Bid with id
        setProductImage(bid.getImage_path());
        setProductName(bid.getTitle());
        setCurrentProductPrice(String.valueOf(bid.getPrice()));
        setDescription(bid.getDescription());
        // TODO: give the correct winner fullname
        setWinner(Login.CurrentUserEmail);

        initializeGui();
        Date creationDate= null;

        try {
            creationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0").parse(bid.getCreation_date());

            long start=creationDate.getTime();
            long endTime = start + bid.getDuration();
            Countdown count=new Countdown(countdown,start, endTime);
            count.startCountdown();
        } catch (ParseException ignored) {
        }

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Route.auctionHall();
                dispose();
            }
        });

        newBidButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // updatePrice

                int id = bid.getId();
                int price;
                try{
                    price = Integer.parseInt(newBid.getText());
                    // winner is the current user email
                    String winner = Login.CurrentUserEmail;
                    Message message4 = new Message("updatePrice", "" + id + "," + price + "," + winner);
                    message4 = (Message) ServerConnection.getInstance().sendMessage(message4);
                    if (message4.getFunctionName().equals("updatePrice")){
                        boolean result4 = (boolean) message4.getObject();
                        if (result4){
                            // successfully updated price
                            System.out.println("Client: " + result4);
                        }
                    }

                }catch (Exception exception){
                    bidError();
                }
            }
        });
    }



    private void initializeGui(){



        productImage.setText("");
        productImage.setSize(new Dimension(200,200));

        winner.setSize(200,200);

        countdown.setSize(new Dimension(200,200));
        countdown.setFont(new Font(countdown.getFont().getFontName(),Font.BOLD,30));

        proudctName.setFont(new Font(proudctName.getFont().getFontName(),Font.BOLD,25));

        currentProductPrice.setFont(new Font(currentProductPrice.getFont().getFontName(),Font.BOLD,15));
        winner.setForeground(Color.blue);
        ContentPanel.add(root);
        StyleComponents(root);
        this.setVisible(true);
    }

    public void setProductName(String proudctName) {
        this.proudctName.setText( proudctName);
    }
    public void setCurrentProductPrice(String price){
        this.currentProductPrice.setText(price);
    }

    public void setProductImage(String imgPath) {
        productImage.setSize(new Dimension(200,200));
        productImage.setIcon(Img.createImageIcon( Img.readImage(imgPath,new Dimension(productImage.getWidth(),productImage.getHeight()))));
        System.out.println("imgPath: " + imgPath);


    }

    public void setWinner(String winner){
        this.winner.setText("winner is "+winner);
    }
//    public void setCountdown(String text){
//        this.countdown.setText(text);
//    }
    public void setDescription(String text){
        this.description.setText(text);
    }

    private void bidError(){

        JOptionPane.showMessageDialog(null,"Bid Error");

    }

}
