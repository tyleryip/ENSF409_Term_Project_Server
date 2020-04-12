package com.KerrYip.ServerController;

import java.util.ArrayList;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.Registration;

public class RegistrationController {

    private DatabaseController databaseController;
    private ArrayList<Registration> myRegistrationList;

    /**
     * Constructor for the class RegistrationController
     *
     * @param db the DatabaseController for the student manager to use
     */
    public RegistrationController(DatabaseController db) {
        this.databaseController = db;
        setMyRegistrationList(databaseController.getRegistrationList());
    }
    
    public void removeRegistration(Registration toRemove) {
    	databaseController.getRegistrationList().remove(toRemove);
    }
    
    public void removeAllRegistrations(Course c) {
    	for(int i = 0; i<myRegistrationList.size(); i++) {
    		if(myRegistrationList.get(i).getTheOffering().getTheCourse().getNameNum().equalsIgnoreCase(c.getNameNum())) {
    			myRegistrationList.remove(i);
    		}
    	}
    }
    
    public void syncData() {
    	databaseController.setRegistrationList(myRegistrationList);
    }

    /**
     * Makes new registration, adds it to databaseController and returns it
     * @return Returns the new registration
     */
    public Registration addRegistration(){
        Registration r = new Registration(databaseController.getIncrementRegistrationID());
        myRegistrationList.add(r);
        return r;
    }

    public DatabaseController getDatabaseController() { return databaseController; }
    public void setDatabaseController(DatabaseController databaseController) { this.databaseController = databaseController; }
    public ArrayList<Registration> getMyRegistrationList() { return myRegistrationList; }
    public void setMyRegistrationList(ArrayList<Registration> myRegistrationList) { this.myRegistrationList = myRegistrationList; }

}
