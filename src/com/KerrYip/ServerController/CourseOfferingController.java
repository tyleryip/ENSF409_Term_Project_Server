package com.KerrYip.ServerController;

import java.util.ArrayList;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;

/**
 * This class manages everything to do with course offerings
 * 
 * @author Tyler Yip
 * @author Will Kerr
 * @version 1.0
 * @since 04/07/20
 *
 */
public class CourseOfferingController {

	private DatabaseController databaseController;
	private CourseController courseController;
	private ArrayList<CourseOffering> myCourseOfferingList;

	/**
	 * Constructor for the class RegistrationController
	 * 
	 * @param db the DatabaseController for the student manager to use
	 * @param courseController the coruseController
	 */
	public CourseOfferingController(DatabaseController db, CourseController courseController) {
		this.databaseController = db;
		this.setCourseController(courseController);
		myCourseOfferingList = databaseController.readCourseOfferingsFromFile(courseController.getCourseList());
		System.out.println("[Course Offering Controller] Systems are online.");
	}

	/**
	 * Removes all course offerings from the database and cache for a specific
	 * course
	 * 
	 * @param c the course to remove course offerings for
	 */
	public void removeAllCourseOfferings(Course c) {
		for (int i = 0; i < myCourseOfferingList.size(); i++) {
			if (myCourseOfferingList.get(i).getTheCourse().getID() == c.getID()) {
				// Delete from the database and then the local cache
				databaseController.deleteCourseOfferingFromDatabase(myCourseOfferingList.get(i));
				myCourseOfferingList.remove(i);
			}
		}
	}

	/**
	 * Adss a new course offering for a course
	 * 
	 * @param c      the course to add the offering to
	 * @param secNum the number for the section
	 * @param secCap the capacity of the section
	 */
	public void addCourseOffering(Course c, int secNum, int secCap) {
		CourseOffering updatedCourseOffering = new CourseOffering(databaseController.getIncrementCourseOfferingID(), c,
				secNum, secCap);
		myCourseOfferingList.add(updatedCourseOffering);
		databaseController.insertCourseOfferingToDatabase(updatedCourseOffering);
	}

	// GETTERS and SETTERS
	public DatabaseController getDatabaseController() {
		return databaseController;
	}

	public void setDatabaseController(DatabaseController databaseController) {
		this.databaseController = databaseController;
	}

	public ArrayList<CourseOffering> getMyCourseOfferingList() {
		return myCourseOfferingList;
	}

	public void setMyCourseOfferingList(ArrayList<CourseOffering> myCourseOfferingList) {
		this.myCourseOfferingList = myCourseOfferingList;
	}

	public CourseController getCourseController() {
		return courseController;
	}

	public void setCourseController(CourseController courseController) {
		this.courseController = courseController;
	}

}
