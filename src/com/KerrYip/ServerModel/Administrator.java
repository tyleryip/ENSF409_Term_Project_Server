package com.KerrYip.ServerModel;

/**
 * This class will be used to store administrator information and will have
 * access to methods that will allow them to add or remove courses from the
 * catalog
 * 
 * @author tyleryip
 * @version 1.0
 * @since 04/09/20
 */
public class Administrator {

	private String password;
	private boolean active;

	public Administrator() {
		this.password = "admin";
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

}
