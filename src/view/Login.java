package view;

import Model.LoginModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends Window{
    private JPanel pn;
    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private JLabel register;

    Login(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);

        initializeGui();




        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                LoginModel.setUsername( username.getText());
//                LoginModel.setPassword(String.valueOf(password.getPassword()));


                if(true) { //Login success
                    AuctionHall auctionHall = new AuctionHall("Auction System", 500, 500, JFrame.EXIT_ON_CLOSE);


                    dispose();

                }
                else{
                    loginError();
                }

            }
        });
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Register register =new Register("Auction System",310, 150, JFrame.EXIT_ON_CLOSE);
                dispose();
            }
        });
    }



    private void initializeGui(){
        ContentPanel.add(pn);
        StyleComponents(pn);
        register.setForeground(Color.blue);
        this.setVisible(true);
    }

    private String getUsername(){
        return username.getText();
    }

    private String getPassword(){
        return String.valueOf(password.getPassword());
    }

    private void loginError(){

        JOptionPane.showMessageDialog(null,"username or password wrong");

    }



}
