package com.KerrYip.ClientController;

import com.KerrYip.ClientModel.Course;
import com.KerrYip.ClientModel.Student;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * This class is primarily used to communicate with the server via sockets
 * 
 * @author tyleryip
 * @author kerrmwill
 * @version 1.0
 * @since 04/07/20
 *
 */
public class ClientCommunicationController {

	private Socket aSocket;
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;

	/**
	 * Constructor for the class ClientCommunicationController opens a connection on
	 * 
	 * @param serverName the name of the server
	 * @param port       the port of the server
	 */
	public ClientCommunicationController(String serverName, int port) {
		try {
			// create socket
			aSocket = new Socket(serverName, port);
			toServer = new ObjectOutputStream(aSocket.getOutputStream());
			fromServer = new ObjectInputStream(aSocket.getInputStream());

		} catch (UnknownHostException e) {
			System.err.println("Error: could not find a host with the name: " + serverName);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: I/O socket error");
			e.printStackTrace();
		}
	}

	/**
	 * This method is a helper method to make allow strings to be read from the
	 * output stream of the server more easily, used for receiving message
	 * @return the string sent by the server
	 */
	private String readString() {
		String input = "";
		try {
			input = (String) fromServer.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Error: could not convert the object to a string");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}

	/**
	 * This method is a helper method to allow sending strings to the input stream
	 * of the server to be made more easily, used for sending commands to the server
	 * @param toSend the string to send to server
	 */
	private void writeString(String toSend) {
		try {
			toServer.writeObject(toSend);
		} catch (IOException e) {
			System.err.println("Error: unable to write string to an object");
			e.printStackTrace();
		}
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 * 
	 * @param instruction The instruction the server will execute
	 * @return The message the server sends back
	 */
	public Course communicateBrowseCatalog(String instruction) {
		Course course = null;
		try {
			writeString(instruction);

			course = (Course) fromServer.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return course;
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 * 
	 * @param instruction The instruction the server will execute
	 * @return The message the server sends back
	 */
	public String communicateStudentLogin(String instruction, String id) {
		String message = null;
		writeString(instruction);

		writeString(id);

		message = readString();

		return message;
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 * 
	 * @param instruction The instruction the server will execute
	 * @param course      The course the server needs for the instruction
	 * @return The message the server sends back
	 */
	public Course communicateSearchCourse(String instruction, Course course) {
		Course courseResult = null;
		try {
			writeString(instruction);

			toServer.writeObject(course);
			toServer.flush();

			courseResult = (Course) fromServer.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return courseResult;
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 * 
	 * @param instruction The instruction the server will execute
	 * @param course      The course the server needs for the instruction
	 * @return The message the server sends back
	 */
	public String communicateSendCourse(String instruction, Course course) {
		String message = null;
		try {
			writeString(instruction);

			toServer.writeObject(course);
			toServer.flush();

			message = readString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * Sends an instruction to the Server to quit and closes all socket connections
	 */
	public void communicateQuit() {
		writeString("QUIT");
		closeConnection();
	}

	/**
	 * closes all the socket connections
	 */
	private void closeConnection() {
		try {
			fromServer.close();
			toServer.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
}
