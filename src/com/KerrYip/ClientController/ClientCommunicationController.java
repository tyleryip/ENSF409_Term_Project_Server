package com.KerrYip.ClientController;

import com.KerrYip.ClientModel.Course;
import com.KerrYip.ClientModel.Student;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * This class is primarily used to communicate with the server via sockets
 * @author tyleryip
 * @version 1.0
 * @since 04/07/20
 *
 */
public class ClientCommunicationController {

	private Socket aSocket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private ObjectInputStream objectIn;
	private ObjectOutputStream objectOut;
	
	/**
	 * Constructor for the class ClientCommunicationController opens a connection on 
	 * @param serverName the name of the server
	 * @param port the port of the server
	 */
	public ClientCommunicationController(String serverName, int port) {
		try {
			//create socket
			aSocket = new Socket(serverName, port);

			//Socket streams
			socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			socketOut = new PrintWriter((aSocket.getOutputStream()), true);

			//Socket object streams
			//objectIn = new ObjectInputStream(aSocket.getInputStream());
			//objectOut = new ObjectOutputStream(aSocket.getOutputStream());


		} catch (UnknownHostException e) {
			System.err.println("Error: could not find a host with the name: " + serverName);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: I/O socket error");
			e.printStackTrace();
		}
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 * @param instruction The instruction the server will execute
	 * @return The message the server sends back
	 */
	public Course communicationBrowseCatalog(String instruction){
		Course course = null;
		try{
			socketOut.println(instruction);
			socketOut.flush();

			course = (Course)objectIn.readObject();
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return course;
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 * @param instruction The instruction the server will execute
	 * @return The message the server sends back
	 */
	public String communicationStudentLogin(String instruction, String id){
		String message = null;
		try{
			socketOut.println(instruction);
			socketOut.flush();

			socketOut.println(id);
			socketOut.flush();

			System.out.println("before read");
			message = socketIn.readLine();
			System.out.println("message is: " + message);

		}catch(IOException e) {
			e.printStackTrace();
		}
		return message;
	}
	/**
	 * Sends an instruction to the Server and receives the message back
	 * @param instruction The instruction the server will execute
	 * @param course The course the server needs for the instruction
	 * @return The message the server sends back
	 */
	public Course communicationSearchCourse(String instruction, Course course){
		Course courseResult = null;
		try{
			socketOut.println(instruction);
			socketOut.flush();

			objectOut.writeObject(course);
			objectOut.flush();

			courseResult = (Course)objectIn.readObject();
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		return courseResult;
	}

	/**
	 * Sends an instruction to the Server and receives the message back
	 * @param instruction The instruction the server will execute
	 * @param course The course the server needs for the instruction
	 * @return The message the server sends back
	 */
	public String communicationSendStudentAndCourse(String instruction, Course course){
		String message = "";
		try{
				socketOut.println(instruction);
				socketOut.flush();

				objectOut.writeObject(course);
				objectOut.flush();

				message = socketIn.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * Sends an instruction to the Server to quit and closes sockets
	 */
	public void communicationQuit(){
		socketOut.println("QUIT");
		socketOut.flush();
		closeConnection();
	}

	/**
	 * closes all the socket connections
	 */
	private void closeConnection(){
		try{
			socketIn.close();
			socketOut.close();
			objectIn.close();
			objectOut.close();
		}catch(IOException e){
			e.getStackTrace();
		}
	}
}
