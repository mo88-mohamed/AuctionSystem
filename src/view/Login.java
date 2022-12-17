package view;

import ClientSide.ServerConnection;
import Models.Message;
import Models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends Window{
    public static String CurrentUserEmail = "";

    private JPanel pn;
    private JTextField email;
    private JPasswordField password;
    private JButton login;
    private JLabel register;

    public Login(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);

        initializeGui();




        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                LoginModel.setUsername( username.getText());
//                LoginModel.setPassword(String.valueOf(password.getPassword()));

            // login

            User user = new User(getEmail(), getPassword());
            Message message1 = new Message("login", user);
            try{
                message1 = (Message) ServerConnection.getInstance().sendMessage(message1);
            }catch (Exception ignored){

            }
            boolean result1 = false;
            if (message1.getFunctionName().equals("login")){
                result1 = (boolean) message1.getObject();
                System.out.println("Client: " + result1);
            }

            if(result1) { //Login success
                CurrentUserEmail = getEmail();
                Route.auctionHall();
//                AuctionHall auctionHall = new AuctionHall("Auction System", 500, 500, JFrame.EXIT_ON_CLOSE);


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
//                Register register =new Register("Auction System",310, 150, JFrame.EXIT_ON_CLOSE);
                Route.register();
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

    private String getEmail(){
        return email.getText();
    }

    private String getPassword(){
        return String.valueOf(password.getPassword());
    }

    private void loginError(){

        JOptionPane.showMessageDialog(null,"username or password wrong");

    }



}
