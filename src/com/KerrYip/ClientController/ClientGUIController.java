package com.KerrYip.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.KerrYip.ClientView.LoginSelectPanel;
import com.KerrYip.ClientView.MainView;

public class ClientGUIController {


    // the other controllers on the client side
    private NewClientCommunicationController clientController;
    private MainView frame;


    public ClientGUIController(int width, int height){
        //clientController = new ClientCommunicationController("localhost",9898);
        clientController = new NewClientCommunicationController("localhost", 9898);
        frame = new MainView(width, height, clientController);

    }



}