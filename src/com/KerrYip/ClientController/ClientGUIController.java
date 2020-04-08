package com.KerrYip.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.KerrYip.ClientView.LoginSelectView;

public class ClientGUIController {

    // the other controllers on the client side
    private ClientCommunicationController clientController;
    private LoginSelectView theView;

    public ClientGUIController(){
        //clientController = new ClientCommunicationController("localhost",9090);
        theView = new LoginSelectView("Main Window", 1000, 600);
    }

    class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){

        }
    }
}
