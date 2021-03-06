package com.KerrYip.ServerApplication;

import com.KerrYip.ServerController.ServerCommunicationController;

/**
 * This class is meant to be run to start the server side of the client server
 * of the registration application
 * 
 * @author Tyler Yip
 * @author Will Kerr
 * @version 1.0
 * @since 04/07/20
 */
public class RegistrationAppServer {

	public static void main(String[] args) {

		ServerCommunicationController sCommController = new ServerCommunicationController(9898);
		sCommController.listen();
	}
}
