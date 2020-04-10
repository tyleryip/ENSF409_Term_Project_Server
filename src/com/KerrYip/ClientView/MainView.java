package com.KerrYip.ClientView;

import java.awt.CardLayout;
import javax.swing.*;

/**
 * The Frame of the GUI which has all the panels and is able to switch between
 * them on the frame
 */
public class MainView extends JFrame {

	// dimensions of the frame
	private final int width, height;

	// panels of the frame
	private LoginSelectPanel loginSelect;
	private StudentMenuPanel studentMenu;
	private AdminMenuPanel adminMenu;
	private BrowseCatalogPanel browseCatalog;

	// card layout which allows for switching between the panels above
	private JPanel cardList;
	private CardLayout cardControl;

	/**
	 * Constructor for the MainView which generates the frame that the GUI will be
	 * on
	 * 
	 * @param height height of the frame
	 * @param width  width of the frame
	 */
	public MainView(int height, int width) {
		// sets dimensions
		this.height = height;
		this.width = width;
		this.setSize(height, width);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// creates card layout to traverse all the panels
		cardList = new JPanel();
		cardList.setLayout(new CardLayout());
		cardControl = (CardLayout) (cardList.getLayout());
	}

	/**
	 * Shows the specified panel on the frame
	 * 
	 * @param label
	 */
	public void show(String label) {
		getCardControl().show(getCardList(), label);
		setTitle(label);
	}

	/**
	 * Adds panel to the cardList
	 * 
	 * @param pane  panel to be added
	 * @param label name the panel will be referred to as
	 */
	public void addCard(JPanel pane, String label) {
		getCardList().add(pane, label);
	}

	/**
	 * Starts the GUI by making the frame visible and starting on the login
	 * selection
	 */
	public void start() {
		add(cardList);
		show("Login Select");
		setVisible(true);
	}

	public JPanel getCardList() {
		return cardList;
	}

	public CardLayout getCardControl() {
		return cardControl;
	}

	public LoginSelectPanel getLoginSelect() {
		return loginSelect;
	}

	public StudentMenuPanel getStudentMenu() {
		return studentMenu;
	}

	public void setLoginSelect(LoginSelectPanel loginSelect) {
		this.loginSelect = loginSelect;
	}

	public void setStudentMenu(StudentMenuPanel studentMenu) {
		this.studentMenu = studentMenu;
	}

	public AdminMenuPanel getAdminMenu() {
		return adminMenu;
	}

	public void setAdminMenu(AdminMenuPanel adminMenu) {
		this.adminMenu = adminMenu;
	}

	public BrowseCatalogPanel getBrowseCatalog() {
		return browseCatalog;
	}

	public void setBrowseCatalog(BrowseCatalogPanel browseCatalog) {
		this.browseCatalog = browseCatalog;
	}

}
