package com.KerrYip.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The Panel for the Admin Menu which allows the admin to select what operations they would like to do for the courses
 */
public class AdminMenuPanel extends JPanel {
    private int width, height;
    private JButton searchCatalogButton, addCourseButton, removeCourseButton, browseCatalogButton, viewStudentCoursesButton, runButton, logoutButton;
    private JLabel adminMenuLabel;
    private JTextArea dataText;

    /**
     * Constructs the Admin Menu Panel
     * @param width width of the Frame the panel will be in
     * @param height width of the Frame the panel will be in
     */
    public AdminMenuPanel(int width, int height) {
        this.width = width;
        this.height = height;

        //Buttons for the main window
        searchCatalogButton = new JButton("Search for Course in Catalog");
        addCourseButton = new JButton("Add new course to the Catalog");
        removeCourseButton = new JButton("Remove course from the Catalog");
        browseCatalogButton = new JButton("View all Courses in Catalog");
        viewStudentCoursesButton = new JButton("View Enrolled Courses from a student");
        runButton = new JButton("Run Courses");
        logoutButton = new JButton("Logout");

        //admin menu title
        adminMenuLabel = new JLabel();
        adminMenuLabel.setText("Admin Menu");

        //admin menu panels for formatting
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

        //the data field that displays
        dataText = new JTextArea((int)(width*0.75), (int)(height*0.75));
        dataText.setLineWrap(true); // Allows text to wrap if it reaches the end of the line
        dataText.setWrapStyleWord(true); // text should wrap at word boundaries rather than character boundaries
        dataText.setEditable(false);  // This ensure that the user cannot edit the data field
        dataText.setText(""); // This displays empty text in the field

        //Make the data field scroll-able if enough data fills the panel
        JScrollPane dataTextScrollPane = new JScrollPane(dataText);
        dataTextScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        //adding panels to appropriate quadrants
        add("North", titlePanel);
        add("East", buttonPanel);
        add("Center", dataTextScrollPane);
        add("South", logoutButton);

    }

    /**
     * Adds Listeners to the addCourseButton
     * @param listenForAddCourseToCatalogButton the listener for the button
     */
    public void addAddCourseToCatalogListener (ActionListener listenForAddCourseToCatalogButton){
        addCourseButton.addActionListener(listenForAddCourseToCatalogButton);
    }

    /**
     * Adds Listeners to the removeCourseButton
     * @param listenForRemoveCourseFromCatalogButton the listener for the button
     */
    public void addRemoveCourseFromCatalogListener (ActionListener listenForRemoveCourseFromCatalogButton){
        removeCourseButton.addActionListener(listenForRemoveCourseFromCatalogButton);
    }

    /**
     * Adds Listeners to the browseCatalogButton
     * @param listenForSearchCatalogButton the listener for the button
     */
    public void addBrowseCatalogListener (ActionListener listenForSearchCatalogButton){
        browseCatalogButton.addActionListener(listenForSearchCatalogButton);
    }

    /**
     * Adds listeners to the searchCatalogButton
     * @param listenForSearchCatalogButton the listener for the button
     */
    public void addSearchCatalogListener (ActionListener listenForSearchCatalogButton){
        searchCatalogButton.addActionListener(listenForSearchCatalogButton);
    }

    /**
     * Adds listeners to the viewStudentCoursesButton
     * @param listenForStudentEnrolledCoursesButton the listener for the button
     */
    public void addViewStudentEnrolledCoursesListener (ActionListener listenForStudentEnrolledCoursesButton){
        viewStudentCoursesButton.addActionListener(listenForStudentEnrolledCoursesButton);
    }

    /**
     * Adds listeners to the runButton
     * @param listenForRunCoursesButton the listener for the button
     */
    public void addRunCoursesListener (ActionListener listenForRunCoursesButton){
        runButton.addActionListener(listenForRunCoursesButton);
    }

    /**
     * Adds listeners to the logoutButton
     * @param listenForLogoutButton the listener for the button
     */
    public void addLogoutListener (ActionListener listenForLogoutButton){
        logoutButton.addActionListener(listenForLogoutButton);
    }
}
