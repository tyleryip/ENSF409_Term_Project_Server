package com.KerrYip.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.KerrYip.ClientView.LoginSelectPanel;
import com.KerrYip.ClientView.MainView;

public class ClientGUIController {


    // the other controllers on the client side
    private ClientCommunicationController clientController;
    private MainView frame;


    public ClientGUIController(int width, int height){
        frame = new MainView(width, height);
        clientController = new ClientCommunicationController("localhost",8989);

        //set frame to login selection
        loginSelect = new LoginSelectPanel();
        loginSelect.addStudentListener(new StudentLoginListener());
        frame.addPanel(loginSelect);

    }

    class StudentLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            String studentID = JOptionPane.showInputDialog("Please enter the student's id");
            String message = clientController.communicationStudentLogin("student login", studentID);
            System.out.println(message);
            if(message.equals("login successful")){

            }else{
                JOptionPane.showMessageDialog(null,"Login Unsuccessful: Could not locate ID");

                loginSelect = new LoginSelectPane();
                loginSelect.addStudentListener(new StudentLoginListener());
                frame.add(loginSelect);
            }
        }
        clientController = new ClientCommunicationController("localhost",9090);
        frame = new MainView(width, height, clientController);
    }


}