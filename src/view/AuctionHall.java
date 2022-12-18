package view;

import ClientSide.Main;
import Models.Bid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.BorderFactory.createEmptyBorder;

public class AuctionHall extends Window {
    public static AuctionHall Instance;

    private JPanel cards = new JPanel();
    private JPanel panel1;
    private JPanel root = new JPanel();
    private JButton addProductBtn;
    private JButton logOutBtn;

    AuctionHall(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);

        Instance = this;

        addCards();
        initializeGui();

        addProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Route.addProductWindow();
                dispose();
            }
        });
        logOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Route.loginWindow();
                dispose();
            }
        });
    }

    private void initializeGui(){
        JPanel top=new JPanel();

        root.setLayout(new BoxLayout(root,BoxLayout.PAGE_AXIS));

        addProductBtn =new JButton("Make a Bid");
        logOutBtn =new JButton("log out");

        top.add(addProductBtn);
        top.add(logOutBtn);
        root.add(top);

        StyleComponents(top);

        addProductBtn.setHorizontalAlignment(SwingConstants.LEFT);
        addProductBtn.setVerticalAlignment(SwingConstants.TOP);
        addProductBtn.setMaximumSize(new Dimension(20,20));

        cards.setLayout(new GridLayout(0, 3, 10, 10));

        JScrollPane scrollPane = new JScrollPane(cards);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(createEmptyBorder());

        root.add(scrollPane);
        ContentPanel.add(root);
        StyleComponents(root);
        StyleComponents(cards);
        this.setVisible(true);
    }



    public void redrawCards() {
        clearCards();
        addCards();
    }

    private void clearCards(){
        cards.removeAll();
        cards.updateUI();
    }

    private void addCards(){
        if (Main.bidsList != null) {
            for (Bid bid1 : Main.bidsList) {
                Card card = new Card(bid1, this);
                cards.add(card);
                System.out.println(bid1.getId());
            }
        }
    }
}
