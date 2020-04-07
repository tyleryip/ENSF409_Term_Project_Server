package com.KerrYip.ServerController;

import com.KerrYip.ServerModel.*;
import java.util.ArrayList;

import com.KerrYip.ServerModel.Course;
import com.KerrYip.ServerModel.CourseOffering;

/**
 * This class manages everything to do with courses
 * 
 * @author tyleryip
 * @version 1.0
 * @since 04/07/20
 */
public class CourseController {

	private ArrayList<Course> courseList;

	public CourseController() {
		loadFromDataBase();
	}

	private void loadFromDataBase() {
		// TODO Auto-generated method stub
		DatabaseController db = new DatabaseController();
		setCourseList(db.readCoursesFromDataBase());

	}

	public void createCourseOffering(Course c, int secNum, int secCap) {
		if (c != null && secCap > 0) {
			CourseOffering theOffering = new CourseOffering(secNum, secCap);
			c.addOffering(theOffering);
		}

	}

	public Course searchCat(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) && courseNum == c.getCourseNum()) {
				return c;
			}
		}
		displayCourseNotFoundError();
		return null;
	}

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

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	@Override
	public String toString() {
		String st = "All courses in the catalogue: \n";
		for (Course c : courseList) {
			st += c; // This line invokes the toString() method of Course
			st += "\n";
		}
		return st;
	}

	public Course addCourse(String nameNum) {
		if (nameNum == null) {
			System.err.println("Error: Invalid input!");
			return null;
		}
		String[] split = nameNum.split(" ");
		if (searchCat(split[0], Integer.parseInt(split[1])) == null) {
			Course newCourse = new Course(split[0], Integer.parseInt(split[1]));
			courseList.add(newCourse);
			return newCourse;
		}
		System.out.println("Error: Course already exists in the catalogue!");
		return null;
	}

	public void removeCourse(String nameNum) {
		if (nameNum == null) {
			System.err.println("Error: Invalid input!");
			return;
		}
		String[] split = nameNum.split(" ");
		courseList.remove(searchCat(split[0], Integer.parseInt(split[1])));
	}

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

}
