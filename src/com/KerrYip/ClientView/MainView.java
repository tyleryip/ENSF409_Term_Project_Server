package com.KerrYip.ClientView;

import javax.swing.*;

public class MainView extends JFrame {

    public MainView(int height, int width){
        this.setSize(height,width);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void addPanel(JPanel pane){
        add(pane);
        this.setVisible(true);
    }
}
