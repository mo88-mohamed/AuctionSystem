package view;

import Helper.Countdown;
import Helper.Img;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class Product extends Window {
    private JLabel productImage;
    private JLabel proudctName;
    private JLabel currentProductPrice;
    private JTextField textField1;
    private JTable table1;
    private JPanel root;
    private JButton button1;
    public JLabel countdown;
    private JButton back;
    private JLabel winner;
    private JTextArea description;
    private Timer timer;
    Product(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);
        initializeGui();

        long start=System.currentTimeMillis();
        Countdown count=new Countdown(countdown,start,start+10000);
        count.startCountdown();
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Route.Home();
                dispose();
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



    }

    public void setWinner(String winner){
        this.winner.setText("winner is "+winner);
    }
    public void setCountdown(String text){
        this.countdown.setText(text);
    }
    public void setDescription(String text){
        this.description.setText(text);
    }

}
