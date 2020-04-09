package com.KerrYip.ClientApplication;

import java.util.Scanner;

import com.KerrYip.ClientController.ClientCommunicationController;
import com.KerrYip.ClientController.ClientGUIController;
import com.KerrYip.ServerController.CourseController;
import com.KerrYip.ServerController.StudentController;
import com.KerrYip.ServerModel.Course;
import com.KerrYip.ServerModel.CourseOffering;
import com.KerrYip.ServerModel.Registration;
import com.KerrYip.ServerModel.Student;

/**
 * This class is meant to be the main application class for the client side of
 * the client server application
 * 
 * @author tyleryip
 * @version 2.0
 * @since 04/07/20
 *
 */
public class RegistrationAppClient {

	public static void main(String[] args) {
		ClientGUIController cgc = new ClientGUIController(600, 400);
	}
}