package com.KerrYip.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientMenuView {

    private JButton studentButton, adminButton, quitButton;
    private JLabel loginLabel;

    public ClientMenuView(JFrame frame){
        frame.removeAll();

        //Buttons for the main window
        studentButton = new JButton("Student Login");
        adminButton = new JButton("Admin Login");
        quitButton = new JButton("Quit Login");

        //This is the login select label
        loginLabel = new JLabel();
        loginLabel.setText("Please select login type");

        //This is the login select panel
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        //Add all the buttons into the panel
        titlePanel.add(loginLabel);
        buttonPanel.add(studentButton);
        buttonPanel.add(adminButton);
        buttonPanel.add(quitButton);

        //Set up the layout of the main window
        frame.setLayout(new BorderLayout());

        frame.add(titlePanel,"north");
        frame.add(buttonPanel, "center");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);


        adminButton.addActionListener((ActionEvent e) -> {
            System.out.println("admin");
        });

        quitButton.addActionListener((ActionEvent e) -> {
            System.out.println("quit");
        });
    }

    public void addStudentListener (ActionListener listenForStudentButton){
        studentButton.addActionListener(listenForStudentButton);
    }

    public void addQuitListener (ActionListener listenForQuitButton){
        quitButton.addActionListener(listenForQuitButton);
    }
}
