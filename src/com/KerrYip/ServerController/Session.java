package com.KerrYip.ServerController;

import java.io.BufferedReader;
import java.io.IOException;
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
			stringIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			stringOut = new PrintWriter(aSocket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Error: problem with setting up input output streams");
			e.printStackTrace();
		}

		this.studentController = studentController;
		this.courseController = courseController;
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
				System.err.println("Error: problem recieving instruciton from client");
				e.printStackTrace();
			}
			executeCommand(command);

		}

	}

	/**
	 * Takes the user's input command and figures out which set of instructions to
	 * execute
	 * 
	 * @param command
	 * @return
	 */
	public void executeCommand(String command) {
		switch (command) {
		case "add course":

			return;

		case "remove course":
			return;

		case "browse courses":
			return;

		case "search for course":
			return;

		default:
			System.err.println("No option available that matched: " + command);
			return;
		}

	}

	/**
	 * This method takes a student and course from the client, looks for the course,
	 * and if successful, adds it to the student and returns the student object to
	 * client
	 */
	public void addCourse() {
		Student clientStudent = null;
		Course clientCourse = null;
		
		try {
			clientStudent = (Student) objectIn.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: the class of this object was not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: I/O error");
			e.printStackTrace();
		}
		
		try {
			clientCourse = (Course)objectIn.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: the class of this object was not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: I/O error");
			e.printStackTrace();
		}
		
		Student theStudent = studentController.searchStudent(clientStudent.getStudentName());
		Course toAdd = courseController.searchCat(clientCourse.getCourseName(), clientCourse.getCourseNum());
		if(theStudent != null && toAdd != null) {
			Registration newReg = new Registration();
			newReg.completeRegistration(theStudent, toAdd.getCourseOfferingAt(0));
			try {
				objectOut.writeObject(theStudent);
			} catch (IOException e) {
				System.err.println("Error: could not write object");
				e.printStackTrace();
			}
		}
	}

}
