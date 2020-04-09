package com.KerrYip.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenuPanel extends JPanel {
    private JButton searchCatalogButton, addCourseButton, removeCourseButton, browseCatalogButton, viewStudentCoursesButton, runButton, logoutButton;
    private JLabel adminMenuLabel;
    private JTextArea dataText;

    public AdminMenuPanel() {

        //Buttons for the main window
        searchCatalogButton = new JButton("Search for Course in Catalog");
        addCourseButton = new JButton("Add new course to the Catalog");
        removeCourseButton = new JButton("Remove course from the Catalog");
        browseCatalogButton = new JButton("View all Courses in Catalog");
        viewStudentCoursesButton = new JButton("View Enrolled Courses from a student");
        runButton = new JButton("Run Courses");
        logoutButton = new JButton("Logout");

        //This is the login select label
        adminMenuLabel = new JLabel();
        adminMenuLabel.setText("Admin Menu");

        //This is the login select panel
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        //Set up the layout of the main window
        setLayout(new BorderLayout());

        //Add all the buttons into the panel
        titlePanel.add(adminMenuLabel);
        buttonPanel.add(addCourseButton);
        buttonPanel.add(removeCourseButton);
        buttonPanel.add(browseCatalogButton);
        buttonPanel.add(searchCatalogButton);
        buttonPanel.add(viewStudentCoursesButton);
        buttonPanel.add(runButton);

        //This is the data field that displays
        dataText = new JTextArea(20, 20);
        dataText.setLineWrap(true); // Allows text to wrap if it reaches the end of the line
        dataText.setWrapStyleWord(true); // text should wrap at word boundaries rather than character boundaries
        dataText.setEditable(false);  // This ensure that the user cannot edit the data field
        dataText.setText(""); // This displays empty text in the field

        //Make the data field scroll-able if enough data fills the panel
        JScrollPane dataTextScrollPane = new JScrollPane(dataText);
        dataTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add("North", titlePanel);
        add("East", buttonPanel);
        add("Center", dataTextScrollPane);
        add("South", logoutButton);

    }

    public void addAddCourseToCatalogListener (ActionListener listenAddCourseToCatalogButton){
        addCourseButton.addActionListener(listenAddCourseToCatalogButton);
    }

    public void addRemoveCourseFromCatalogListener (ActionListener listenForRemoveCourseFromCatalogButton){
        removeCourseButton.addActionListener(listenForRemoveCourseFromCatalogButton);
    }

    public void addBrowseCatalogListener (ActionListener listenForSearchCatalogButton){
        browseCatalogButton.addActionListener(listenForSearchCatalogButton);
    }

    public void addSearchCatalogListener (ActionListener listenForSearchCatalogButton){
        searchCatalogButton.addActionListener(listenForSearchCatalogButton);
    }

    public void addViewStudentEnrolledCoursesListener (ActionListener listenForStudentEnrolledCoursesButton){
        viewStudentCoursesButton.addActionListener(listenForStudentEnrolledCoursesButton);
    }

    public void addRunCoursesListener (ActionListener listenForRunCoursesButton){
        runButton.addActionListener(listenForRunCoursesButton);
    }

    public void addLogoutListener (ActionListener listenForSearchCatalogButton){
        logoutButton.addActionListener(listenForSearchCatalogButton);
    }
}
