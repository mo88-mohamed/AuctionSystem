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
    private JTextField price;
//    private JComboBox hours;
    private JButton submitBtn;
    private JTextArea description;
    private JButton backBtn;
    private JTextField hours;
    private String imagePath;
    JFileChooser chooser;

    BidView(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);

        initializeGui();

        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fileBrowserOutput = chooser.showOpenDialog(null);
                if (fileBrowserOutput == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    setProductImage(f.getAbsolutePath());
                }
            }
        });

        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // On creating bid button click

                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0");
                String currentDateTime = dateFormat.format(LocalDateTime.now());
                int time;
                if (!checkValidProductName()){
                    emptyProductNameError();
                    return;
                }

                if (!checkValidPrice()){
                    priceFormatError();
                    return;
                }
                try{
                    time=getAuctionTime();
                }
                catch (Exception exception){
                    JOptionPane.showMessageDialog(null,"duration must be Integer number");
                    return;
                }
                Bid bid = new Bid(getPrice(),time,getProductName(),getDescription(),getProductImage(),currentDateTime,Login.CurrentUserEmail);
                Message message3 = new Message("createBid", bid);

                try{
                    message3 = (Message) ServerConnection.getInstance().sendMessage(message3);
                } catch (Exception ignored){
                }

                if (message3.getFunctionName().equals("createBid")){
                    Route.auctionHallWindow();
                }

                dispose();
            }
        });
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Route.auctionHallWindow();
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
    public int getPrice(){
        return Integer.parseInt(price.getText());
    }
    public String getProductName(){
        return productName.getText();
    }
    public int getAuctionTime(){

        return Integer.parseInt(hours.getText());
    }
    public String getDescription(){
        return description.getText();
    }

    private boolean checkValidProductName(){
        return !getProductName().equals("");
    }

    private boolean checkValidPrice(){
        try{
            getPrice();
            return true;
        } catch (Exception exception){
            return false;
        }
    }

    private void priceFormatError(){
        JOptionPane.showMessageDialog(null,"Price must be a number");
    }

    private void emptyProductNameError(){
        JOptionPane.showMessageDialog(null,"Product name is required");
    }

}
