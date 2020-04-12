package com.KerrYip.ServerController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.KerrYip.Model.Administrator;
import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;
import com.KerrYip.Model.Registration;
import com.KerrYip.Model.Student;

/**
 * This class implements the runnable interface so that user can interact with
 * the application during runtime
 * 
 * @author tyleryip
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

	private Student studentUser;
	private Administrator adminUser;

	/**
	 * Constructor for session connects I/O
	 * 
	 * @param aSocket           the socket to communicate with the client
	 * @param courseController  a controller to manipulate courses
	 * @param studentController a controller to manipulate students
	 */
	public Session(Socket aSocket, CourseController courseController, StudentController studentController) {
		// Set up instruction I/O
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

		this.studentUser = null;
		this.adminUser = new Administrator();
	}

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
			toClient.writeObject(toSend);
		} catch (IOException e) {
			System.err.println("Error: unable to write string to an object");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// This string is used to accept commands from the client and parse them into
		// instructions
		String command = "";

		while (!command.contentEquals("QUIT")) {
			syncData();
			command = readString();
			boolean successful = executeCommand(command);

			if (successful)
				System.out.println("[Server] Command: " + command + ", executed successfully");
			else
				System.out.println("[Server] Command: " + command + ", failed to execute");
		}
		syncData();
		// Now we have to close all connections to the client
		try {
			fromClient.close();
			toClient.close();
		} catch (IOException e) {
			System.err.println("Error: problem closing the sockets");
			e.printStackTrace();
		}
		System.out.println("[Server] User disconnected");
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

		case "QUIT":
			return true;

		case "logout":
			return logout();

		default:
			System.err.println("No option available that matched: " + command);
			return false;
		}
	}

	public void syncData() {
		studentController.syncData();
		courseController.syncData();
	}

	/**
	 * This method allows student users to log into the system and the server to
	 * select a student to manipulate
	 */
	private boolean studentLogin() {
		int checkID = -1;
		checkID = Integer.parseInt(readString());
		studentUser = studentController.searchStudent(checkID);
		if (studentUser != null) {
			if (!studentUser.isActive()) { // Check to make sure that this student is not already logged in to the
											// system
				System.out.println("[Sever] User logged in using id: " + checkID);
				writeString(studentUser.getStudentName());
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
			System.err.println("User " + checkID + " is already logged in to the system");
		}
		// If search fails write null to the client
		writeString("login failed");
		try {
			toClient.writeObject(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * Logs the user out
	 * 
	 * @return true if successful
	 */
	private boolean logout() {
		if (studentUser != null) {
			studentUser.setActive(false);
			setStudentUser(null);
		}
		if (adminUser.isActive()) {
			adminUser.setActive(false);
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
			Registration newReg = new Registration();
			for (CourseOffering offer : clientCourse.getOfferingList()) {
				if (section == offer.getSecNum()) {
					writeString("enroll successful");
					newReg.completeRegistration(studentUser, clientCourse.getCourseOfferingAt(section - 1));
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
				System.err.println("Error: couse not write this course to output stream");
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
		String credentials = "";
		credentials = readString();
		if (credentials.contentEquals(adminUser.getPassword())) { // Check to see if the password matches
			adminUser.setActive(true);
			writeString("login successful");
			return true;
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
		if (adminUser.isActive()) {
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
		Course toAdd = null;
		try {
			toAdd = (Course) fromClient.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: problem reading course from the input stream");
			e.printStackTrace();
			writeString("new course not added");
			return false;
		} catch (IOException e) {
			System.err.println("Error: unknown I/O exception");
			e.printStackTrace();
			writeString("new course not added");
			return false;
		}
		if (courseController.searchCat(toAdd.getNameNum()) == null) {
			courseController.addCourse(toAdd.getNameNum());
			CourseOffering newCourseOffering = null;
			do {
				try {
					newCourseOffering = (CourseOffering) fromClient.readObject();
					courseController.searchCat(toAdd.getNameNum()).addOffering(newCourseOffering);
				} catch (ClassNotFoundException e) {
					System.err.println("Error: class not found, unable to cast");
					e.printStackTrace();
				} catch (IOException e) {
					System.err.println("Error: problem with I/O");
					e.printStackTrace();
				}
			} while (newCourseOffering != null);
			writeString("Course added");
			return true;
		} 
		else {
			try {
				fromClient.readAllBytes();
			} catch (IOException e) {
				System.err.println("Error: problem clearing out the stream");
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
		Course toRemove = null;
		try {
			toRemove = (Course) fromClient.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: problem reading course from the input stream");
			e.printStackTrace();
			writeString("course not removed");
			return false;
		} catch (IOException e) {
			System.err.println("Error: unknown I/O exception");
			e.printStackTrace();
			writeString("course not removed");
			return false;
		}

		courseController.removeCourse(toRemove.getCourseName() + " " + toRemove.getCourseNum());
		writeString("course removed");
		return true;
	}
	
	/**
	 * Tries to start the semester by checking every course offering of every course to ensure that at least 8 students are registered in courses
	 * @return true if successful, false otherwise
	 */
	private boolean runCourses() {
		if (!adminLoggedIn()) {
			return false;
		}
		String result = "";
		for(Course c: courseController.getCourseList()) {
			for(CourseOffering o: c.getOfferingList()) {
				if(o.getOfferingRegList().size() < 8) {
					result += "\n" + o.getTheCourse().getNameNum() + " started successfully.";
				}
				else {
					result += "\n" + o.getTheCourse().getNameNum() + " failed to run.";
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
				toClient.writeObject(clientCourse);
				return true;
			} catch (IOException e) {
				System.err.println("Error: unable to write course to output stream");
				e.printStackTrace();
			}
		}
		writeString("course not found");
		writeString("Search completed");
		return false;
	}

	/**
	 * Sends each course in the catalog back to the user
	 */
	private boolean browseCourses() {
		if (!studentLoggedIn() && !adminLoggedIn()) {
			return false;
		}
		// Write each course into the output stream
		for (Course c : courseController.getCourseList()) {
			try {
				toClient.writeObject(c);
			} catch (IOException e) {
				System.err.println("Error: could not write the course to the output strean");
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

}
