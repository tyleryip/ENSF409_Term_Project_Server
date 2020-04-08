package com.KerrYip.ServerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.KerrYip.ServerModel.Course;
import com.KerrYip.ServerModel.Registration;
import com.KerrYip.ServerModel.Student;

/**
 * This class implements the runnable interface so that user can interact with
 * the application during runtime
 * 
 * @author tyleryip
 *
 */
public class Session implements Runnable {

	// These deal with sending and receiving data and instructions to and from the
	// client
	private PrintWriter stringOut;
	private BufferedReader stringIn;

	// These I/O streams deal with sending Student objects back and forth between
	// the client
	private ObjectInputStream objectIn;
	private ObjectOutputStream objectOut;

	// These controllers will be assigned to each session, so all sessions use the
	// same controllers
	private CourseController courseController;
	private StudentController studentController;

	private Student studentUser;

	/**
	 * Constructor for session connects I/O
	 * 
	 * @param aSocket
	 */
	public Session(Socket aSocket, CourseController courseController, StudentController studentController) {
		// Set up instruction I/O
		try {
			stringIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			stringOut = new PrintWriter(aSocket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Error: problem with setting up input output streams");
			e.printStackTrace();
		}

		// Set up object I/O
		try {
			objectIn = new ObjectInputStream(aSocket.getInputStream());
			objectOut = new ObjectOutputStream(aSocket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Error: problem with setting up input output streams");
			e.printStackTrace();
		}

		this.studentController = studentController;
		this.courseController = courseController;

		this.studentUser = null;
	}

	@Override
	public void run() {
		// This string is used to accept commands from the client and parse them into
		// instructions
		String command = "";

		while (!command.contentEquals("QUIT")) {
			try {
				command = stringIn.readLine();
			} catch (IOException e) {
				System.err.println("Error: problem recieving instruction from client");
				e.printStackTrace();
			}
			executeCommand(command);

		}

		// Close all communication channels in the event that the user quits
		try {
			stringOut.close();
			stringIn.close();
			objectOut.close();
			objectIn.close();
		} catch (IOException e) {
			System.err.println("Error: unable to close communication sockets");
			e.printStackTrace();
		}

	}

	/**
	 * Takes the user's input command and figures out which set of instructions to
	 * execute
	 * 
	 * @param command a string telling the server which command they want to execute
	 */
	private void executeCommand(String command) {
		switch (command) {
		case "student login":
			studentLogin();
			return;

		case "add course":
			addCourse();
			return;

		case "remove course":
			removeCourse();
			return;

		case "browse courses":
			browseCourses();
			return;

		case "search for course":
			searchForCourse();
			return;

		default:
			System.err.println("No option available that matched: " + command);
			return;
		}

	}

	/**
	 * This method allows student users to log into the system and the server to
	 * select a student to manipulate
	 */
	private void studentLogin() {
		int checkID = -1;
		try {
			checkID = Integer.parseInt(stringIn.readLine());
		} catch (IOException e) {
			System.err.println("Error: invalid ID format");
			e.printStackTrace();
		}
		studentUser = studentController.searchStudent(checkID);
		if (studentUser != null) {
			stringOut.println("login successful");
			return;
		}
		stringOut.println("login failed");
		return;
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

	private void searchForCourse() {
		if (!studentLoggedIn()) {
			return;
		}
		Course clientCourse = readCourseFromClient();
		clientCourse = courseController.searchCat(clientCourse.getCourseName(), clientCourse.getCourseNum());
		if (clientCourse != null) {
			try {
				objectOut.writeObject(clientCourse);
				return;
			} catch (IOException e) {
				System.err.println("Error: unable to write course to output stream");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sends each course in the catalogue back to the user
	 */
	private void browseCourses() {
		if (!studentLoggedIn()) {
			return;
		}
		for (Course c : courseController.getCourseList()) {
			try {
				objectOut.writeObject(c);
				return;
			} catch (IOException e) {
				System.err.println("Error: unable to write course to output stream");
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method takes a student and course from the client, looks for the course,
	 * and if successful, adds it to the student and returns the student object to
	 * client
	 */
	private void addCourse() {
		if (!studentLoggedIn()) {
			return;
		}
		Course clientCourse = readCourseFromClient();

		clientCourse = courseController.searchCat(clientCourse.getCourseName(), clientCourse.getCourseNum());
		if (clientCourse != null) {
			Registration newReg = new Registration();
			newReg.completeRegistration(studentUser, clientCourse.getCourseOfferingAt(0));
			stringOut.println("Sucessfullyy added this course to your courses.");
			return;
		}
		stringOut.println("Unable to add this course to your courses.");
	}

	/**
	 * Removes a course from the user's registration list
	 */
	private void removeCourse() {
		if (!studentLoggedIn()) {
			return;
		}
		Course clientCourse = readCourseFromClient();

		Registration removeReg = studentUser.searchStudentReg(clientCourse);
		if (removeReg != null) {
			studentUser.getStudentRegList().remove(removeReg);
			stringOut.write("Successfully removed this course from your courses.");
		}
		stringOut.println("Unable to add this course to your courses: could not find this course in your courses.");

	}

	/**
	 * A helper function to check read course objects from the client socket
	 * 
	 * @return a Course if the course exists, null otherwise
	 */
	private Course readCourseFromClient() {
		Course clientCourse = null;
		try {
			clientCourse = (Course) objectIn.readObject();
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
