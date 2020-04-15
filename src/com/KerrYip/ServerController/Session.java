package com.KerrYip.ServerController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.KerrYip.Model.Administrator;
import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;
import com.KerrYip.Model.Registration;
import com.KerrYip.Model.Student;

/**
 * This class implements the runnable interface so that user can interact with
 * the application during runtime
 * 
 * @author Tyler Yip
 * @author Will Kerr
 * @version 2.0
 * @since 04/09/20
 *
 */
public class Session implements Runnable {

	// These I/O streams deal with sending Student objects back and forth between
	// the client
	private ObjectInputStream fromClient;
	private ObjectOutputStream toClient;

	// These controllers will be assigned to each session, so all sessions use the
	// same controllers
	private CourseController courseController;
	private StudentController studentController;
	private CourseOfferingController courseOfferingController;
	private RegistrationController registrationController;
	private AdministratorController administratorController;

	private Student studentUser;
	private Administrator adminUser;

	private SimpleDateFormat formatter;

	/**
	 * Constructor for session connects I/O
	 * 
	 * @param aSocket           the socket to communicate with the client
	 * @param courseController  a controller to manipulate courses
	 * @param studentController a controller to manipulate students
	 */
	public Session(Socket aSocket, CourseController courseController, StudentController studentController,
			CourseOfferingController courseOfferingController, RegistrationController registrationController,
				   AdministratorController administratorController) {
		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		/*
		 * stringIn = new BufferedReader(new
		 * InputStreamReader(aSocket.getInputStream())); stringOut = new
		 * PrintWriter(aSocket.getOutputStream(), true); The boolean argument for this
		 * line was added around 11:00am on 04/09/20, after a massive 4 hour debugging
		 * session; when setting up communication sockets for the server and client, it
		 * is imperative that this boolean be included or the client/server will hang
		 * waiting, even if a println is used.
		 */

		// Set up object I/O
		try {
			fromClient = new ObjectInputStream(aSocket.getInputStream());
			toClient = new ObjectOutputStream(aSocket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Error: problem with setting up the object input output streams");
			e.printStackTrace();
		}

		this.studentController = studentController;
		this.courseController = courseController;
		this.courseOfferingController = courseOfferingController;
		this.registrationController = registrationController;
		this.setAdministratorController(administratorController);

		this.studentUser = null;
		this.adminUser = null;
	}

	// THE FOLLOWING METHODS DEAL WITH CLIENT/SERVER I/O
	/**
	 * The following method is a helper to read the next object in the input stream
	 * as a string, used for commands
	 * 
	 * @return a string from the input stream
	 */
	private String readString() {
		String input = "";
		try {
			input = (String) fromClient.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: could not convert the object to a string");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * The following method allows strings to be sent though the object output
	 * socket easily, used for sending messages back to the client
	 * 
	 * @param toSend the string to send to the client
	 */
	private void writeString(String toSend) {
		try {
			toClient.reset();
			toClient.writeObject(toSend);
		} catch (IOException e) {
			System.err.println("Error: unable to write string to an object");
			e.printStackTrace();
		}
	}

	/**
	 * Reads an integer from the client
	 * 
	 * @return the integer read from the client
	 */
	private int readInt() {
		int i;
		try {
			i = Integer.parseInt(readString());
		} catch (NumberFormatException e) {
			return -1;
		}
		return i;
	}

	/**
	 * A helper function to check read course objects from the client socket
	 * 
	 * @return a Course if the course exists, null otherwise
	 */
	private Course readCourseFromClient() {
		Course clientCourse = null;
		try {
			clientCourse = (Course) fromClient.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: the class of this object was not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: I/O error");
			e.printStackTrace();
		}
		return clientCourse;
	}

	/**
	 * Used to log events on the server's console
	 * 
	 * @param message the message to log
	 */
	private void serverLog(String message) {
		System.out.println("[Server @" + formatter.format(new Date()) + "] " + message);
	}

	/**
	 * Used to log errors on the server's console
	 * 
	 * @param error
	 */
	private void serverError(String error) {
		System.err.println("[Server @" + formatter.format(new Date()) + "] Error: " + error);
	}

	@Override
	public void run() {
		// This string is used to accept commands from the client and parse them into
		// instructions
		String command = "";

		while (!command.contentEquals("QUIT")) {
			command = readString();

			if (executeCommand(command))
				serverLog("Command: " + command + ", executed successfully");
			else
				serverLog("Command: " + command + ", failed to execute");
		}

		// Now we have to close all connections to the client
		try {
			fromClient.close();
			toClient.close();
		} catch (IOException e) {
			serverError("problem closing the sockets");
			e.printStackTrace();
		}
		serverLog("User disconnected");
	}

	/**
	 * Takes the user's input command and figures out which set of instructions to
	 * execute
	 * 
	 * @param command a string telling the server which command they want to execute
	 * @return true if the command executes successfully, false otherwise
	 */
	private boolean executeCommand(String command) {
		switch (command) {
		case "student login":
			return studentLogin();

		case "enroll course":
			return enrollCourse();

		case "drop course":
			return dropCourse();

		case "student courses":
			return showStudentCourses();

		case "add course":
			return addCourse();

		case "remove course":
			return removeCourse();

		case "browse courses":
			return browseCourses();

		case "search for course":
			return searchForCourse();

		case "admin login":
			return adminLogin();

		case "run courses":
			return runCourses();

		case "admin view student courses":
			return searchForStudent();

		case "add student":
			return addNewStudent();
			
		case "assign grade":
			return assignGrade();
			
		case "add prereq":
			//TODO complete the implementation of this method
			return false;

		case "QUIT":
			return true;

		case "logout":
			return logout();

		default:
			serverLog("No option available that matched: " + command);
			return false;
		}
	}

	/**
	 * This method allows student users to log into the system and the server to
	 * select a student to manipulate
	 */
	private boolean studentLogin() {
		int checkID = -1;
		checkID = readInt();
		studentUser = studentController.searchStudent(checkID);
		String passwordInput = readString();
		
		if (studentUser != null) {
			if (!studentUser.isActive()) { // Check to make sure that this student is not already logged in to the
											// system
				if (passwordInput.contentEquals(studentUser.getPassword())) { //Validate the user's password against the password in the system

					studentUser.setActive(true);
					serverLog("User logged in using id: " + checkID);
					writeString("login successful");
					writeString(studentUser.getStudentName());
					try {
						for (Registration r : studentUser.getStudentRegList()) {
							toClient.writeObject(r);
						}
						toClient.writeObject(null);
						return true;
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			else 
			{
				serverError("User " + checkID + " is already logged in to the system");
				writeString("User already logged in");
				return false;
			}
		}
		
		writeString("login failed");
		return false;
	}

	/**
	 * Checks to see if the student user is logged in or not
	 * 
	 * @return true if the student is logged in to a valid student, false otherwise
	 */
	private boolean studentLoggedIn() {
		if (studentUser != null) {
			return true;
		}
		return false;
	}

	/**
	 * Logs the user out, works for both student and admin
	 * 
	 * @return true if successful
	 */
	private boolean logout() {
		if (studentUser != null) {
			studentUser.setActive(false);
			setStudentUser(null);
		}
		if (adminUser != null) {
			adminUser.setActive(false);
			setAdminUser(null);
		}
		return true;
	}

	/**
	 * This method takes a student and course from the client, looks for the course,
	 * and if successful, adds it to the student and returns the student object to
	 * client
	 */
	private boolean enrollCourse() {
		if (!studentLoggedIn()) {
			return false;
		}
		Course clientCourse = readCourseFromClient();
		clientCourse = courseController.searchCat(clientCourse.getCourseName(), clientCourse.getCourseNum());
		int section = Integer.parseInt(readString());
		if (clientCourse != null) {
			Registration newReg = registrationController.addRegistration();
			for (CourseOffering offer : clientCourse.getOfferingList()) {
				if (section == offer.getSecNum()) {
					writeString("enroll successful");
					newReg.completeRegistration(studentUser, clientCourse.getCourseOfferingAt(section - 1));
					registrationController.confirmRegistration(newReg);
					try {
						for (Registration r : studentUser.getStudentRegList()) {
							toClient.writeObject(r);
						}
						toClient.writeObject(null);
						return true;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		writeString("enroll failed");
		try {
			toClient.writeObject(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Removes a course from the user's registration list
	 */
	private boolean dropCourse() {
		if (!studentLoggedIn()) {
			return false;
		}
		Course clientCourse = readCourseFromClient();
		clientCourse = courseController.searchCat(clientCourse.getCourseName(), clientCourse.getCourseNum());
		System.out.println("[TEST] " + clientCourse);
		Registration removeReg = null;
		removeReg = studentUser.searchStudentReg(clientCourse);
		if (removeReg != null) {
			studentUser.getStudentRegList().remove(removeReg);
			registrationController.removeRegistration(removeReg);
			writeString("drop successful");
			try {
				for (Registration r : studentUser.getStudentRegList()) {
					toClient.writeObject(r);
				}
				toClient.writeObject(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		writeString("drop failed");
		try {
			toClient.writeObject(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * Sends all courses in the student's registered courses to the client
	 * 
	 * @return true if successful, false if failed
	 */
	private boolean showStudentCourses() {
		if (!studentLoggedIn()) {
			return false;
		}
		for (int i = 0; i < studentUser.getStudentRegList().size(); i++) {
			try {
				toClient.writeObject(studentUser.getStudentRegList().get(i));
			} catch (IOException e) {
				serverLog("Could not write this course to output stream");
				e.printStackTrace();
				return false;
			}
		}

		// The very last thing we write is a null to tell the client we are done
		try {
			toClient.writeObject(null);
		} catch (IOException e) {
			System.err.println("Error: could not write the course to the output strean");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * This method allows administrator users to log into the system
	 */
	private boolean adminLogin() {
		String username = readString();
		String password = readString();
		if (!username.contentEquals("")) { //Ensure input is valid
			Administrator theAdmin = administratorController.searchAdmin(username);
			if (theAdmin != null) { 
				if(theAdmin.isActive()) { // Check to make sure that this student is not already logged in to the system
					serverLog("Admin user " + theAdmin.getUsername() +"is already logged in");
					writeString("user already logged in");
					return false;
				}
					if(!password.contentEquals("") && theAdmin.getPassword().contentEquals(password)) {
						this.adminUser = theAdmin;
						theAdmin.setActive(true);
						writeString("login success");
						return true;
					}
			}
		}
		writeString("login failed");
		return false;
	}

	/**
	 * Checks to see if the administrator user is logged in or not
	 * 
	 * @return true if the administrator is logged in to a valid administrator
	 *         account, false otherwise
	 */
	private boolean adminLoggedIn() {
		if (adminUser != null) {
			return true;
		}
		return false;
	}

	/**
	 * Adds a course to the list of available courses
	 * 
	 * @return true if successful, false otherwise
	 */
	private boolean addCourse() {
		if (!adminLoggedIn()) {
			return false;
		}
		Course toAdd = readCourseFromClient();

		// Check to see if the coruse already exists, if not, we can add it
		if (courseController.searchCat(toAdd.getNameNum()) == null) {
			courseController.addCourse(toAdd.getNameNum());

			// We need to know how many sections
			String input = readString();
			String[] sections = input.split(";");
			for (int i = 0; i < sections.length; i++) {
				courseOfferingController.addCourseOffering(courseController.searchCat(toAdd.getNameNum()), i + 1,
						Integer.parseInt(sections[i]));
				courseController.createCourseOffering(courseController.searchCat(toAdd.getNameNum()), i + 1,
						Integer.parseInt(sections[i]));
			}
			writeString("Course added");
			return true;
		} else {
			try {
				fromClient.readAllBytes();
			} catch (IOException e) {
				serverError("problem clearing out the stream");
				e.printStackTrace();
			}
			writeString("Course already exists");
			return false;
		}
	}

	/**
	 * Removes a course to the list of available courses
	 * 
	 * @return true if successful, false otherwise
	 */
	private boolean removeCourse() {
		if (!adminLoggedIn()) {
			return false;
		}
		Course toRemove = readCourseFromClient();

		if (toRemove == null) {
			writeString("course not removed");
			return false;
		}
		// We need to remove the course from any student that may be registered in it
		studentController.removeCourseFromAll(toRemove);
		// We need to remove all registrations that have the course in it
		registrationController.removeAllRegistrations(toRemove);
		// We need to remove the all course offerings that have the course
		courseOfferingController.removeAllCourseOfferings(toRemove);
		// Now that all dependencies are taken care off, we can remove it
		courseController.removeCourse(toRemove.getNameNum());
		writeString("course removed");
		return true;
	}

	/**
	 * Tries to start the semester by checking every course offering of every course
	 * to ensure that at least 8 students are registered in courses
	 * 
	 * @return true if successful, false otherwise
	 */
	private boolean runCourses() {
		if (!adminLoggedIn()) {
			return false;
		}
		String result = "";
		for (Course c : courseController.getCourseList()) {
			for (CourseOffering o : c.getOfferingList()) {
				if (o.getOfferingRegList().size() >= 8) {
					result += "\n" + o.getTheCourse().getNameNum() + " section #" + o.getSecNum()
							+ " started successfully, at least 8 students are registered";
				} else {
					result += "\n" + o.getTheCourse().getNameNum() + " section #" + o.getSecNum()
							+ " failed to run, less than 8 students registered in the section.";
				}
			}
		}
		writeString(result);
		return true;
	}

	/**
	 * Allows a student to search for a course and returns the results of their
	 * search back to the client
	 * 
	 * @return true if found, false otherwise
	 */
	private boolean searchForCourse() {
		if (!studentLoggedIn() && !adminLoggedIn()) {
			return false;
		}
		Course clientCourse = readCourseFromClient();
		clientCourse = courseController.searchCat(clientCourse.getCourseName(), clientCourse.getCourseNum());
		if (clientCourse != null) {
			try {
				writeString("course found");
				toClient.reset();
				toClient.writeObject(clientCourse);
				return true;
			} catch (IOException e) {
				serverError("Unable to write course to output stream");
				e.printStackTrace();
			}
		}
		writeString("course not found");
		writeString("Search completed");
		return false;
	}

	/**
	 * Browses all courses in the course catalog
	 * 
	 * @return true if successful, false if failed
	 */
	private boolean browseCourses() {
		if (!studentLoggedIn() && !adminLoggedIn()) {
			return false;
		}
		// Write each course into the output stream
		for (Course c : courseController.getCourseList()) {
			try {
				toClient.reset();
				toClient.writeObject(c);
			} catch (IOException e) {
				serverError("Could not write the course to the output strean");
				e.printStackTrace();
				return false;
			}
		}

		// The very last thing we write is a null to tell the client we are done
		try {
			toClient.writeObject(null);
		} catch (IOException e) {
			serverError("Could not write the course to the output strean");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Searches for a student as an admin and returns their registration
	 * 
	 * @return true if successful, false if failed
	 */
	private boolean searchForStudent() {
		if (!adminLoggedIn()) {
			return false;
		}
		int checkID = readInt();
		Student theStudent = studentController.searchStudent(checkID);
		if (theStudent == null) {
			writeString("could not find student");
			return false;
		} else {
			writeString("student found");
			try {
				for (Registration r : theStudent.getStudentRegList()) {
					toClient.writeObject(r);
				}
				toClient.writeObject(null);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}

		}
	}

	/**
	 * Adds a new student into the student list
	 * 
	 * @return true if successful, false otherwise
	 */
	private boolean addNewStudent() {
		if (!adminLoggedIn()) {
			return false;
		}
		String newName = readString();
		String newPassword = readString();

		if (newName.contentEquals("") || newPassword.contentEquals("")) {
			writeString("failed to add");
			return false;
		}

		studentController.addStudent(newName, newPassword);
		writeString("" + (studentController.getMyStudentList().get(studentController.getMyStudentList().size() - 1)
				.getStudentId()));
		return true;
	}
	
	/**
	 * Assigns a grade to a student
	 * @return true if successful, false if failed
	 */
	private boolean assignGrade() {
		if (!adminLoggedIn()) {
			return false;
		}
		//We need to get the name of the student to assign a grade to
		int studentId = readInt();
		String courseNameNum = readString();
		String grade = readString();
		Student theStudent = studentController.searchStudent(studentId);
		if(theStudent != null && !grade.contentEquals("")) {
			Registration theReg = theStudent.searchStudentReg(courseController.searchCat(courseNameNum));
			if(theReg != null) {
				theReg.setGrade(grade.charAt(0));
				writeString("grade set successfully");
				return true;
			}
		}
		writeString("failed to set grade");
		return false;
	}

	// GETTERS and SETTERS
	public ObjectInputStream getFromClient() {
		return fromClient;
	}

	public void setFromClient(ObjectInputStream fromClient) {
		this.fromClient = fromClient;
	}

	public ObjectOutputStream getToClient() {
		return toClient;
	}

	public void setToClient(ObjectOutputStream toClient) {
		this.toClient = toClient;
	}

	public CourseController getCourseController() {
		return courseController;
	}

	public void setCourseController(CourseController courseController) {
		this.courseController = courseController;
	}

	public StudentController getStudentController() {
		return studentController;
	}

	public void setStudentController(StudentController studentController) {
		this.studentController = studentController;
	}

	public Student getStudentUser() {
		return studentUser;
	}

	public void setStudentUser(Student studentUser) {
		this.studentUser = studentUser;
	}

	public Administrator getAdminUser() {
		return adminUser;
	}

	public void setAdminUser(Administrator adminUser) {
		this.adminUser = adminUser;
	}

	public RegistrationController getRegistrationController() {
		return registrationController;
	}

	public void setRegistrationController(RegistrationController registrationController) {
		this.registrationController = registrationController;
	}

	public AdministratorController getAdministratorController() {
		return administratorController;
	}

	public void setAdministratorController(AdministratorController administratorController) {
		this.administratorController = administratorController;
	}

}
