package com.KerrYip.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentMenuPanel extends JPanel {
	private JButton searchCatalogButton, enrollCourseButton, dropCourseButton, browseCatalogButton,
			viewEnrolledCoursesButton, logoutButton;
	private JLabel studentMenuLabel;
	private JTextArea dataText;

	public StudentMenuPanel() {

		// Buttons for the main window
		searchCatalogButton = new JButton("Search for Course in Catalog");
		enrollCourseButton = new JButton("Enroll in a Course");
		dropCourseButton = new JButton("Drop a Course");
		browseCatalogButton = new JButton("View all Courses in Catalog");
		viewEnrolledCoursesButton = new JButton("View Enrolled Courses");
		logoutButton = new JButton("Logout");

		// This is the login select label
		studentMenuLabel = new JLabel();
		studentMenuLabel.setText("Student Menu");

		// This is the login select panel
		JPanel titlePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel coursePanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

		// Set up the layout of the main window
		setLayout(new BorderLayout());

		// Add all the buttons into the panel
		titlePanel.add(studentMenuLabel);
		buttonPanel.add(enrollCourseButton);
		buttonPanel.add(dropCourseButton);
		buttonPanel.add(browseCatalogButton);
		buttonPanel.add(searchCatalogButton);
		buttonPanel.add(viewEnrolledCoursesButton);

		// This is the data field that displays
		dataText = new JTextArea(20, 20);
		dataText.setLineWrap(true); // Allows text to wrap if it reaches the end of the line
		dataText.setWrapStyleWord(true); // text should wrap at word boundaries rather than character boundaries
		dataText.setEditable(false); // This ensure that the user cannot edit the data field
		dataText.setText(""); // This displays empty text in the field

		// Make the data field scroll-able if enough data fills the panel
		JScrollPane dataTextScrollPane = new JScrollPane(dataText);
		dataTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		add("North", titlePanel);
		add("East", buttonPanel);
		add("Center", dataTextScrollPane);
		add("South", logoutButton);

	}

	public void addEnrollCourseListener(ActionListener listenForSearchCatalogButton) {
		enrollCourseButton.addActionListener(listenForSearchCatalogButton);
	}

	public void addDropCourseListener(ActionListener listenForSearchCatalogButton) {
		dropCourseButton.addActionListener(listenForSearchCatalogButton);
	}

	public void addBrowseCatalogListener(ActionListener listenForSearchCatalogButton) {
		browseCatalogButton.addActionListener(listenForSearchCatalogButton);
	}

	public void addSearchCatalogListener(ActionListener listenForSearchCatalogButton) {
		searchCatalogButton.addActionListener(listenForSearchCatalogButton);
	}

	public void addViewEnrolledCoursesListener(ActionListener listenForSearchCatalogButton) {
		viewEnrolledCoursesButton.addActionListener(listenForSearchCatalogButton);
	}

	public void addLogoutListener(ActionListener listenForSearchCatalogButton) {
		logoutButton.addActionListener(listenForSearchCatalogButton);
	}
}
