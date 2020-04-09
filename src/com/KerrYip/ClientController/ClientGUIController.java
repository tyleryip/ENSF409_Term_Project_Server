package com.KerrYip.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.KerrYip.ClientModel.Course;
import com.KerrYip.ClientView.*;

public class ClientGUIController {

    // the other controllers on the client side
    private ClientCommunicationController communicate;
    private MainView frame;

    public ClientGUIController(int width, int height) {
        communicate = new ClientCommunicationController("localhost", 9898);

        //generating MainView with frame with all panels to change between
        frame = new MainView(width, height);
        loginSelectSetup();
        studentMenuSetup();
        adminMenuSetup();
        browseCatalogSetup();
        frame.start();
    }

    public void loginSelectSetup() {
        frame.setLoginSelect(new LoginSelectPanel());
        frame.getLoginSelect().addStudentLoginListener(new StudentLoginListener());
        frame.getLoginSelect().addAdminLoginListener(new AdminLoginListener());
        frame.getLoginSelect().addQuitLoginListener(new QuitLoginListener());
        frame.addCard(frame.getLoginSelect(), "Login Select");
    }

    public void studentMenuSetup() {
        frame.setStudentMenu(new StudentMenuPanel());
        frame.getStudentMenu().addEnrollCourseListener(new StudentEnrollCourseListener());
        frame.getStudentMenu().addDropCourseListener(new StudentDropCourseListener());
        frame.getStudentMenu().addBrowseCatalogListener(new BrowseCatalogListener());
        frame.getStudentMenu().addSearchCatalogListener(new SearchCatalogListener());
        frame.getStudentMenu().addViewEnrolledCoursesListener(new StudentViewEnrolledCoursesListener());
        frame.getStudentMenu().addLogoutListener(new LogoutListener());
        frame.addCard(frame.getStudentMenu(), "Student Menu");
    }

    public void adminMenuSetup() {
        frame.setAdminMenu(new AdminMenuPanel());
        frame.getAdminMenu().addAddCourseToCatalogListener(new AddCourseToCatalogListener());
        frame.getAdminMenu().addRemoveCourseFromCatalogListener(new RemoveCourseFromCatalogListener());
        frame.getAdminMenu().addBrowseCatalogListener(new BrowseCatalogListener());
        frame.getAdminMenu().addSearchCatalogListener(new SearchCatalogListener());
        frame.getAdminMenu().addViewStudentEnrolledCoursesListener(new AdminViewEnrolledCoursesListener());
        frame.getAdminMenu().addRunCoursesListener(new RunCoursesListener());
        frame.getAdminMenu().addLogoutListener(new LogoutListener());
        frame.addCard(frame.getAdminMenu(), "Admin Menu");
    }

    public void browseCatalogSetup() {
        frame.setBrowseCatalog(new BrowseCatalogPanel());
        frame.getBrowseCatalog().addBackStudentMenuListener(new BackStudentMenu());
        frame.addCard(frame.getBrowseCatalog(), "Browse Catalog");
    }

    class StudentLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("student login");
            String studentID = JOptionPane.showInputDialog("Please enter the student's id");
            String message = communicate.communicateStudentLogin("student login", studentID);
            if (message.equals("login successful")) {
                frame.show("Student Menu");
            } else {
                JOptionPane.showMessageDialog(null, "Login Unsuccessful: Could not locate ID");
                frame.show("Login Select");
            }
        }
    }

    class AdminLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("admin login");
            String adminID = JOptionPane.showInputDialog("Please enter the admin's id (lets make \"admin\" the id");
            //String message = communicate.communicateStudentLogin("admin login", adminID);
            String message = "login successful";
            if (message.equals("login successful")) {
                frame.show("Admin Menu");
            } else {
                JOptionPane.showMessageDialog(null, "Login Unsuccessful: Could not locate ID");
                frame.show("Login Select");
            }
        }
    }

    class QuitLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("QUIT");
            communicate.communicateQuit();
            System.exit(0);
        }
    }

    class StudentEnrollCourseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("enroll course");
            String courseName = JOptionPane.showInputDialog("Please enter the course you would like to enroll in");
            String[] temp = courseName.split(" ");
            Course tempCourse = new Course(temp[0], Integer.parseInt(temp[1]));
            String message = communicate.communicateSendCourse("enroll course", tempCourse);
            System.out.println(message);
            JOptionPane.showMessageDialog(null, message);
        }
    }

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

    class BrowseCatalogListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("browse catalog");
            //frame.show("Browse Catalog");
        }
    }

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

    class StudentViewEnrolledCoursesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("student courses");
        }
    }

        class LogoutListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.show("Login Select");
            }
        }

        class BackStudentMenu implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.show("Student Menu");
            }
        }

        class AddCourseToCatalogListener implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add course");
            }
        }

    class RemoveCourseFromCatalogListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("remove course");
        }
    }

    class AdminViewEnrolledCoursesListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("admin student courses");
        }
    }

    class RunCoursesListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("run courses");
        }
    }


}
