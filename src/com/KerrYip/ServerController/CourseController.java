package com.KerrYip.ServerController;

import java.util.ArrayList;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;

/**
 * This class manages everything to do with courses
 * 
 * @author tyleryip
 * @version 1.0
 * @since 04/07/20
 */
public class CourseController {

	private DatabaseController databaseController;

	private ArrayList<Course> myCourseList;

	/**
	 * Constructor for class CourseController, links the course controller to the
	 * database controller
	 * 
	 * @param db the DatabaseController
	 */
	public CourseController(DatabaseController db) {
		this.databaseController = db;
		setMyCourseList(databaseController.getCourseList());
	}

	public void syncData() {
		databaseController.setCourseList(myCourseList);
	}

	/**
	 * Creates a new course offering for a specified course
	 * 
	 * @param c      the course
	 * @param secNum the section number
	 * @param secCap the section capacity
	 */
	public void createCourseOffering(Course c, int secNum, int secCap) {
		if (c != null && secCap > 0) {
			CourseOffering theOffering = new CourseOffering(secNum, secCap);
			c.addOffering(theOffering);
		}
	}

	/**
	 * Searches the database for a specified course
	 * 
	 * @param courseName the name of the course, ex. ENGG
	 * @param courseNum  the number of the course, ex. 233
	 * @return the course you are searching for, or null if not found
	 */
	public Course searchCat(String courseName, int courseNum) {
		for (Course c : myCourseList) {
			if (courseName.equals(c.getCourseName()) && courseNum == c.getCourseNum()) {
				return c;
			}
		}
		displayCourseNotFoundError();
		return null;
	}

	/**
	 * Another search method that uses the primary search method, but enables easier
	 * searching by lumping name and number together
	 * 
	 * @param nameNum the course's full name and number, ex. ENGG 233
	 * @return the course you are searching for, or null if not found
	 */
	public Course searchCat(String nameNum) {
		if (nameNum == null) {
			return null;
		}
		String[] split = nameNum.split(" ");
		return searchCat(split[0], Integer.parseInt(split[1]));
	}

	// Typically, methods that are called from other methods of the class
	// are private and are not exposed for use by other classes.
	// These methods are refereed to as helper methods or utility methods
	private void displayCourseNotFoundError() {
		// TODO Auto-generated method stub
		System.err.println("Course was not found!");
	}

	/**
	 * Adds a course to the course list
	 * 
	 * @param nameNum the name and number of the course you want to add, ex. ENGG
	 *                233
	 * @return the course you added, or null if course already exists
	 */
	public Course addCourse(String nameNum) {
		int newID = databaseController.getIncrementCourseID();
		String[] split = nameNum.split(" ");
		if (searchCat(split[0], Integer.parseInt(split[1])) == null) {
			Course newCourse = new Course(split[0], Integer.parseInt(split[1]), newID);
			myCourseList.add(newCourse);
			return newCourse;
		}
		System.out.println("Error: Course already exists in the catalogue!");
		return null;
	}

	/**
	 * Removes a course from the course list and removes the course listed as a
	 * prereq from all other courses
	 * 
	 * @param nameNum the name and number of the course you want to remove, ex. ENGG
	 *                233
	 */
	public void removeCourse(String nameNum) {
		if (nameNum == null) {
			System.err.println("Error: Invalid input!");
			return;
		}
		if (searchCat(nameNum) == null) { // Check to see if the coruse is in the catalog
			return;
		}
		// We now need to deal with other courses that may have this course listed as a
		// prerequisite
		for (Course c : myCourseList) {
			for (Course p : c.getPreReq()) {
				if (p.getNameNum().contentEquals(nameNum)) {
					c.getPreReq().remove(p);
				}
			}
		}
		// Now that prereqs are dealt with, we can finally remove the course itself from
		// the catalog
		String[] split = nameNum.split(" ");
		myCourseList.remove(searchCat(split[0], Integer.parseInt(split[1])));
	}

	/**
	 * Adds a pre-requisite course to an existing course
	 * 
	 * @param advancedCourse the course that will require a prereq
	 * @param preReqCourse   the prereq course
	 */
	public void addPreRequisite(String advancedCourse, String preReqCourse) {
		if (advancedCourse == null || preReqCourse == null) {
			System.err.println("Error: Invalid input!");
			return;
		}
		String[] split = advancedCourse.split(" ");
		String[] split2 = preReqCourse.split(" ");
		Course checkThis = searchCat(split[0], Integer.parseInt(split[1]));
		if (checkThis != null) {
			Course preReq = new Course(split2[0], Integer.parseInt(split2[1]));
			checkThis.addPreReq(preReq);
			return;
		}
		displayCourseNotFoundError();
	}

	/**
	 * Another variant of the search feature that prints out the results to the
	 * console
	 * 
	 * @param nameNum the name and number of the course, ex. ENGG 233
	 */
	public void searchCourseCatalogue(String nameNum) {
		if (nameNum == null) {
			System.err.println("Error: Invalid input!");
			return;
		}
		String[] split = nameNum.split(" ");
		Course checkThis = searchCat(split[0], Integer.parseInt(split[1]));
		if (checkThis != null) {
			System.out.println("Course was found:");
			System.out.println(checkThis.toString());
			return;
		}
		displayCourseNotFoundError();
		return;
	}

	@Override
	public String toString() {
		String st = "All courses in the catalogue: \n";
		for (Course c : myCourseList) {
			st += c; // This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}

	// GETTERS and SETTERS
	public ArrayList<Course> getCourseList() {
		return myCourseList;
	}

	public ArrayList<Course> getMyCourseList() {
		return myCourseList;
	}

	public void setMyCourseList(ArrayList<Course> myCourseList) {
		this.myCourseList = myCourseList;
	}
	
	public DatabaseController getDatabaseController() {
		return databaseController;
	}

	public void setDatabaseController(DatabaseController databaseController) {
		this.databaseController = databaseController;
	}

}
