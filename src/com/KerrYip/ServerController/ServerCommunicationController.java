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

	private ServerSocket serverSocket;
	private Socket aSocket;
	
	//This thread pool isn't used yet but is a placeholder for milestone
	private ExecutorService pool;
	
	/**
	 * The constructor for class ServerCommunicationsController opens up a port and sets up a thread pool
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
	}
	
	/**
	 * Accept connections from clients
	 */
	public void acceptConnections() {
		while(true) {
			try {
				aSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Error: problem with accepting client socket");
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

	
}
