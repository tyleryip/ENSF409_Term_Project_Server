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
	 * @param db                       the DatabaseController for the student
	 *                                 manager to use
	 * @param studentController
	 * @param courseOfferingController
	 */
	public RegistrationController(DatabaseController db, CourseOfferingController courseOfferingController,
			StudentController studentController) {
		this.databaseController = db;
		this.setCourseOfferingController(courseOfferingController);
		this.setStudentController(studentController);
		myRegistrationList = databaseController.readRegistrationsFromFile(
				courseOfferingController.getMyCourseOfferingList(), studentController.getMyStudentList());
		databaseController.updateRegistrationID(getUpdatedRegistrationID());
		System.out.println("[Registration Controller] Systems are online.");
	}

	/**
	 * Removes a registration from the cache and database
	 * 
	 * @param toRemove the registration to remove
	 */
	public void removeRegistration(Registration toRemove) {
		databaseController.deleteRegistrationFromDatabase(toRemove);
		myRegistrationList.remove(toRemove);
	}

	/**
	 * Removes all registrations from the database and cache
	 * 
	 * @param c the course to remove all registrations from
	 */
	public void removeAllRegistrations(Course c) {
		for (int i = 0; i < myRegistrationList.size(); i++) {
			if (myRegistrationList.get(i).getTheOffering().getTheCourse().getNameNum()
					.equalsIgnoreCase(c.getNameNum())) {
				removeRegistration(myRegistrationList.get(i));
			}
		}
	}

	/**
	 * Updates the databaseController's count of how many registrations exist in the
	 * system
	 * 
	 * @return the ID of the next registration to make
	 */
	public int getUpdatedRegistrationID() {
		return myRegistrationList.get(myRegistrationList.size() - 1).getID() + 1;
	}

	/**
	 * Makes new registration, adds it to databaseController and returns it
	 * 
	 * @return Returns the new registration
	 */
	public Registration addRegistration() {
		Registration r = new Registration(databaseController.getIncrementRegistrationID());
		myRegistrationList.add(r);

		return r;
	}

	/**
	 * Confirms with the database that a registration is complete and should be
	 * saved
	 */
	public void confirmRegistration(Registration r) {
		databaseController.insertRegistrationToDatabase(r);
	}

	// GETTERS and SETTERS
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

	public CourseOfferingController getCourseOfferingController() {
		return courseOfferingController;
	}

	public void setCourseOfferingController(CourseOfferingController courseOfferingController) {
		this.courseOfferingController = courseOfferingController;
	}

	public StudentController getStudentController() {
		return studentController;
	}

	public void setStudentController(StudentController studentController) {
		this.studentController = studentController;
	}

}
