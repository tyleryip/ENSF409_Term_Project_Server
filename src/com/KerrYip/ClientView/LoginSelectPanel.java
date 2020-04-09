package com.KerrYip.ClientView;

import javax.swing.*;
import java.awt.event.ActionListener;

<<<<<<< HEAD
/**
 * The Login Selection Panel which allows the user to selection which type of login they would like to do
 */
public class LoginSelectPanel extends JPanel{
    private int width, height;
    private JButton studentButton, adminButton, quitButton;
    private JLabel loginLabel;

    /**
     * Constructs the Login Selection Panel
     * @param width width of the Frame the panel will be in
     * @param height width of the Frame the panel will be in
     */
    public LoginSelectPanel(int width, int height) {
        this.width = width;
        this.height = height;
=======
public class LoginSelectPanel extends JPanel {

	private JButton studentButton, adminButton, quitButton;
	private JLabel loginLabel;

	public LoginSelectPanel() {
>>>>>>> 23a7f7e4c411c070f0a8d11f4666f6ddebd1a46a

		// Buttons for the main window
		studentButton = new JButton("Student Login");
		adminButton = new JButton("Admin Login");
		quitButton = new JButton("Quit Login");

<<<<<<< HEAD
        //login select title
        loginLabel = new JLabel();
        loginLabel.setText("Please select login type");

        //login select panels for formatting
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        //Add all the buttons into the panel
        titlePanel.add(loginLabel);
        buttonPanel.add(studentButton);
        buttonPanel.add(adminButton);
        buttonPanel.add(quitButton);

        //layout of the main window and adding the panels
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(titlePanel);
        add(buttonPanel);
=======
		// This is the login select label
		loginLabel = new JLabel();
		loginLabel.setText("Please select login type");

		// This is the login select panel
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		// Set up the layout of the main window
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// Add all the buttons into the panel
		titlePanel.add(loginLabel);
		buttonPanel.add(studentButton);
		buttonPanel.add(adminButton);
		buttonPanel.add(quitButton);

		add(titlePanel);
		add(buttonPanel);
>>>>>>> 23a7f7e4c411c070f0a8d11f4666f6ddebd1a46a

	}

<<<<<<< HEAD
    /**
     * Adds listener for the studentButton
     * @param listenForStudentButton listener for the button
     */
    public void addStudentLoginListener (ActionListener listenForStudentButton){
        studentButton.addActionListener(listenForStudentButton);
    }

    /**
     * Adds listener for the adminButton
     * @param listenForAdminButton listener for the button
     */
    public void addAdminLoginListener (ActionListener listenForAdminButton){
        adminButton.addActionListener(listenForAdminButton);
    }

    /**
     * Adds listener for the quitButton
     * @param listenForQuitButton listener for the button
     */
    public void addQuitLoginListener (ActionListener listenForQuitButton){
        quitButton.addActionListener(listenForQuitButton);
    }
=======
	public void addStudentLoginListener(ActionListener listenForStudentButton) {
		studentButton.addActionListener(listenForStudentButton);
	}

	public void addAdminLoginListener(ActionListener listenForAdminButton) {
		adminButton.addActionListener(listenForAdminButton);
	}

	public void addQuitLoginListener(ActionListener listenForQuitButton) {
		quitButton.addActionListener(listenForQuitButton);
	}
>>>>>>> 23a7f7e4c411c070f0a8d11f4666f6ddebd1a46a
}
