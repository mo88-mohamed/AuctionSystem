package view;

import Helper.Img;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Card extends JPanel {

    private Window parent;
    private JLabel img;
    private JLabel itemName;
    private JPanel card;

    public JLabel price;

    private String imgPath;
//    Card(){
//        this.card=new JPanel();
//        this.img=new JLabel();
//        this.itemName= new JLabel();
//        initializeGui();
//    }
    Card(String imgPath,String itemName,int price,Window parent){

        this.parent=parent;
        initializeGui();
        setImg(imgPath);
        setItemName(itemName);
        setPrice(price);


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(0x3b3f44));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(0x2C2F33));
            }
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println("hello");
                Product product=new Product(itemName,500,500,JFrame.EXIT_ON_CLOSE);
                product.setProductImage(imgPath);
                product.setProductName(itemName);
                product.setCurrentProductPrice(String.valueOf(price));
                parent.dispose();
            }
        });

//        hover();
//        click();

    }
    private void initializeGui(){
        this.card=new JPanel();
        this.img=new JLabel();
        this.itemName= new JLabel();
        this.price=new JLabel();


        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.add(Box.createVerticalGlue());
        this.add(img);
        this.add(itemName);

        this.add(Box.createGlue());
        this.add(price);



        img.setSize(new Dimension(150,100));
        itemName.setForeground(Color.white);
        price.setForeground(Color.gray);

        this.setPreferredSize(new Dimension(150,150));


        this.setVisible(true);
    }

    public void setImg(String imgPath) {

        img.setIcon(Img.createImageIcon( Img.readImage(imgPath,new Dimension(img.getWidth(),img.getHeight()))));

        this.imgPath=imgPath;


    }

    public void setItemName(String itemName) {
        this.itemName.setText( itemName);
    }

    public void setPrice(int price) {
        this.price.setText(String.valueOf(price)+" $");
    }

//    private void hover(){
//        JPanel card=this;
//        card.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                card.setBackground(new Color(0x3b3f44));
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//                card.setBackground(new Color(0x2C2F33));
//            }
//            @Override
//            public void mouseClicked(MouseEvent e) {
////                System.out.println("hello");
//                Product product=new Product(itemName.getText(),500,500,JFrame.EXIT_ON_CLOSE);
//                product.setProductImage(imgPath);
//                product.setProductName(itemName.getText());
//                product.setCurrentProductPrice(price.getText());
//                parent.dispose();
//            }
//        });
//
//    }


}
