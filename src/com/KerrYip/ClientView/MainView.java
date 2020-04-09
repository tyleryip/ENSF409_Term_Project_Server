package com.KerrYip.ClientView;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.KerrYip.ClientController.ClientCommunicationController;
import com.KerrYip.ClientController.NewClientCommunicationController;
import com.KerrYip.ClientModel.Course;

public class MainView extends JFrame {

    private LoginSelectPanel loginSelect;
    private StudentMenuPanel studentMenu;
    private JPanel cardList;
    private CardLayout cardControl;

    //temporary solution
    private NewClientCommunicationController communicate;

    public MainView(int height, int width, NewClientCommunicationController communicate){
        this.setSize(height,width);
        this.communicate = communicate;
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardList = new JPanel();
        cardList.setLayout(new CardLayout());

        loginSelectSetup(cardList);
        studentMenuSetup(cardList);

        add(cardList);
        setVisible(true);
        cardControl = (CardLayout)(cardList.getLayout());
        cardControl.show(cardList, "Login Select");
    }

    public void loginSelectSetup(JPanel pane){
        loginSelect = new LoginSelectPanel();
        loginSelect.addStudentLoginListener(new StudentLoginListener());
        loginSelect.addAdminLoginListener(new AdminLoginListener());
        //loginSelect.addQuitLoginListener(new QuitLoginListener());
        pane.add(loginSelect, "Login Select");
    }

    public void studentMenuSetup(JPanel pane){
        studentMenu = new StudentMenuPanel();
        /*
        studentMenu.addEnrollCourseListener(new StudentEnrollCourseListener());
        studentMenu.addDropCourseListener(new StudentDropCourseListener());
        studentMenu.addBrowseCatalogListener(new BrowseCatalogListener());
        studentMenu.addSearchCatalogListener(new SearchCatalogListener());
        */
        studentMenu.addViewEnrolledCoursesListener(new StudentViewEnrolledCoursesListener());
        studentMenu.addLogoutListener(new LogoutListener());
        pane.add(studentMenu, "Student Menu");
    }

    class StudentLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            String studentID = JOptionPane.showInputDialog("Please enter the student's id");
            String message = communicate.communicateStudentLogin("student login", studentID);
            System.out.println(message);
            if(message.equals("login successful")){
                cardControl.show(cardList, "Student Menu");
            }else{
                JOptionPane.showMessageDialog(null,"Login Unsuccessful: Could not locate ID");
                loginSelectSetup(cardList);
            }
        }
    }

    class AdminLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println("Admin login");
        }
    }
/*
    class QuitLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            communicate.communicationQuit();
            System.exit(0);
        }
    }

    class StudentEnrollCourseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            String courseName = JOptionPane.showInputDialog("Please enter the course you would like to enroll in");
            String[] temp = courseName.split(" ");
            Course tempCourse = new Course(temp[0], Integer.parseInt(temp[1]));
            String message = communicate.communicationSendCourse("add course", tempCourse);
            System.out.println(message);
            JOptionPane.showMessageDialog(null,message);
            }
        }

    class StudentDropCourseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            String courseName = JOptionPane.showInputDialog("Please enter the course you would like to drop");
            String[] temp = courseName.split(" ");
            Course tempCourse = new Course(temp[0], Integer.parseInt(temp[1]));
            String message = communicate.communicationSendCourse("add course", tempCourse);
            System.out.println(message);
            JOptionPane.showMessageDialog(null,message);
        }
    }

    class BrowseCatalogListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println("Browse catalog");
        }
    }

    class SearchCatalogListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            String courseName = JOptionPane.showInputDialog("Please enter the course you would like to search for");
            String[] temp = courseName.split(" ");
            Course tempCourse = new Course(temp[0], Integer.parseInt(temp[1]));
            String message = communicate.communicationSendCourse("add course", tempCourse);
            System.out.println(message);
            JOptionPane.showMessageDialog(null,message);
        }
    }
*/
    class StudentViewEnrolledCoursesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            System.out.println("Student's Enrolled Courses");
        }
    }

    class LogoutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            cardControl.show(cardList,"Login Select");
        }
    }
}
