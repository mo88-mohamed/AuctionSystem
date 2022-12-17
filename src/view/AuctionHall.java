package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.BorderFactory.createEmptyBorder;

public class AuctionHall extends Window {

    private JPanel cards;
    private JPanel panel1;
    private JPanel root;
    private JButton makeBid;
    private JButton logOut;
    AuctionHall(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);

        initializeGui();

//        click();
        makeBid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Bid bid=new Bid("test",500,500,JFrame.EXIT_ON_CLOSE);
                dispose();
            }
        });
    }



    private void initializeGui(){
        root=new JPanel();
        root.setLayout(new BoxLayout(root,BoxLayout.PAGE_AXIS));
        JPanel top=new JPanel();
        cards=new JPanel();
        makeBid =new JButton("Make a Bid");
        logOut=new JButton("log out");
        top.add(makeBid);
        top.add(logOut);
        root.add(top);
        StyleComponents(top);
        makeBid.setHorizontalAlignment(SwingConstants.LEFT);
        makeBid.setVerticalAlignment(SwingConstants.TOP);
        makeBid.setMaximumSize(new Dimension(20,20));
        //
//        ContentPanel.add(makeBid);
//        cards.add(makeBid);

        Dimension d=new Dimension();
        d.width=-1;
        d.height=1000;
        cards.setPreferredSize(d);
        addCards();

        JScrollPane scrollPane = new JScrollPane(cards);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(createEmptyBorder());
        root.add(scrollPane);
        ContentPanel.add(root);
        StyleComponents(root);
        StyleComponents(cards);
        this.setVisible(true);
    }
    public void addCards(){
        for (int i=0;i<10;i++){
            Card card=new Card(".\\src\\view\\hp.png","headphone",100,this);
            cards.add(card);
        }
    }


//    public void click() {
//         makeBid.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//               Bid bid=new Bid("test",500,500,JFrame.EXIT_ON_CLOSE);
//               dispose();
//             }
//         });
//    }
}
