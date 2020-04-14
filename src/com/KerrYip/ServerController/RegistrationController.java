package com.KerrYip.ServerController;

import java.util.ArrayList;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.Registration;

/**
 * This class manages everything to do with registrations
 * 
 * @author Tyler Yip
 * @author Will Kerr
 * @version 1.0
 * @since 04/07/20
 *
 */
public class RegistrationController {

	private DatabaseController databaseController;
	private CourseOfferingController courseOfferingController;
	private StudentController studentController;
	
	private ArrayList<Registration> myRegistrationList;

	/**
	 * Constructor for the class RegistrationController
	 *
	 * @param db the DatabaseController for the student manager to use
	 * @param studentController 
	 * @param courseOfferingController 
	 */
	public RegistrationController(DatabaseController db, CourseOfferingController courseOfferingController, StudentController studentController) {
		this.databaseController = db;
		this.courseOfferingController = courseOfferingController;
		this.studentController = studentController;
		myRegistrationList = databaseController.readRegistrationsFromFile(courseOfferingController.getMyCourseOfferingList(), studentController.getMyStudentList());
		databaseController.updateRegistrationID(getUpdatedRegistrationID());
	}

	public void removeRegistration(Registration toRemove) {
		databaseController.deleteRegistrationFromDatabase(toRemove);
		myRegistrationList.remove(toRemove);
	}

	public void removeAllRegistrations(Course c) {
		for (int i = 0; i < myRegistrationList.size(); i++) {
			if (myRegistrationList.get(i).getTheOffering().getTheCourse().getNameNum()
					.equalsIgnoreCase(c.getNameNum())) {
				//Delete from the database and then the local cache
				databaseController.deleteRegistrationFromDatabase(myRegistrationList.get(i));
				myRegistrationList.remove(i);
			}
		}
	}
	
	public int getUpdatedRegistrationID() {
		return myRegistrationList.get(myRegistrationList.size() -1).getID() + 1;
	}

	/**
	 * Makes new registration, adds it to databaseController and returns it
	 * 
	 * @return Returns the new registration
	 */
	public Registration addRegistration() {
		Registration r = new Registration(databaseController.getIncrementRegistrationID());
		myRegistrationList.add(r);
		databaseController.insertRegistrationToDatabase(r);
		return r;
	}

	public DatabaseController getDatabaseController() {
		return databaseController;
	}

	public void setDatabaseController(DatabaseController databaseController) {
		this.databaseController = databaseController;
	}

	public ArrayList<Registration> getMyRegistrationList() {
		return myRegistrationList;
	}

	public void setMyRegistrationList(ArrayList<Registration> myRegistrationList) {
		this.myRegistrationList = myRegistrationList;
	}

}
