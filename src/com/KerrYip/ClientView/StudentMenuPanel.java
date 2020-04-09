package com.KerrYip.ClientView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The Panel for the Student Menu which allows the student to select what operations they would like to do for their courses
 */
public class StudentMenuPanel extends JPanel {
    private int width, height;
    private JButton searchCatalogButton, enrollCourseButton, dropCourseButton, browseCatalogButton, viewEnrolledCoursesButton, logoutButton;
    private JLabel studentMenuLabel;
    private JTextArea dataText;

    /**
     * Constructs the Student Menu Panel
     *
     * @param width  width of the Frame the panel will be in
     * @param height width of the Frame the panel will be in
     */
    public StudentMenuPanel(int width, int height) {
        this.width = width;
        this.height = height;

        //Buttons for the main window
        searchCatalogButton = new JButton("Search for Course in Catalog");
        enrollCourseButton = new JButton("Enroll in a Course");
        dropCourseButton = new JButton("Drop a Course");
        browseCatalogButton = new JButton("View all Courses in Catalog");
        viewEnrolledCoursesButton = new JButton("View Enrolled Courses");
        logoutButton = new JButton("Logout");

        //student menu title
        studentMenuLabel = new JLabel();
        studentMenuLabel.setText("Student Menu");

        //student menu panels for formatting
        JPanel titlePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel coursePanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));

        //Set up the layout of the main window
        setLayout(new BorderLayout());

        //Add all the buttons into the panel
        titlePanel.add(studentMenuLabel);
        buttonPanel.add(enrollCourseButton);
        buttonPanel.add(dropCourseButton);
        buttonPanel.add(browseCatalogButton);
        buttonPanel.add(searchCatalogButton);
        buttonPanel.add(viewEnrolledCoursesButton);

        //the data field that displays
        dataText = new JTextArea((int) (width * 0.75), (int) (height * 0.75));
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
     * Adds listener to the enrollCourseButton
     *
     * @param listenForSearchCatalogButton listener for the button
     */
    public void addEnrollCourseListener(ActionListener listenForSearchCatalogButton) {
        enrollCourseButton.addActionListener(listenForSearchCatalogButton);
    }

    /**
     * Adds listener to the dropCourseButton
     *
     * @param listenForSearchCatalogButton listener for the button
     */
    public void addDropCourseListener(ActionListener listenForSearchCatalogButton) {
        dropCourseButton.addActionListener(listenForSearchCatalogButton);
    }

    /**
     * Adds listener to the browseCatalogButton
     *
     * @param listenForSearchCatalogButton listener for the button
     */
    public void addBrowseCatalogListener(ActionListener listenForSearchCatalogButton) {
        browseCatalogButton.addActionListener(listenForSearchCatalogButton);
    }

    /**
     * Adds listener to the searchCatalogButton
     *
     * @param listenForSearchCatalogButton listener for the button
     */
    public void addSearchCatalogListener(ActionListener listenForSearchCatalogButton) {
        searchCatalogButton.addActionListener(listenForSearchCatalogButton);
    }

    /**
     * Adds listener to the viewEnrolledCoursesButton
     *
     * @param listenForSearchCatalogButton listener for the button
     */
    public void addViewEnrolledCoursesListener(ActionListener listenForSearchCatalogButton) {
        viewEnrolledCoursesButton.addActionListener(listenForSearchCatalogButton);
    }

    /**
     * Adds listener to the logoutButton
     *
     * @param listenForSearchCatalogButton listener for the button
     */
    public void addLogoutListener(ActionListener listenForSearchCatalogButton) {
        logoutButton.addActionListener(listenForSearchCatalogButton);
    }
}
