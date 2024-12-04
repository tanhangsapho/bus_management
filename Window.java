package Project_Final;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public Window(){

        super("Bus Management System");

        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(600,100);
        setBackground(Color.WHITE);
        setResizable(true);
        setBackground(Color.BLACK);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void openWindow(){
        setVisible(true);
    }
    public void closeWindow(){
        setVisible(false);
    }
}
