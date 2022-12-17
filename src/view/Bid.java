package view;

import Helper.Img;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Bid extends Window {
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

    Bid(String windowTitle, int width, int height, int defaultCloseOperation) {
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


                Route.Home();
                dispose();
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Route.Home();
                dispose();
            }
        });



    }
    private void initializeGui(){
        chooser =new JFileChooser();

        setProductImage("..\\Auction System\\src\\view\\upload.png");
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
    public int getMinPrice(){
        return Integer.parseInt(minPrice.getText());
    }
    public String getProductName(){
        return productName.getText();
    }
    public long getAuctionTime(){
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
