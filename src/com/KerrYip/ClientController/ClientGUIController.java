package com.KerrYip.ClientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.KerrYip.ClientModel.Course;
import com.KerrYip.ClientView.LoginSelectPanel;
import com.KerrYip.ClientView.MainView;
import com.KerrYip.ClientView.StudentMenuPanel;

public class ClientGUIController {

    // the other controllers on the client side
    private ClientCommunicationController communicate;
    private MainView frame;

    public ClientGUIController(int width, int height){
        communicate = new ClientCommunicationController("localhost",9898);

        //generating MainView with frame with all panels to change between
        frame = new MainView(width, height);
        loginSelectSetup();
        studentMenuSetup();
        frame.start();
    }

    public void loginSelectSetup(){
        frame.setLoginSelect(new LoginSelectPanel());
        frame.getLoginSelect().addStudentLoginListener(new StudentLoginListener());
        frame.getLoginSelect().addAdminLoginListener(new AdminLoginListener());
        frame.getLoginSelect().addQuitLoginListener(new QuitLoginListener());
        frame.addCard(frame.getLoginSelect(),"Login Select");
    }

    public void studentMenuSetup(){
        frame.setStudentMenu(new StudentMenuPanel());
        frame.getStudentMenu().addEnrollCourseListener(new StudentEnrollCourseListener());
        frame.getStudentMenu().addDropCourseListener(new StudentDropCourseListener());
        frame.getStudentMenu().addBrowseCatalogListener(new BrowseCatalogListener());
        frame.getStudentMenu().addSearchCatalogListener(new SearchCatalogListener());
        frame.getStudentMenu().addViewEnrolledCoursesListener(new StudentViewEnrolledCoursesListener());
        frame.getStudentMenu().addLogoutListener(new LogoutListener());
        frame.addCard(frame.getStudentMenu(),"Student Menu");
    }

    class StudentLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            String studentID = JOptionPane.showInputDialog("Please enter the student's id");
            String message = communicate.communicateStudentLogin("student login", studentID);
            System.out.println(message);
            if(message.equals("login successful")){
                frame.show("Student Menu");
            }else{
                JOptionPane.showMessageDialog(null,"Login Unsuccessful: Could not locate ID");
                frame.show("Login Select");
            }
        }
    }

    class AdminLoginListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Admin login");
            }
        }

        class QuitLoginListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                communicate.communicateQuit();
                System.exit(0);
            }
        }

        class StudentEnrollCourseListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = JOptionPane.showInputDialog("Please enter the course you would like to enroll in");
                String[] temp = courseName.split(" ");
                Course tempCourse = new Course(temp[0], Integer.parseInt(temp[1]));
                String message = communicate.communicateSendCourse("add course", tempCourse);
                System.out.println(message);
                JOptionPane.showMessageDialog(null, message);
            }
        }

        class StudentDropCourseListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
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
                System.out.println("Browse catalog");
            }
        }

        class SearchCatalogListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
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
                System.out.println("Student's Enrolled Courses");
            }
        }

        class LogoutListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                frame.show("Login Select");
            }
        }

}
