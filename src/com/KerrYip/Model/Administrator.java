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

	private int id;
	private String username;
	private String password;
	private boolean active;

	public Administrator(int id, String username, String password) {
		this.id = id;
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

}
