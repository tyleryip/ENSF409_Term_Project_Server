package com.KerrYip.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSelectPane extends JPanel{

    private JButton studentButton, adminButton, quitButton;
    private JLabel loginLabel;

    public LoginSelectPane() {

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

        //Set up the layout of the main window
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        //Add all the buttons into the panel
        titlePanel.add(loginLabel);
        buttonPanel.add(studentButton);
        buttonPanel.add(adminButton);
        buttonPanel.add(quitButton);

        add(titlePanel);
        add(buttonPanel);

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
}



