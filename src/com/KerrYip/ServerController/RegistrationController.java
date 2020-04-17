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
	 * @param studentController	the student controller to use
	 * @param courseOfferingController the course offering controller to use
	 */
	public RegistrationController(DatabaseController db, CourseOfferingController courseOfferingController,
			StudentController studentController) {
		this.databaseController = db;
		this.setCourseOfferingController(courseOfferingController);
		this.setStudentController(studentController);
		myRegistrationList = databaseController.readRegistrationsFromFile(
				courseOfferingController.getMyCourseOfferingList(), studentController.getMyStudentList());
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
				databaseController.deleteRegistrationFromDatabase(myRegistrationList.get(i));
				removeRegistration(myRegistrationList.get(i));
			}
		}
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
	 * @param r the registration to confirm
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
