package com.KerrYip.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginSelectView extends JFrame {

    private JButton studentButton, adminButton, quitButton;
    private JLabel loginLabel;

    public LoginSelectView(String s, int width, int height) {
        super(s);
        this.setSize(width, height);

        //Buttons for the main window
        studentButton = new JButton("Student Login");
        adminButton = new JButton("Admin Login");
        quitButton = new JButton("Quit Login");

        //This is the login select label
        loginLabel = new JLabel();
        loginLabel.setText("Please selec login type");

        //This is the login select panel
        JPanel loginPanel = new JPanel();

        //Set up the layout of the main window
        setLayout(new FlowLayout());

        //Add all the buttons into the panel
        loginPanel.add(studentButton);
        loginPanel.add(adminButton);
        loginPanel.add(quitButton);

        add(loginPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        studentButton.addActionListener((ActionEvent e) -> {

        });

        adminButton.addActionListener((ActionEvent e) -> {
            System.out.println("admin");
        });

        quitButton.addActionListener((ActionEvent e) -> {
            System.out.println("quit");
        });
    }
}


