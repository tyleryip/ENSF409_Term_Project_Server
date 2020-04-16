package com.KerrYip.ServerController;

import java.util.ArrayList;
import java.util.Iterator;

import com.KerrYip.Model.Administrator;

/**
 * This class controls and manages a list of administrator users on the system
 * 
 * @author Tyler Yip
 * @author Will Kerr
 * @version 1.0
 * @since 04/15/20
 *
 */
public class AdministratorController {

	private DatabaseController databaseController;
	private ArrayList<Administrator> myAdminList;

	/**
	 * Constructor for the class StudentController
	 *
	 * @param db the DatabaseController for the administrator controller to use
	 */
	public AdministratorController(DatabaseController db) {
		this.databaseController = db;
		myAdminList = databaseController.readAdminsFromFile();
		System.out.println("[Administrator Controller] Systems are online.");
	}

	/**
	 * Searches for a admin with a matching username
	 *
	 * @param username the name of desired admin
	 * @return the student with a matching name
	 */
	public Administrator searchAdmin(String username) {
		Iterator<Administrator> itr = myAdminList.iterator();
		while (itr.hasNext()) {
			Administrator check = itr.next();
			if (check.getUsername().equalsIgnoreCase(username)) {
				return check;
			}
		}
		System.err.println("Could not find admin with username: " + username);
		return null;

	}

	// GETTERS and SETTERS
	public DatabaseController getDatabaseController() {
		return databaseController;
	}

	public void setDatabaseController(DatabaseController databaseController) {
		this.databaseController = databaseController;
	}

	public ArrayList<Administrator> getMyAdminList() {
		return myAdminList;
	}

	public void setMyAdminList(ArrayList<Administrator> myAdminList) {
		this.myAdminList = myAdminList;
	}

}
