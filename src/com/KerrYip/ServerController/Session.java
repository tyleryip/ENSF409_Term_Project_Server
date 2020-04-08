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
			addCourse();
			return;

		case "remove course":
			removeCourse();
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
		Student clientStudent = readStudentFromClient();
		Course clientCourse = readCourseFromClient();
		
		clientStudent = studentController.searchStudent(clientStudent.getStudentName());
		clientCourse = courseController.searchCat(clientCourse.getCourseName(), clientCourse.getCourseNum());
		if(clientStudent != null && clientCourse != null) {
			Registration newReg = new Registration();
			newReg.completeRegistration(clientStudent, clientCourse.getCourseOfferingAt(0));
			stringOut.write("Sucessfullyy added this course to your courses.");
			return;
		}
		stringOut.write("Unable to add this course to your courses.");
	}
	
	public void removeCourse() {
		Student clientStudent = readStudentFromClient();
		Course clientCourse = readCourseFromClient();
		
		clientStudent = studentController.searchStudent(clientStudent.getStudentName());
		if(clientStudent != null) {
			Registration removeReg = clientStudent.searchStudentReg(clientCourse);
				if(removeReg != null) {
					clientStudent.getStudentRegList().remove(removeReg);
					stringOut.write("Successfully removed this course from your courses.");
				}
			stringOut.write("Unable to add this course to your courses: could not find this course in your courses.");
		}
		stringOut.write("Unable to remove this course from your courses.");
	}
	
	public Course readCourseFromClient(){
		Course clientCourse = null;
		try {
			clientCourse = (Course)objectIn.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: the class of this object was not found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: I/O error");
			e.printStackTrace();
		}
		return clientCourse;
	}
	
	public Student readStudentFromClient() {
		Student clientStudent = null;
			try {
				clientStudent = (Student) objectIn.readObject();
			} catch (ClassNotFoundException e) {
				System.err.println("Error: the class of this object was not found");
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Error: I/O error");
				e.printStackTrace();
			}
		return clientStudent;
	}
	


}
