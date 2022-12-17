package view;

import ClientSide.ServerConnection;
import Helper.Img;
import Models.Bid;
import Models.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BidView extends Window {
    private JPanel root;
    private JLabel image;
    private JTextField productName;
    private JTextField minPrice;
    private JComboBox hours;
    private JButton submit;
    private JTextArea description;
    private JButton back;
    private String imagePath;
    JFileChooser chooser;

    BidView(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);

        initializeGui();
//        mouse();
//        submitClick();
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = chooser.showOpenDialog(null);
                if (x == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();

                    setProductImage(f.getAbsolutePath());

                }
            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //submiting click

                // createBid

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
                LocalDateTime localDateTime = LocalDateTime.now();
                String now = dateTimeFormatter.format(localDateTime);
                Bid bid = new Bid(getMinPrice(),getAuctionTime(),getProductName(),getDescription(),getProductImage(),now,Login.CurrentUserEmail);
                Message message3 = new Message("createBid", bid);

                try{
                    message3 = (Message) ServerConnection.getInstance().sendMessage(message3);
                }catch (Exception ignored){
                }

                if (message3.getFunctionName().equals("createBid")){
                    boolean result3 = (boolean) message3.getObject();
                    System.out.println("Client: " + result3);
                    Route.auctionHall();
                }

                dispose();
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Route.auctionHall();
                dispose();
            }
        });



    }
    private void initializeGui(){
        chooser =new JFileChooser();

        setProductImage(".\\src\\view\\upload.png");
        ContentPanel.add(root);
        StyleComponents(root);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);

        this.setVisible(true);
    }

    public void setProductImage(String imgPath) {
        image.setSize(new Dimension(150,150));
        this.imagePath=imgPath;
        image.setIcon(Img.createImageIcon( Img.readImage(imgPath,new Dimension(image.getWidth(),image.getHeight()))));
    }

    public String getProductImage() {
        return imagePath;
    }
    public int getMinPrice(){
        return Integer.parseInt(minPrice.getText());
    }
    public String getProductName(){
        return productName.getText();
    }
    public int getAuctionTime(){
        return Integer.parseInt(hours.getSelectedItem().toString())*60*60*1000;
    }
    public String getDescription(){
        return description.getText();
    }

//    public void mouse(){
//        image.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int x = chooser.showOpenDialog(null);
//                if (x == JFileChooser.APPROVE_OPTION) {
//                    File f = chooser.getSelectedFile();
//
//                    setProductImage(f.getAbsolutePath());
//
//                }
//            }
//        });
//    }
//    public void submitClick(){
//        submit.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            //submiting click
//
//
//                Route.Home();
//                dispose();
//            }
//        });
//    }

}
