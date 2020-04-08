package com.KerrYip.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.KerrYip.ClientView.LoginSelectView;

import javax.swing.*;

public class ClientGUIController {

    private JFrame mainFrame;
    // the other controllers on the client side
    private ClientCommunicationController clientController;
    private LoginSelectView LoginSelect;


    public ClientGUIController(int width, int height){
        mainFrame = new JFrame();
        mainFrame.setSize(width, height);
        clientController = new ClientCommunicationController("localhost",9090);
        LoginSelect = new LoginSelectView(mainFrame);
        LoginSelect.addStudentListener(new StudentLoginListener());
    }

    class StudentLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            String studentID = JOptionPane.showInputDialog("Please enter the student's id");
            String message = clientController.communicationStudentLogin("student login", studentID);
            if(message.equals("login successful")){

            }else{
                JOptionPane.showMessageDialog(null,"Login Unsuccessful: Could not locate ID");
                LoginSelect = new LoginSelectView(mainFrame);
                LoginSelect.addStudentListener(new StudentLoginListener());
            }
        }
    }

    class QuitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            clientController.communicationQuit();
        }
    }
}
