package com.KerrYip.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import com.KerrYip.ClientModel.Course;
import com.KerrYip.ClientView.AdminMenuPanel;
import com.KerrYip.ClientView.BrowseCatalogPanel;
import com.KerrYip.ClientView.LoginSelectPanel;
import com.KerrYip.ClientView.MainView;
import com.KerrYip.ClientView.StudentMenuPanel;

/**
 * This class is used to communicate between the GUI and the ClientCommunicationController
 */
public class ClientGUIController {

    // the other controllers on the client side
    private ClientCommunicationController communicate;

    //the frame that is used for the GUI
    private MainView frame;

    //width and height of the frame
    private int width;
    private int height;

    /**
     * Constructor for the ClientGUIController
     * @param width the width of the frame
     * @param height the height of the frame
     * @param port the port number that will connect with server
     */
    public ClientGUIController(int width, int height, int port) {
        //generating controller for socket connections
        communicate = new ClientCommunicationController("localhost", port);
        
        //generating the frame
        frame = new MainView(width, height);

        //generating all panels that will be used by the frame
        loginSelectSetup();
        studentMenuSetup();
        adminMenuSetup();
        browseCatalogSetup();

        //adds listener for when the 'X' is pressed
        frame.addWindowListener(new MyWindowListener());

        //Starts the GUI, makes the frame visible and start on the login selection screen
        frame.start();
    }

    /**
     * Creates the JPanel for the login selection and adds all the buttons and adds it to the frame
     */
    public void loginSelectSetup() {
        //adding panel to frame
        frame.setLoginSelect(new LoginSelectPanel(getWidth(),getHeight()));

        //adding button listeners to panel
        frame.getLoginSelect().addStudentLoginListener(new StudentLoginListener());
        frame.getLoginSelect().addAdminLoginListener(new AdminLoginListener());
        frame.getLoginSelect().addQuitLoginListener(new QuitLoginListener());

        //adding panel to card Layout for switching between panels
        frame.addCard(frame.getLoginSelect(), "Login Select");
    }

    /**
     * Creates the JPanel for the student menu and adds all the buttons and adds it to the frame
     */
    public void studentMenuSetup() {
        //adding panel to frame
        frame.setStudentMenu(new StudentMenuPanel(getWidth(),getHeight()));

        //adding button listeners to panel
        frame.getStudentMenu().addEnrollCourseListener(new StudentEnrollCourseListener());
        frame.getStudentMenu().addDropCourseListener(new StudentDropCourseListener());
        frame.getStudentMenu().addBrowseCatalogListener(new BrowseCatalogListener());
        frame.getStudentMenu().addSearchCatalogListener(new SearchCatalogListener());
        frame.getStudentMenu().addViewEnrolledCoursesListener(new StudentViewEnrolledCoursesListener());
        frame.getStudentMenu().addLogoutListener(new LogoutListener());

        //adding panel to card Layout for switching between panels
        frame.addCard(frame.getStudentMenu(), "Student Menu");
    }

    /**
     * Creates the JPanel for the admin menu and adds all the buttons and adds it to the frame
     */
    public void adminMenuSetup() {
        //adding panel to frame
        frame.setAdminMenu(new AdminMenuPanel(getWidth(),getHeight()));

        //adding button listeners to panel
        frame.getAdminMenu().addAddCourseToCatalogListener(new AddCourseToCatalogListener());
        frame.getAdminMenu().addRemoveCourseFromCatalogListener(new RemoveCourseFromCatalogListener());
        frame.getAdminMenu().addBrowseCatalogListener(new BrowseCatalogListener());
        frame.getAdminMenu().addSearchCatalogListener(new SearchCatalogListener());
        frame.getAdminMenu().addViewStudentEnrolledCoursesListener(new AdminViewEnrolledCoursesListener());
        frame.getAdminMenu().addRunCoursesListener(new RunCoursesListener());
        frame.getAdminMenu().addLogoutListener(new LogoutListener());

        //adding panel to card Layout for switching between panels
        frame.addCard(frame.getAdminMenu(), "Admin Menu");
    }

    /**
     * Creates the JPanel for viewing the course catalog and adds all the buttons and adds it to the frame
     */
    public void browseCatalogSetup() {
        frame.setBrowseCatalog(new BrowseCatalogPanel());
        frame.getBrowseCatalog().addBackStudentMenuListener(new BackStudentMenu());
        frame.addCard(frame.getBrowseCatalog(), "Browse Catalog");
    }

