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

    int width, height;

    AuctionHall(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);

        Instance = this;

        this.width = width;
        this.height = height;

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

        cards.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));

        resetCardsPreferredSize();

        JScrollPane scrollPane = new JScrollPane(cards);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(createEmptyBorder());

        root.add(scrollPane);
        ContentPanel.add(root);
        StyleComponents(root);
        StyleComponents(cards);
        this.setVisible(true);
    }

    private void resetCardsPreferredSize(){
        int columnHeight = 160;
        if(Main.bidsList.size() > 9){
            int numRows = (int)Math.ceil(Main.bidsList.size()/3.0);
            cards.setPreferredSize(new Dimension(width - 27, columnHeight * numRows - (numRows * 4)));
        } else{
            cards.setPreferredSize(new Dimension(width - 27, height - 37));
        }
    }

    public void redrawCards() {
        clearCards();
        addCards();
        resetCardsPreferredSize();
    }

    private void clearCards(){
        cards.removeAll();
        cards.updateUI();
    }

    private void addCards(){
        if (Main.bidsList != null) {
            for (Bid bid1 : Main.bidsList) {
                Card card = new Card(bid1,this);
                cards.add(card);
                System.out.println(bid1.getId());
            }
            cards.updateUI();
            cards.setBackground( new Color(0x2C2F33));
        }
    }
}
