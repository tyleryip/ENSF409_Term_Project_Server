package com.KerrYip.ClientView;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainView extends JFrame {

    private LoginSelectPanel loginSelect;
    private StudentMenuPanel studentMenu;
    private AdminMenuPanel adminMenu;
    private BrowseCatalogPanel browseCatalog;

    private JPanel cardList;
    private CardLayout cardControl;

    public MainView(int height, int width){
        this.setSize(height,width);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardList = new JPanel();
        cardList.setLayout(new CardLayout());
        cardControl = (CardLayout)(cardList.getLayout());
    }

    public void show(String label){
        getCardControl().show(getCardList(),label);
    }

    public void addCard(JPanel pane, String label){
        getCardList().add(pane,label);
    }

    public void start(){
        add(cardList);
        show( "Login Select");
        setVisible(true);
    }

    public JPanel getCardList() { return cardList; }
    public CardLayout getCardControl() { return cardControl; }
    public LoginSelectPanel getLoginSelect() { return loginSelect; }
    public StudentMenuPanel getStudentMenu() { return studentMenu; }
    public void setLoginSelect(LoginSelectPanel loginSelect) { this.loginSelect = loginSelect; }
    public void setStudentMenu(StudentMenuPanel studentMenu) { this.studentMenu = studentMenu; }
    public AdminMenuPanel getAdminMenu() { return adminMenu; }
    public void setAdminMenu(AdminMenuPanel adminMenu) { this.adminMenu = adminMenu; }
    public BrowseCatalogPanel getBrowseCatalog() { return browseCatalog; }
    public void setBrowseCatalog(BrowseCatalogPanel browseCatalog) { this.browseCatalog = browseCatalog; }
}
