package com.KerrYip.ServerController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.KerrYip.ServerModel.Administrator;
import com.KerrYip.ServerModel.Course;
import com.KerrYip.ServerModel.Registration;
import com.KerrYip.ServerModel.Student;

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
	 * @param aSocket
	 */
	public Session(Socket aSocket, CourseController courseController, StudentController studentController) {
		// Set up instruction I/O
//			stringIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
//			stringOut = new PrintWriter(aSocket.getOutputStream(), true);
		// The boolean argument for this line was
		// added around 11:00am on 04/09/20, after a
		// massive 4 hour debugging session; when
		// setting up communication sockets for the
		// server and client, it is imperative that
		// this boolean be included or the
		// client/server will hang waiting, even if
		// a println() is used.

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
		this.adminUser = null;
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
			command = readString();
			boolean successful = executeCommand(command);

			if (successful)
				System.out.println("[Server] Command: " + command + ", executed successfully");
			else
				System.out.println("[Server] Command: " + command + ", failed to execute");
		}

		// Close all communication channels in the event that the user quits
		/*
		 * try { stringOut.close(); stringIn.close(); //objectOut.close();
		 * //objectIn.close(); } catch (IOException e) {
		 * System.err.println("Error: unable to close communication sockets");
		 * e.printStackTrace(); }
		 */

	}

	/**
	 * Takes the user's input command and figures out which set of instructions to
	 * execute
	 * 
	 * @param command a string telling the server which command they want to execute
	 */
	private boolean executeCommand(String command) {
		switch (command) {
		case "student login":
			System.out.println("doing student login");
			return studentLogin();

		case "add course":
			return addCourse();

		case "remove course":
			return removeCourse();

		case "browse courses":
			return browseCourses();

		case "search for course":
			return searchForCourse();
			
		case "admin login":
			return false;

		default:
			System.err.println("No option available that matched: " + command);
			return          false;
		}

	}

	/**
	 * This method allows student users to log into the system and the server to
	 * select a student to manipulate
	 */
	private boolean studentLogin() {
		int checkID = -1;
		checkID = Integer.parseInt(readString());
		System.out.println(checkID);
		studentUser = studentController.searchStudent(checkID);
		if (studentUser != null) {
			writeString("login successful");
			return true;
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

	private boolean searchForCourse() {
		if (!studentLoggedIn()) {
			return false;
		}
		Course clientCourse = readCourseFromClient();
		clientCourse = courseController.searchCat(clientCourse.getCourseName(), clientCourse.getCourseNum());
		if (clientCourse != null) {
			try {
				toClient.writeObject(clientCourse);
				return true;
			} catch (IOException e) {
				System.err.println("Error: unable to write course to output stream");
				e.printStackTrace();
			}
		}
		writeString("Search completed.");
		return false;
	}

	/**
	 * Sends each course in the catalog back to the user
	 */
	private boolean browseCourses() {
		if (!studentLoggedIn()) {
			return false;
		}
		for (Course c : courseController.getCourseList()) {
			try {
				toClient.writeObject(c);
				return true;
			} catch (IOException e) {
				System.err.println("Error: unable to write course to output stream");
				e.printStackTrace();
			}
		}
		writeString("Browse completed.");
		return false;
	}

	/**
	 * This method takes a student and course from the client, looks for the course,
	 * and if successful, adds it to the student and returns the student object to
	 * client
	 */
	private boolean addCourse() {
		if (!studentLoggedIn()) {
			return false;
		}
		Course clientCourse = readCourseFromClient();

		clientCourse = courseController.searchCat(clientCourse.getCourseName(), clientCourse.getCourseNum());
		if (clientCourse != null) {
			Registration newReg = new Registration();
			newReg.completeRegistration(studentUser, clientCourse.getCourseOfferingAt(0));
			writeString("Sucessfullyy added this course to your courses.");
			return true;
		}
		writeString("Unable to add this course to your courses.");
		return false;
	}

	/**
	 * Removes a course from the user's registration list
	 */
	private boolean removeCourse() {
		if (!studentLoggedIn()) {
			return false;
		}
		Course clientCourse = readCourseFromClient();

		Registration removeReg = studentUser.searchStudentReg(clientCourse);
		if (removeReg != null) {
			studentUser.getStudentRegList().remove(removeReg);
			writeString("Successfully removed this course from your courses.");
			return true;
		}
		writeString("Unable to add this course to your courses: could not find this course in your courses.");
		return false;

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

}
