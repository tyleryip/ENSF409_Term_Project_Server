package com.KerrYip.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.KerrYip.ClientView.LoginSelectPanel;
import com.KerrYip.ClientView.MainView;
import com.KerrYip.ClientView.StudentMenuPanel;

import javax.swing.*;

public class ClientGUIController {

    // the other controllers on the client side
    private ClientCommunicationController clientController;


    private LoginSelectPanel loginSelect;
    private StudentMenuPanel studentMenu;

    private MainView frame;


    public ClientGUIController(int width, int height){
        frame = new MainView(width, height);
        clientController = new ClientCommunicationController("localhost",9090);

        //set frame to login selection
        loginSelect = new LoginSelectPanel();
        loginSelect.addStudentLoginListener(new StudentLoginListener());
        frame.addPanel(loginSelect);

    }

    class StudentLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            String studentID = JOptionPane.showInputDialog("Please enter the student's id");
            String message = clientController.communicationStudentLogin("student login", studentID);
            System.out.println(message);
            if(message.equals("login successful")){
                studentMenu = new StudentMenuPanel();
            }else{
                JOptionPane.showMessageDialog(null,"Login Unsuccessful: Could not locate ID");

                loginSelect = new LoginSelectPanel();
                loginSelect.addStudentLoginListener(new StudentLoginListener());
                frame.add(loginSelect);
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
