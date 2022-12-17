package view;

import Model.RegisterModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends  Window{
    private JTextField username;
    private JPanel panel1;
    private JPasswordField password;
    private JTextField email;
    private JButton signUpButton;

    Register(String windowTitle, int width, int height, int defaultCloseOperation) {
        super(windowTitle, width, height, defaultCloseOperation);

        initializeFrame();


        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                RegisterModel.setEmail(email.getText());
//                RegisterModel.setPassword(String.valueOf(password.getPassword()));
//                RegisterModel.setUsername(username.getText());

            }
        });
    }

    void initializeFrame(){
        ContentPanel.add(panel1);
        StyleComponents(panel1);
        this.setVisible(true);

    }

    private String getPassword() {
        return String.valueOf(password.getPassword());
    }

    private String getEmail() {
        return email.getText();
    }
    private String getUsername(){
        return username.getText();
    }

}
