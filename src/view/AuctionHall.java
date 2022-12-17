package view;

import ClientSide.Main;
import ClientSide.ServerConnection;
import Models.Bid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
//import Models.Bid;

import static ClientSide.Main.isListUpdated;
import static ServerSide.ServerHandler.bidsList;
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
                BidView bidView =new BidView("test",500,500,JFrame.EXIT_ON_CLOSE);
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

        do {
            addCards();
            isListUpdated = false;
        }
        while(isListUpdated);

        JScrollPane scrollPane = new JScrollPane(cards);

        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(createEmptyBorder());
        root.add(scrollPane);
        ContentPanel.add(root);
        StyleComponents(root);
        StyleComponents(cards);
        this.setVisible(true);
    }
    public void addCards() {
        // getAllBids
        // call this function in the main to init the static bidsList from the database
        System.out.println("add cards");
        cards.removeAll();
        cards.updateUI();
//        try {
//            Main.bidsList = ServerConnection.getInstance().getBidsList();
//        } catch (Exception ignored) {
//            System.out.println("error");
//        }
//        System.out.println("bidslist: " + Main.bidsList);
        if (Main.bidsList != null) {
            for (Bid bid1 : Main.bidsList) {
                Card card = new Card(bid1, this);
                cards.add(card);
            }
        }
//        isListUpdated = false;
//        for (int i=0;i<10;i++){
//            Card card=new Card(".\\src\\view\\hp.png","headphone",100,this);
//            cards.add(card);
//        }
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