    /**
     * Listens for when the button to login as a student as been pressed.
     * Prompts the user for their ID, if login in successful takes them to the student menu
     * other login fails and they remain at the login selection
     */
    class StudentLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //prompts user for student id
            String studentID = JOptionPane.showInputDialog("Please enter the student's id");
            if(studentID == null){
                //cancel is pressed, return to login selection
                frame.show("Login Select");
            }else {
                //ok is pressed, check if login is valid
                System.out.println("student login");
                String message = communicate.communicateStudentLogin("student login", studentID);
                if (message.equals("login successful")) {
                    //login is successful, take to student menu
                    frame.show("Student Menu");
                } else {
                    //login in unsuccessful, take back to login selection
                    JOptionPane.showMessageDialog(null, "Login Unsuccessful: Could not locate ID");
                    frame.show("Login Select");
                }
            }
        }
    }

    /**
     * Listens for when the button to login as a admin as been pressed.
     * Prompts the user for their ID, if login in successful takes them to the admin menu
     * other login fails and they remain at the login selection
     */
    class AdminLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //prompts user for admin id
            String adminID = JOptionPane.showInputDialog("Please enter the admin's id (lets make \"admin\" the id");
            if (adminID == null) {
                //cancel is pressed, return to login selection
                frame.show("Login Select");
            }else {
                //ok is pressed, check if login is valid
                System.out.println("admin login");
                //String message = communicate.communicateStudentLogin("admin login", adminID);
                String message = "login successful";
                if (message.equals("login successful")) {
                    //login is successful, take to admin menu
                    frame.show("Admin Menu");
                } else {
                    //login in unsuccessful, take back to login selection
                    JOptionPane.showMessageDialog(null, "Login Unsuccessful: Could not locate ID");
                    frame.show("Login Select");
                }
            }
        }
    }

    /**
     * Listens for when the button the quit the program is pressed.
     * Whens pressed closes all socket connections then shuts the program down
     */
    class QuitLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("QUIT");
            communicate.communicateQuit(); //closes socket connections
            System.exit(0);
        }
    }

    /**
     * Listens for when the button for a course to enroll is a course has been pressed.
     * When pressed prompts the user to enter their course and sends to server to be added
     * Displays a message in enrollment was successful or not
     */
    class StudentEnrollCourseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("enroll course");
            //prompts user for the course
            String courseName = JOptionPane.showInputDialog("Please enter the course you would like to enroll in");
            String[] temp = courseName.split(" ");
            Course tempCourse = new Course(temp[0], Integer.parseInt(temp[1]));
            String message = communicate.communicateSendCourse("enroll course", tempCourse);
            System.out.println(message);
            JOptionPane.showMessageDialog(null, message);
        }
    }

    /**
     * Listens for when the button for a course to drop a course has been pressed.
     * When pressed prompts the user to enter their course and sends to server to be added
     * Displays a message if dropping the course was successful or not
     */
    class StudentDropCourseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("drop course");
            String courseName = JOptionPane.showInputDialog("Please enter the course you would like to drop");
            String[] temp = courseName.split(" ");
            Course tempCourse = new Course(temp[0], Integer.parseInt(temp[1]));
            String message = communicate.communicateSendCourse("add course", tempCourse);
            System.out.println(message);
            JOptionPane.showMessageDialog(null, message);
        }
    }

    /**
     * Listens for when the view all course catalog button has been pressed
     * Prompts server for catalog list and displays on a new panel
     */
    class BrowseCatalogListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("browse catalog");
            //frame.show("Browse Catalog");
        }
    }

    /**
     * Listens for when the search catalog button has been pressed
     * Prompts user for the course they are searching for and sends it to server
     * if the result is found display, if not display that course isn't found
     */
    class SearchCatalogListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("search catalog");
            String courseName = JOptionPane.showInputDialog("Please enter the course you would like to search for");
            String[] temp = courseName.split(" ");
            Course tempCourse = new Course(temp[0], Integer.parseInt(temp[1]));
            String message = communicate.communicateSendCourse("add course", tempCourse);
            System.out.println(message);
            JOptionPane.showMessageDialog(null, message);
        }
    }

    /**
     * Listen for when the Enroll Student in a Course button has been pressed
     * Prompts the user for which student and which course they would like to enroll in
     * sends it to server and message displays if enrollment is successful or not
     */
    class StudentViewEnrolledCoursesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("student courses");
        }
    }

    /**
     * Listens for when the logout button is pressed and takes the user
     * back to the login selection
     */
    class LogoutListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.show("Login Select");
            }
        }

    /**
     * listens for when the back button is pressed while logged in as a student
     * takes the user back to the student menu
     */
    class BackStudentMenu implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.show("Student Menu");
        }
    }

    /**
     * listens for when the back button is pressed while logged in as a admin
     * takes the user back to the student menu
     */
    class BackAdminMenu implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            frame.show("Admin Menu");
        }
    }

    /**
     * Listens for when the add course to catalog button is pressed
     * Prompts user for information of the new course and sends it to server
     * message is displayed if the course was successfully added or not
     */
    class AddCourseToCatalogListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("add course");
        }
    }

    /**
     * Listens for when the remove course from catalog button is pressed
     * Prompts the user for the course name and sends it to server
     * message is displayed if the course was successfully removed or not
     */
    class RemoveCourseFromCatalogListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("remove course");
        }
    }

    /**
     * Listens for when the view Student's enrolled courses button is pressed
     * Prompts the user for the student ID and sends it to server
     * enrolled courses by that student is displayed if the ID is found
     * if not displays that it could not find that student
     */
    class AdminViewEnrolledCoursesListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("admin student courses");
        }
    }

    /**
     * Listens for when the Run Courses button is pressed
     * Attempts to run all courses and message is displayed on what courses ran successfully and what did not
     */
    class RunCoursesListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("run courses");
        }
    }

    /**
     * Listens for when the 'X' button at the top of the frame is pressed
     * Whens pressed closes all socket connections then shuts the program down
     */
    class MyWindowListener implements WindowListener {

        public void windowClosing(WindowEvent arg0) {
            System.out.println("QUIT");
            communicate.communicateQuit();
            System.exit(0);

        }

        public void windowOpened(WindowEvent arg0) {}
        public void windowClosed(WindowEvent arg0) {}
        public void windowIconified(WindowEvent arg0) {}
        public void windowDeiconified(WindowEvent arg0) {}
        public void windowActivated(WindowEvent arg0) {}
        public void windowDeactivated(WindowEvent arg0) {}

    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
