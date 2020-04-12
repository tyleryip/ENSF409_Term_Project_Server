package com.KerrYip.ServerController;

import com.KerrYip.Model.Registration;

import java.util.ArrayList;

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

    /**
     * Makes new registration, adds it to databaseController and returns it
     * @return Returns the new registration
     */
    public Registration makeRegistration(){
        Registration r = new Registration(databaseController.getIncrementRegistrationID());
        databaseController.getRegistrationList().add(r);
        return r;
    }

    public DatabaseController getDatabaseController() { return databaseController; }
    public void setDatabaseController(DatabaseController databaseController) { this.databaseController = databaseController; }
    public ArrayList<Registration> getMyRegistrationList() { return myRegistrationList; }
    public void setMyRegistrationList(ArrayList<Registration> myRegistrationList) { this.myRegistrationList = myRegistrationList; }

}
