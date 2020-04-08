package com.KerrYip.ServerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class implements the runnable interface so that user can interact with the application during runtime
 * @author tyleryip
 *
 */
public class Session implements Runnable{

	//These deal with sending and receiving data and instructions to and from the client
	private PrintWriter stringOut;
	private BufferedReader stringIn;
	
	//These I/O streams deal with sending Student objects back and forth between the client
	private ObjectInputStream objectIn;
	private ObjectOutputStream objectOut;
	
	/**
	 * Constructor for session connects I/O
	 * @param aSocket
	 */
	public Session(Socket aSocket) {
		//Set up instruction I/O
		try {
			stringIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			stringOut = new PrintWriter(aSocket.getOutputStream());			
		} catch (IOException e) {
			System.err.println("Error: problem with setting up input output streams");
			e.printStackTrace();
		}
		
		//Set up object I/O
		try {
			stringIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
			stringOut = new PrintWriter(aSocket.getOutputStream());
		} catch (IOException e) {
			System.err.println("Error: problem with setting up input output streams");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		//This string is used to accept commands from the client and parse them into instructions
		String command = "";
		
		while(true) {
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
	 * Takes the user's input command and figures out which set of instructions to execute
	 * @param command
	 * @return
	 */
	public void executeCommand(String command) {
		switch(command) {
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
	

}
