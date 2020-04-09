package com.KerrYip.ServerController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is primarily used to communicate with clients and handle
 * multi-threading tasks
 * 
 * @author tyleryip
 * @version 1.0
 * @since 04/07/20
 *
 */
public class ServerCommunicationController {

	// This is the server's own socket used to accept new connections from clients
	private ServerSocket serverSocket;

	// This is a socket used to hand off client connections to a new thread
	private Socket aSocket;

	// This thread pool isn't used yet but is a placeholder for milestone
	private ExecutorService pool;

	// These are the other controllers for the server side
	private CourseController courseController;
	private StudentController studentController;

	/**
	 * The constructor for class ServerCommunicationsController opens up a port and
	 * sets up a thread pool
	 * 
	 * @param port the port to open the server connection on
	 */
	public ServerCommunicationController(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Error: unable to open a server socket on port " + port);
			e.printStackTrace();
		}
		pool = Executors.newCachedThreadPool();

		// Create the database first so we can let course controller and student
		// controller use it
		DatabaseController databaseController = new DatabaseController();

		courseController = new CourseController(databaseController);
		studentController = new StudentController(databaseController);
	}

	/**
	 * Accept connections from clients and start threads when they connect
	 */
	public void listen() {
		System.out.println("Server is listening for clients...");
		while (true) {
			try {
				aSocket = serverSocket.accept();
				System.out.println("Connection accepted by server!");

				// Create a new session for the new client that joined
				Session newSession = new Session(aSocket, courseController, studentController);

				// Add client to the thread pool and execute
				pool.execute(newSession);
			} catch (IOException e) {
				System.err.println("Error: problems accepting client socket");
				e.printStackTrace();
			}
		}
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public Socket getaSocket() {
		return aSocket;
	}

	public void setaSocket(Socket aSocket) {
		this.aSocket = aSocket;
	}

	public ExecutorService getPool() {
		return pool;
	}

	public void setPool(ExecutorService pool) {
		this.pool = pool;
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

}
