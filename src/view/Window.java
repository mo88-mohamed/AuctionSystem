package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame implements ActionListener, MouseListener {

    public JPanel TitlebarPanel = new MotionPanel(this);
    public JPanel ContentPanel = new JPanel();

    private JButton closeWindow;
    private JButton minimizeWindow;
    private JLabel title;
    private Color dark = new Color(0x2C2F33);
    private Color light = new Color(0x3b3f44);
    private Color veryDark = new Color(0x23272A);
    private int width ,height;
    private String windowTitle;

    Window(String windowTitle, int width, int height, int defaultCloseOperation){
        this.windowTitle=windowTitle;
        this.width=width;
        this.height=height;

        styleWindow();

        setDefaultCloseOperation(defaultCloseOperation);
//        setLocationRelativeTo(null);
        centerWindowOnScreen();




        styleContentPanel();
        createCloseButton();
        createMinimizeButton();
//        createWindowTitle();
        createWindowTopbar();



        add(TitlebarPanel, BorderLayout.NORTH);
        add(ContentPanel, BorderLayout.CENTER);
    }
    private void styleWindow(){
        setSize(width + 10, height + 37);
        setUndecorated(true);//  ?
        setLayout(new BorderLayout());

    }
    private  void createCloseButton(){
        closeWindow = new JButton("X");
        closeWindow.setFocusable(false);
        closeWindow.setBackground(null);
        closeWindow.setForeground(Color.WHITE);
        closeWindow.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        closeWindow.addActionListener(this);
        closeWindow.addMouseListener(this);
    }
    private void createMinimizeButton(){
        minimizeWindow = new JButton("_");
        minimizeWindow.setFocusable(false);
        minimizeWindow.setBackground(null);
        minimizeWindow.setForeground(Color.WHITE);
        minimizeWindow.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        minimizeWindow.addActionListener(this);
        minimizeWindow.addMouseListener(this);
    }
    private  void styleContentPanel(){
        ContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        ContentPanel.setBackground(dark);
        ContentPanel.setLayout(new GridLayout(0, 1, 10, 10));
    }

    private void styleTitle(JLabel title){
        title.setForeground(Color.WHITE);
        title.setBorder(new EmptyBorder(0,10,0,0));
    }

    private JLabel createWindowTitle(){
//        JPanel titlePanel = new JPanel();
         title = new JLabel(windowTitle);
//        title.setForeground(Color.WHITE);
          styleTitle(title);
//        titlePanel.add(title);
//        titlePanel.setBackground(veryDark);
//        title.setAlignmentX(-10000);
//        title.setBorder(new EmptyBorder(0,10,0,0));
      return title;
    }
    private void createWindowTopbar(){

        TitlebarPanel.setBackground(veryDark);
        TitlebarPanel.setLayout(new BorderLayout());
        TitlebarPanel.add(createWindowTitle(), BorderLayout.WEST);
        TitlebarPanel.add(createWindowControlpanel(), BorderLayout.EAST);

    }

    private JPanel createWindowControlpanel(){
        JPanel windowControlsPanel = new JPanel();

        windowControlsPanel.add(minimizeWindow);
        windowControlsPanel.add(closeWindow);
        windowControlsPanel.setBackground(veryDark);

        return windowControlsPanel;
    }


//    private void styleTitlebarPanel(){
//        TitlebarPanel.setBackground(veryDark);
//        TitlebarPanel.setLayout(new BorderLayout());
//
//    }
//    private void styleTitle(JLabel tilt){
//
//    }


    public void centerWindowOnScreen(){
        setLocationRelativeTo(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == closeWindow){
            dispose();
            System.out.println("System Exit!");
            System.exit(0);
        }
        else if(e.getSource() == minimizeWindow){
            this.setState(Frame.ICONIFIED);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == closeWindow){
            closeWindow.setBackground(Color.RED);
        }
        else if (e.getSource() == minimizeWindow){
            ((JButton) e.getSource()).setBackground(dark);
        }
        else if (e.getSource() instanceof JButton){
            ((JButton) e.getSource()).setBackground(veryDark);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == closeWindow || e.getSource() == minimizeWindow){
            ((JButton) e.getSource()).setBackground(null);
        }
        else if (e.getSource() instanceof JButton){
            ((JButton) e.getSource()).setBackground(light);
        }
    }

    public void StyleComponents(JPanel parent){
        for (Component comp : parent.getComponents()) {
            parent.setBackground(dark);
            if (comp instanceof  JPanel){
                comp.setBackground(dark);
            }
            else if (comp instanceof JButton) {
                ((JButton)comp).setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                ((JButton)comp).setFocusable(false);
                ((JButton)comp).setBackground(light);
                ((JButton)comp).setForeground(Color.WHITE);
                ((JButton)comp).addActionListener(this);
                ((JButton)comp).addMouseListener(this);
            }
            else if (comp instanceof JLabel){
                comp.setForeground(Color.WHITE);
            }
            else if (comp instanceof JRadioButton){
                comp.setBackground(null);
                comp.setForeground(Color.WHITE);
            }
            else if (comp instanceof JTextField){
                comp.setBackground(light);
                comp.setForeground(Color.WHITE);
                ((JTextField) comp).setCaretColor(Color.WHITE);
            }
            else if (comp instanceof JComboBox){
                comp.setBackground(light);
                ((JComboBox) comp).setForeground(Color.WHITE);
            }
            else if (comp instanceof JScrollPane){
                comp.getParent().setBackground(dark);
            }

            setVisible(true);
        }
    }
}