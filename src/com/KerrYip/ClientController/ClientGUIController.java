package com.KerrYip.ClientController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import com.KerrYip.ServerModel.Course;
//import com.KerrYip.ClientModel.Course;

import com.KerrYip.ClientView.AdminMenuPanel;
import com.KerrYip.ClientView.BrowseCatalogPanel;
import com.KerrYip.ClientView.LoginSelectPanel;
import com.KerrYip.ClientView.MainView;
import com.KerrYip.ClientView.StudentMenuPanel;
import com.KerrYip.ServerModel.CourseOffering;
import com.KerrYip.ServerModel.Registration;
import com.KerrYip.ServerModel.Student;

/**
 * This class is used to communicate between the GUI and the
 * ClientCommunicationController
 */
public class ClientGUIController {

	// the other controllers on the client side
	private ClientCommunicationController communicate;

	// the frame that is used for the GUI
	private MainView frame;

	// width and height of the frame
	private int width;
	private int height;

	/**
	 * Constructor for the ClientGUIController
	 * 
	 * @param width  the width of the frame
	 * @param height the height of the frame
	 * @param port   the port number that will connect with server
	 */
	public ClientGUIController(int width, int height, int port) {
		// generating controller for socket connections
		communicate = new ClientCommunicationController("localhost", port);

		// generating the frame
		frame = new MainView(width, height);

		// generating all panels that will be used by the frame
		loginSelectSetup();
		studentMenuSetup();
		adminMenuSetup();
		browseCatalogSetup();

		// adds listener for when the 'X' is pressed
		frame.addWindowListener(new MyWindowListener());

		// Starts the GUI, makes the frame visible and start on the login selection
		// screen
		frame.start();
	}

	/**
	 * Creates the JPanel for the login selection and adds all the buttons and adds
	 * it to the frame
	 */
	public void loginSelectSetup() {
		// adding panel to frame
		frame.setLoginSelect(new LoginSelectPanel(getWidth(), getHeight()));

		// adding button listeners to panel
		frame.getLoginSelect().addStudentLoginListener(new StudentLoginListener());
		frame.getLoginSelect().addAdminLoginListener(new AdminLoginListener());
		frame.getLoginSelect().addQuitLoginListener(new QuitLoginListener());

		// adding panel to card Layout for switching between panels
		frame.addCard(frame.getLoginSelect(), "Login Select");
	}

	/**
	 * Creates the JPanel for the student menu and adds all the buttons and adds it
	 * to the frame
	 */
	public void studentMenuSetup() {
		// adding panel to frame
		frame.setStudentMenu(new StudentMenuPanel(getWidth(), getHeight()));

		// adding button listeners to panel
		frame.getStudentMenu().addEnrollCourseListener(new StudentEnrollCourseListener());
		frame.getStudentMenu().addDropCourseListener(new StudentDropCourseListener());
		frame.getStudentMenu().addBrowseCatalogListener(new StudentBrowseCatalogListener());
		frame.getStudentMenu().addSearchCatalogListener(new SearchCatalogListener());
		frame.getStudentMenu().addLogoutListener(new LogoutListener());

		// adding panel to card Layout for switching between panels
		frame.addCard(frame.getStudentMenu(), "Student Menu");
	}

	/**
	 * Creates the JPanel for the admin menu and adds all the buttons and adds it to
	 * the frame
	 */
	public void adminMenuSetup() {
		// adding panel to frame
		frame.setAdminMenu(new AdminMenuPanel(getWidth(), getHeight()));

		// adding button listeners to panel
		frame.getAdminMenu().addAddCourseToCatalogListener(new AddCourseToCatalogListener());
		frame.getAdminMenu().addRemoveCourseFromCatalogListener(new RemoveCourseFromCatalogListener());
		frame.getAdminMenu().addBrowseCatalogListener(new AdminBrowseCatalogListener());
		frame.getAdminMenu().addSearchCatalogListener(new SearchCatalogListener());
		frame.getAdminMenu().addViewStudentEnrolledCoursesListener(new AdminViewEnrolledCoursesListener());
		frame.getAdminMenu().addAddStudentListener(new AddStudentListener());
		frame.getAdminMenu().addRunCoursesListener(new RunCoursesListener());
		frame.getAdminMenu().addLogoutListener(new LogoutListener());

		// adding panel to card Layout for switching between panels
		frame.addCard(frame.getAdminMenu(), "Admin Menu");
	}

