package com.KerrYip.ServerController;

import java.util.ArrayList;

import com.KerrYip.ServerModel.*;

/**
 * This class manages our database, including loading and saving data on
 * students and courses
 * 
 * @author tyleryip
 * @version 1.0
 * @since 04/07/20
 *
 */
public class DatabaseController {

	private ArrayList<Student> studentList;
	private ArrayList<Course> courseList;

	/**
	 * These two array lists are currently inactive, but will be needed for the SQL
	 * database
	 */
	private ArrayList<Registration> registrationList;
	private ArrayList<CourseOffering> courseOfferingList;

	/**
	 * Constructor for class DatabaseController creates ArrayLists and populates
	 * them with simulated data, this data will be replaced with data from the SQL
	 * database in a future milestone
	 */
	public DatabaseController() {
		courseList = new ArrayList<Course>();
		studentList = new ArrayList<Student>();

		// Load the simulated data in
		readCoursesFromDatabase();
		readStudentsFromDatabase();
		registerStudentsInCourses();
	}

	// The following methods create and add the simulated data, in the future we
	// will have to put the SQL data here
	private void readStudentsFromDatabase() {
		studentList.add(new Student("Sara", 1));
		studentList.add(new Student("Sam", 2));
		studentList.add(new Student("Tom", 3));
		studentList.add(new Student("David", 4));
		studentList.add(new Student("John", 5));
		studentList.add(new Student("Jenny", 6));
		studentList.add(new Student("Alex", 7));
		studentList.add(new Student("Megan", 8));
		studentList.add(new Student("Mike", 9));
		studentList.add(new Student("Sugar Tits", 10));
	}

	private void readCoursesFromDatabase() {
		// Simulating courses
		courseList.add(new Course("ENGG", 233));
		courseList.get(0).addOffering(new CourseOffering(1, 100));

		courseList.add(new Course("ENSF", 409));
		courseList.get(1).addOffering(new CourseOffering(1, 100));
		courseList.get(1).addOffering(new CourseOffering(2, 200));

		courseList.add(new Course("PHYS", 259));
		courseList.get(2).addOffering(new CourseOffering(1, 100));
		courseList.get(2).addOffering(new CourseOffering(2, 320));

		courseList.add(new Course("MATH", 211));
		courseList.get(3).addOffering(new CourseOffering(1, 150));
		courseList.get(3).addOffering(new CourseOffering(2, 250));
		courseList.get(3).addOffering(new CourseOffering(3, 270));

		courseList.add(new Course("ENGG", 202));
		courseList.get(4).addOffering(new CourseOffering(1, 120));

		// Added prerequisites
		courseList.get(1).addPreReq(courseList.get(4));
		courseList.get(4).addPreReq(courseList.get(3));
		courseList.get(2).addPreReq(courseList.get(3));
	}

	private void registerStudentsInCourses() {
		Registration r = new Registration();
		r.completeRegistration(studentList.get(0), courseList.get(0).getCourseOfferingAt(0));
		
		r = new Registration();
		r.completeRegistration(studentList.get(1), courseList.get(2).getCourseOfferingAt(1));
		
		r = new Registration();
		r.completeRegistration(studentList.get(2), courseList.get(3).getCourseOfferingAt(1));
	}

	// GETTERS and SETTERS
	public synchronized ArrayList<Student> getStudentList() {
		return studentList;
	}

	public synchronized void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}

	public synchronized ArrayList<Course> getCourseList() {
		return courseList;
	}

	public synchronized void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	public ArrayList<Student> loadStudents() {
		return studentList;
	}

	public ArrayList<Course> loadCourses() {
		return courseList;
	}

}
