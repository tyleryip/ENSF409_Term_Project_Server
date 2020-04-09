package com.KerrYip.ClientController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.KerrYip.ClientView.LoginSelectPanel;
import com.KerrYip.ClientView.MainView;
import com.KerrYip.ClientView.StudentMenuPanel;

import javax.swing.*;

public class ClientGUIController {


    // the other controllers on the client side
    private ClientCommunicationController clientController;
    private MainView frame;


    public ClientGUIController(int width, int height){
        clientController = new ClientCommunicationController("localhost",9090);
        frame = new MainView(width, height, clientController);
    }


}