	/**
	 * Creates the JPanel for viewing the course catalog and adds all the buttons
	 * and adds it to the frame
	 */
	public void browseCatalogSetup() {
		frame.setBrowseCatalog(new BrowseCatalogPanel(getWidth(), getHeight()));
		frame.getBrowseCatalog().addBackMenuListener(new BackStudentMenu());
		frame.addCard(frame.getBrowseCatalog(), "Browse Catalog");
	}

	/**
	 * Listens for when the button to login as a student as been pressed. Prompts
	 * the user for their ID, if login in successful takes them to the student menu
	 * other login fails and they remain at the login selection
	 */
	class StudentLoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// prompts user for student id
			String studentID = JOptionPane.showInputDialog("Please enter the student's id");
			if (studentID == null) {
				// cancel is pressed, return to login selection
				frame.show("Login Select");
			} else {
				// ok is pressed, check if login is valid
				System.out.println("student login");
				Student tempStudent = communicate.communicateStudentLoginString("student login", studentID);
				if (tempStudent == null) {
					// login in unsuccessful, take back to login selection
					JOptionPane.showMessageDialog(null, "Login Unsuccessful: Could not locate ID");
					frame.show("Login Select");

				} else {
					// login is successful, take to student menu
					frame.getStudentMenu().setTempStudent(tempStudent);
					frame.getStudentMenu().updateTitle();
					frame.getStudentMenu().updateEnrolledCourse();
					frame.show("Student Menu");
				}
			}
		}
	}

	/**
	 * Listens for when the button to login as a admin as been pressed. Prompts the
	 * user for their ID, if login in successful takes them to the admin menu other
	 * login fails and they remain at the login selection
	 */
	class AdminLoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// prompts user for admin id
			String adminID = JOptionPane.showInputDialog("Please enter the admin's id (lets make \"admin\" the id");
			if (adminID == null) {
				// cancel is pressed, return to login selection
				frame.show("Login Select");
			} else {
				// ok is pressed, check if login is valid
				System.out.println("admin login");
				String message = communicate.communicateSendString("admin login", adminID);
				if (message.equals("login successful")) {
					// login is successful, take to admin menu
					frame.show("Admin Menu");
				} else {
					// login in unsuccessful, take back to login selection
					JOptionPane.showMessageDialog(null, "Login Unsuccessful: Could not locate ID");
					frame.show("Login Select");
				}
			}
		}
	}

	/**
	 * Listens for when the button the quit the program is pressed. Whens pressed
	 * closes all socket connections then shuts the program down
	 */
	class QuitLoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("QUIT");
			communicate.communicateQuit(); // closes socket connections
			System.exit(0);
		}
	}

	/**
	 * Listens for when the button for a course to enroll is a course has been
	 * pressed. When pressed prompts the user to enter their course and sends to
	 * server to be added Displays a message in enrollment was successful or not
	 */
	class StudentEnrollCourseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("enroll course");

			// creates prompt
			JPanel enrollPanel = new JPanel();
			JLabel enrollTitle = new JLabel("Please enter the course you would like to enroll in");
			JPanel input = new JPanel();
			input.add(new JLabel("Course Name:"));
			JTextField courseName = new JTextField(10);
			input.add(courseName);
			input.add(new JLabel("Course Number:"));
			JTextField courseNumber = new JTextField(5);
			input.add(courseNumber);

			JPanel input2 = new JPanel();
			input2.add(new JLabel("Lecture Number:"));
			JTextField lectureNumber = new JTextField(5);
			input2.add(lectureNumber);

			enrollPanel.setLayout(new BorderLayout());
			enrollPanel.add("North", enrollTitle);
			enrollPanel.add("Center", input);
			enrollPanel.add("South", input2);

			// prompts user for the course
			try {
				int result = JOptionPane.showOptionDialog(null, enrollPanel, "Enroll In New Course",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

				if(result == JOptionPane.OK_OPTION) {
					Course tempCourse = new Course(courseName.getText(), Integer.parseInt(courseNumber.getText()));
					ArrayList<Registration> registrationList = communicate.communicateEnrollCourse("enroll course", tempCourse,lectureNumber.getText());

					if(registrationList.size() == 0) {
						JOptionPane.showMessageDialog(null, "Enrollment was unsuccessful");
					}else{
						frame.getStudentMenu().updateEnrolledCourse(registrationList);
						frame.show("Student Menu");
						JOptionPane.showMessageDialog(null, "Enrollment was successful");
					}

				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Invalid input");
			}
		}
	}

	/**
	 * Listens for when the button for a course to drop a course has been pressed.
	 * When pressed prompts the user to enter their course and sends to server to be
	 * added Displays a message if dropping the course was successful or not
	 */
	class StudentDropCourseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("drop course");

			// creates prompt
			JPanel enrollPanel = new JPanel();
			JLabel enrollTitle = new JLabel("Please enter the course you would like to drop");
			JPanel input = new JPanel();
			input.add(new JLabel("Course Name:"));
			JTextField courseName = new JTextField(10);
			input.add(courseName);
			input.add(new JLabel("Course Number:"));
			JTextField courseNumber = new JTextField(15);
			input.add(courseNumber);

			enrollPanel.setLayout(new BorderLayout());
			enrollPanel.add("North", enrollTitle);
			enrollPanel.add("Center", input);

			// prompts user for the course
			try{
				int result = JOptionPane.showOptionDialog(null, enrollPanel, "Drop a Course",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (result == JOptionPane.OK_OPTION) {
					Course tempCourse = new Course(courseName.getText(), Integer.parseInt(courseNumber.getText()));
					ArrayList<Registration> registrationList = communicate.communicateDropCourse("drop course", tempCourse);

					if(registrationList.size() == 0) {
						JOptionPane.showMessageDialog(null, "Drop was unsuccessful");
					}else{
						frame.getStudentMenu().updateEnrolledCourse(registrationList);
						frame.show("Student Menu");
						JOptionPane.showMessageDialog(null, "Drop was successful");
					}
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Invalid input");
			}
		}
	}

	/**
	 * Listens for when the view all course catalog button has been pressed Prompts
	 * server for catalog list and displays on a new panel
	 */
	class StudentBrowseCatalogListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("browse catalog");
			ArrayList<Course> catalog = communicate.communicateGetCourseList("browse courses");
			frame.getBrowseCatalog().updateCatalog(catalog);
			frame.show("Browse Catalog");
		}
	}

	/**
	 * Listens for when the view all course catalog button has been pressed Prompts
	 * server for catalog list and displays on a new panel
	 */
	class AdminBrowseCatalogListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("browse courses");
			ArrayList<Course> catalog = communicate.communicateGetCourseList("browse courses");
			frame.getAdminMenu().updateCourse(catalog);
		}
	}

	/**
	 * Listens for when the search catalog button has been pressed Prompts user for
	 * the course they are searching for and sends it to server if the result is
	 * found display, if not display that course isn't found
	 */
	class SearchCatalogListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("search catalog");

			// creates prompt
			JPanel dropPanel = new JPanel();
			JLabel dropTitle = new JLabel("Please enter the course you would like to search for");
			JPanel input = new JPanel();
			input.add(new JLabel("Course Name:"));
			JTextField courseName = new JTextField(10);
			input.add(courseName);
			input.add(new JLabel("Course Number:"));
			JTextField courseNumber = new JTextField(5);
			input.add(courseNumber);

			dropPanel.setLayout(new BorderLayout());
			dropPanel.add("North", dropTitle);
			dropPanel.add("Center", input);

			// prompts user for the course
			try {
				int result = JOptionPane.showOptionDialog(null, dropPanel, "Search for a Course",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (result == JOptionPane.OK_OPTION) {
					Course tempCourse = new Course(courseName.getText(), Integer.parseInt(courseNumber.getText()));
					String message = communicate.communicateSearchCourse("search for course", tempCourse);
					System.out.println(message);
					if (message.equals("Search completed")) {
						JOptionPane.showMessageDialog(null, "Course was not found");
					} else {
						JOptionPane.showMessageDialog(null, message);
					}
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Invalid input");
			}
		}
	}

	/**
	 * Listens for when the logout button is pressed and takes the user back to the
	 * login selection
	 */
	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.show("Login Select");
			communicate.communicateLogout("logout");
		}
	}

	/**
	 * listens for when the back button is pressed while logged in as a student
	 * takes the user back to the student menu
	 */
	class BackStudentMenu implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.show("Student Menu");
		}
	}

	/**
	 * Listens for when the add course to catalog button is pressed Prompts user for
	 * information of the new course and sends it to server message is displayed if
	 * the course was successfully added or not
	 */
	class AddCourseToCatalogListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("add course");

			// creates prompt
			JPanel addPanel = new JPanel();
			JLabel addTitle = new JLabel("Please enter the course name you would like to add");
			JPanel input = new JPanel();
			input.add(new JLabel("Course Name:"));
			JTextField courseName = new JTextField(10);
			input.add(courseName);
			input.add(new JLabel("Course Number:"));
			JTextField courseNumber = new JTextField(5);
			input.add(courseNumber);

			addPanel.setLayout(new BorderLayout());
			addPanel.add("North", addTitle);
			addPanel.add("Center", input);

			// creates prompt
			JPanel offeringPanel = new JPanel();
			JLabel offeringTitle = new JLabel("Please enter the course offering you would like to add");
			JPanel offeringInput = new JPanel();
			offeringInput.add(new JLabel("Section Number:"));
			JTextField secNum = new JTextField(5);
			offeringInput.add(secNum);
			offeringInput.add(new JLabel("Section Cap:"));
			JTextField secCap = new JTextField(5);
			offeringInput.add(secCap);

			offeringPanel.setLayout(new BorderLayout());
			offeringPanel.add("North", offeringTitle);
			offeringPanel.add("Center", offeringInput);

			Object[] options = { "Add another Course Offering", "Complete", "Cancel" };

			// prompts user for the course
			try {
				int result = JOptionPane.showOptionDialog(null, addPanel, "Add a Course", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (result == JOptionPane.OK_OPTION) {
					ArrayList<CourseOffering> courseOfferings = new ArrayList<CourseOffering>();
					int offeringResult = -1;
					while (offeringResult != JOptionPane.NO_OPTION) {
						offeringResult = JOptionPane.showOptionDialog(null, offeringPanel, "Add a Course Offering",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
						courseOfferings.add(new CourseOffering(Integer.parseInt(secNum.getText()),
								Integer.parseInt(secCap.getText())));
					}
					if (offeringResult == JOptionPane.CANCEL_OPTION) {
						return;
					}
					Course tempCourse = new Course(courseName.getText(), Integer.parseInt(courseNumber.getText()),
							courseOfferings);
					String message = communicate.communicateSendCourse("add course", tempCourse);
					System.out.println(message);
					if (message.equals("failed")) {
						JOptionPane.showMessageDialog(null, "Course could not be added");
					} else {
						JOptionPane.showMessageDialog(null, message);
					}
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Invalid input");
			}
		}
	}

	/**
	 * Listens for when the remove course from catalog button is pressed Prompts the
	 * user for the course name and sends it to server message is displayed if the
	 * course was successfully removed or not
	 */
	class RemoveCourseFromCatalogListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("remove course");

			// creates prompt
			JPanel dropPanel = new JPanel();
			JLabel dropTitle = new JLabel("Please enter the course you would like to remove");
			JPanel input = new JPanel();
			input.add(new JLabel("Course Name:"));
			JTextField courseName = new JTextField(10);
			input.add(courseName);
			input.add(new JLabel("Course Number:"));
			JTextField courseNumber = new JTextField(5);
			input.add(courseNumber);

			dropPanel.setLayout(new BorderLayout());
			dropPanel.add("North", dropTitle);
			dropPanel.add("Center", input);

			// prompts user for the course
			try {
				int result = JOptionPane.showOptionDialog(null, dropPanel, "Remove a Course",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

				if (result == JOptionPane.OK_OPTION) {
					Course tempCourse = new Course(courseName.getText(), Integer.parseInt(courseNumber.getText()));
					String message = communicate.communicateSearchCourse("remove course", tempCourse);
					System.out.println(message);
					if (message.equals("course not found")) {
						JOptionPane.showMessageDialog(null, "Course was not found");
					} else {
						JOptionPane.showMessageDialog(null, message);
					}
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(null, "Invalid input");
			}
		}
	}

	/**
	 * Listens for when the view Student's enrolled courses button is pressed
	 * Prompts the user for the student ID and sends it to server enrolled courses
	 * by that student is displays courses if the ID is found if not displays that
	 * it could not find that student
	 */
	class AdminViewEnrolledCoursesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("admin view student courses");
			String studentID = JOptionPane.showInputDialog("Please enter the student's id");
			ArrayList<Course> catalog = communicate.communicateGetStudentsCourseList("admin view student courses",
					studentID);
			if (catalog == null) {
				JOptionPane.showMessageDialog(null, "Unable to find the Student");
			} else {
				String temp = "";
				for (int i = 0; i < catalog.size(); i++) {
					temp += catalog.get(i);
				}
				JOptionPane.showMessageDialog(null, temp);
			}
		}
	}

	/**
	 * Listens for when the Add New Student button is pressed Prompts the user for
	 * the student info and sends it to server to be added Message is returns if
	 * successful or not
	 */
	class AddStudentListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("add student");
			String studentID = JOptionPane.showInputDialog("Please enter the student's id");
			String message = communicate.communicateSendString("add student", studentID);
			if (message == "failed") {
				JOptionPane.showMessageDialog(null, "Unable to create new Student");
			} else {
				JOptionPane.showMessageDialog(null, message);
			}
		}
	}

	/**
	 * Listens for when the Run Courses button is pressed Attempts to run all
	 * courses and message is displayed on what courses ran successfully and what
	 * did not
	 */
	class RunCoursesListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("run courses");
			String message = communicate.communicateReceiveString("run courses");
			JOptionPane.showMessageDialog(null, message);
		}
	}

	/**
	 * Listens for when the 'X' button at the top of the frame is pressed Whens
	 * pressed closes all socket connections then shuts the program down
	 */
	class MyWindowListener implements WindowListener {

		public void windowClosing(WindowEvent arg0) {
			System.out.println("QUIT");
			communicate.communicateQuit();
			System.exit(0);

		}

		public void windowOpened(WindowEvent arg0) {
		}

		public void windowClosed(WindowEvent arg0) {
		}

		public void windowIconified(WindowEvent arg0) {
		}

		public void windowDeiconified(WindowEvent arg0) {
		}

		public void windowActivated(WindowEvent arg0) {
		}

		public void windowDeactivated(WindowEvent arg0) {
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
