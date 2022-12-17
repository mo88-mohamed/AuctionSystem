package view;

import ClientSide.ServerConnection;
import Models.Message;
import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends  Window{
    private JTextField fullname;
    private JPanel panel1;
    private JPasswordField password;
    private JTextField email;
    private JButton signUpButton;

    Register(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);

        initializeGui();


        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // createNewUser
                if (!checkInputsEmpty()) {


                    User user1 = new User(getEmail(), getFullname(), getPassword());
                    Message message2 = new Message("createNewUser", user1);
                    try {
                        message2 = (Message) ServerConnection.getInstance().sendMessage(message2);
                    } catch (Exception ignored) {
                    }
                    if (message2.getFunctionName().equals("createNewUser")) {
                        boolean isRegistered = (boolean) message2.getObject();
                        System.out.println("Client: " + isRegistered);

                        if (isRegistered) {
                            dispose();
//                        Login login = new Login("Auction System",310, 150, JFrame.DISPOSE_ON_CLOSE);
                        } else {
                            registerError();
                        }
                    }

                }
                else{
                    emptyFieldsError();
                }
            }

        });

    }

    void initializeGui(){
        ContentPanel.add(panel1);
        StyleComponents(panel1);
        this.setVisible(true);

    }
    private boolean checkInputsEmpty(){
        return (getEmail().isBlank()||getFullname().isBlank()||getPassword().isBlank());


    }

    private String getPassword() {
        return String.valueOf(password.getPassword());
    }

    private String getEmail() {
        return email.getText();
    }
    private String getFullname(){
        return fullname.getText();
    }

    private void registerError(){

        // check isUserExist

        Message message = new Message("isUserExist",getEmail());

        try{
            message = (Message) ServerConnection.getInstance().sendMessage(message);
        }catch (Exception ignored){
        }

        boolean result = (boolean) message.getObject();

        if (result){
            JOptionPane.showMessageDialog(null,"Error creating account, Email is already used");
        } else{ // email is not used, but an error still occurred
            JOptionPane.showMessageDialog(null,"Error creating account");
        }


    }
    private void emptyFieldsError(){
        JOptionPane.showMessageDialog(null,"fill all fields ");

    }

}
