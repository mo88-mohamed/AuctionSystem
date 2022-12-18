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

    private JPanel root;
    private JTextField email;
    private JPasswordField password;
    private JButton loginBtn;
    private JLabel register;

    public Login(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);
        initializeGui();

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // login
                User user = CreateUser(getEmail(), getPassword());

                if(login(user)) { //Login success
                    CurrentUserEmail = getEmail();
                    Route.auctionHallWindow();
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
                Route.registerWindow();
//                dispose();
            }
        });
    }



    private void initializeGui(){
        ContentPanel.add(root);
        StyleComponents(root);
        register.setForeground(Color.blue);
        this.setVisible(true);
    }

    private User CreateUser(String email, String password){
        return new User(getEmail(), getPassword());
    }

    private boolean login(User user){
        boolean isLogin = false;

        Message message = new Message("login", user);

        try{
            message = (Message) ServerConnection.getInstance().sendMessage(message);
        }catch (Exception ignored){
        }

        if (message.getFunctionName().equals("login")){
            isLogin = (boolean) message.getObject(); // Get login status
        }

        return isLogin;
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
