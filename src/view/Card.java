package view;

import Helper.Img;
import Models.Bid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Card extends JPanel {
    public JLabel price;

    private Window parent;
    private JLabel img;
    private JLabel itemName;

    Card(Bid bid,int id, Window parent){
        this.parent=parent;

        initializeGui();
        initCard(bid);

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
                Product product=new Product(bid,500,500,JFrame.EXIT_ON_CLOSE);
                parent.dispose();
            }
        });
    }

    private void initializeGui(){
        this.img=new JLabel();
        this.itemName= new JLabel();
        this.price=new JLabel();

        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.add(Box.createVerticalGlue());
        this.add(img);
        this.add(itemName);

        this.add(Box.createGlue());
        this.add(price);

        setBackground(new Color(0x2C2F33));
        img.setSize(new Dimension(150,100));
        itemName.setForeground(Color.white);
        price.setForeground(Color.gray);

        this.setPreferredSize(new Dimension(150,150));
        this.setVisible(true);
    }

    private void initCard(Bid bid){
        setImg(bid.getImage_path());
        setItemName(bid.getTitle());
        setPrice(bid.getPrice());
    }

    public void setImg(String imgPath) {
        img.setIcon(Img.createImageIcon( Img.readImage(imgPath,new Dimension(img.getWidth(),img.getHeight()))));
    }

    public void setItemName(String itemName) {
        this.itemName.setText( itemName);
    }

    public void setPrice(int price) {
        System.out.println("Name: " + itemName.getText() + " newPrice: " + price);
        this.price.setText(String.valueOf(price)+" $");
    }
}
