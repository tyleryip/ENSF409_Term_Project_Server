package com.KerrYip.Model;

/**
 * This class will be used to store administrator information and will have
 * access to methods that will allow them to add or remove courses from the
 * catalog
 * 
 * @author Tyler Yip
 * @author Will Kerr
 * @version 1.0
 * @since 04/09/20
 */
public class Administrator {

	// Used to identify the instance of this class in the SQL database
	private int id;

	// Used for login and logout
	private String username;
	private String password;

	// This is used during logins to ensure that two users cannot access the same
	// user profile at the same time
	private boolean active;

	/**
	 * COnstructor for the class Administrator
	 * 
	 * @param id       the id number used to store the admin in the SQL database
	 * @param username the admin's username
	 * @param password the password used to login
	 */
	public Administrator(int id, String username, String password) {
		this.setID(id);
		this.username = username;
		this.password = password;
		this.active = false;
	}

	// GETTERS and SETTERS
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

}
