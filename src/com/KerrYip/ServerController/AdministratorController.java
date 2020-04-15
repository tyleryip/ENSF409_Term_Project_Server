package com.KerrYip.ServerController;

import com.KerrYip.Model.Administrator;
import com.KerrYip.Model.Course;
import com.KerrYip.Model.Student;

import java.util.ArrayList;
import java.util.Iterator;

public class AdministratorController {
    private DatabaseController databaseController;
    private ArrayList<Administrator> myAdminList;

    /**
     * Constructor for the class StudentController
     *
     * @param db the DatabaseController for the student manager to use
     */
    public AdministratorController(DatabaseController db) {
        this.databaseController = db;
        myAdminList = databaseController.readAdminsFromFile();
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
