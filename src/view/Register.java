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
    private JButton signUpBtn;

    Register(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);

        initializeGui();

        signUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // createNewUser
                if (!checkInputsEmpty()) {
                    User newUser = createUser(getEmail(), getFullname(), getPassword());
                    if (registerNewUser(newUser)) {
                        dispose();
//                        Route.loginWindow();
                    } else { // Error occurred during account creation
                        if(checkUserExists()){
                            usedEmailError();
                        } else{
                            generalError();
                        }
                    }
                } else{
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
        return (getEmail().isBlank() || getFullname().isBlank() || getPassword().isBlank());
    }

    private User createUser(String email, String fullname, String password){
        return new User(email, fullname, password);
    }

    private boolean registerNewUser(User user){
        Message message = new Message("createNewUser", user);
        try {
            message = (Message) ServerConnection.getInstance().sendMessage(message);
        } catch (Exception ignored) {
        }
        if (message.getFunctionName().equals("createNewUser")) {
            return (boolean) message.getObject();
        }

        return false;
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

    private boolean checkUserExists(){
        Message message = new Message("isUserExist",getEmail());

        try{
            message = (Message) ServerConnection.getInstance().sendMessage(message);
        }catch (Exception ignored){
        }

        return (boolean) message.getObject();
    }

    private void emptyFieldsError(){
        JOptionPane.showMessageDialog(null,"fill all fields ");
    }

    private void usedEmailError(){
        JOptionPane.showMessageDialog(null,"Error creating account, Email is already used");
    }

    private void generalError(){
        JOptionPane.showMessageDialog(null,"Error creating account");
    }
}
