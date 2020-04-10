package com.KerrYip.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The Panel for viewing all of the course catalog which will display all
 * courses in the catalog
 */
public class BrowseCatalogPanel extends JPanel {

	private int width, height;
	private JButton backButton;
	private JLabel browseCatalogLabel;
	private JTextArea dataText;

	public BrowseCatalogPanel(int width, int height) {
		this.width = width;
		this.height = height;
		// Buttons for the main window
		backButton = new JButton("Back");

		// browse catalog title
		browseCatalogLabel = new JLabel();
		browseCatalogLabel.setText("Course Catalog");

		// browse catalog panels for formatting
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

		// This is the login select label
		browseCatalogLabel = new JLabel();
		browseCatalogLabel.setText("Course Catalog");

		// Set up the layout of the main window
		setLayout(new BorderLayout());

		// Add all the buttons into the panel
		titlePanel.add(browseCatalogLabel);
		buttonPanel.add(backButton);

		// This is the data field that displays
		dataText = new JTextArea(20, 20);
		dataText.setLineWrap(true); // Allows text to wrap if it reaches the end of the line
		dataText.setWrapStyleWord(true); // text should wrap at word boundaries rather than character boundaries
		dataText.setEditable(false); // This ensure that the user cannot edit the data field
		dataText.setText(""); // This displays empty text in the field

		// Make the data field scroll-able if enough data fills the panel
		JScrollPane dataTextScrollPane = new JScrollPane(dataText);
		dataTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// adding panels to appropriate quadrants
		add("North", titlePanel);
		add("South", buttonPanel);
		add("Center", dataTextScrollPane);

	}

	/**
	 * Adds listener for the backButton
	 * 
	 * @param listenForBackStudentMenuButton listener for the button
	 */
	public void addBackStudentMenuListener(ActionListener listenForBackStudentMenuButton) {
		backButton.addActionListener(listenForBackStudentMenuButton);
	}

}
