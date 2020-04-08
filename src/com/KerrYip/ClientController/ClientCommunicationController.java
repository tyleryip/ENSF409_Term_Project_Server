package com.KerrYip.ClientController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
	private BufferedReader stdIn;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	/**
	 * Constructor for the class ClientCommunicationController opens a connection on 
	 * @param serverName the name of the server
	 * @param port the port of the server
	 */
	public ClientCommunicationController(String serverName,int port) {
		try {
			aSocket = new Socket(serverName, port);
		} catch (UnknownHostException e) {
			System.err.println("Error: could not find a host with the name: " + serverName);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: I/O socket error");
			e.printStackTrace();
		}
		
	}
}
