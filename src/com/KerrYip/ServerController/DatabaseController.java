package com.KerrYip.ServerController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;
import com.KerrYip.Model.Registration;
import com.KerrYip.Model.Student;

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

	/*
	IDs for all variable types for SQL integration
	 */
	int studentID = 1;
	int courseID = 10000;
	int courseOfferingID = 20000;
	int registrationID = 30000;

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
		studentList.add(new Student("Sara", getIncrementStudentID()));
		studentList.add(new Student("Sam", getIncrementStudentID()));
		studentList.add(new Student("Tom", getIncrementStudentID()));
		studentList.add(new Student("David", getIncrementStudentID()));
		studentList.add(new Student("John", getIncrementStudentID()));
		studentList.add(new Student("Jenny", getIncrementStudentID()));
		studentList.add(new Student("Alex", getIncrementStudentID()));
		studentList.add(new Student("Megan", getIncrementStudentID()));
		studentList.add(new Student("Mike", getIncrementStudentID()));
		studentList.add(new Student("Sugar Tits", getIncrementStudentID()));
	}

	private void readCoursesFromDatabase() {
		// Simulating courses
		courseList.add(new Course("ENGG", 233,getIncrementCourseID()));
		courseList.get(0).addOffering(new CourseOffering(1, 100,getIncrementCourseOfferingID()));

		courseList.add(new Course("ENSF", 409,getIncrementCourseID()));
		courseList.get(1).addOffering(new CourseOffering(1, 100,getIncrementCourseOfferingID()));
		courseList.get(1).addOffering(new CourseOffering(2, 200,getIncrementCourseOfferingID()));

		courseList.add(new Course("PHYS", 259,getIncrementCourseID()));
		courseList.get(2).addOffering(new CourseOffering(1, 100,getIncrementCourseOfferingID()));
		courseList.get(2).addOffering(new CourseOffering(2, 320,getIncrementCourseOfferingID()));

		courseList.add(new Course("MATH", 211,getIncrementCourseID()));
		courseList.get(3).addOffering(new CourseOffering(1, 150,getIncrementCourseOfferingID()));
		courseList.get(3).addOffering(new CourseOffering(2, 250,getIncrementCourseOfferingID()));
		courseList.get(3).addOffering(new CourseOffering(3, 270,getIncrementCourseOfferingID()));

		courseList.add(new Course("ENGG", 202,getIncrementCourseID()));
		courseList.get(4).addOffering(new CourseOffering(1, 120,getIncrementCourseOfferingID()));

		// Added prerequisites
		courseList.get(1).addPreReq(courseList.get(4));
		courseList.get(4).addPreReq(courseList.get(3));
		courseList.get(2).addPreReq(courseList.get(3));
	}

	private void registerStudentsInCourses() {
		Registration r = new Registration();
		r.completeRegistration(studentList.get(1), courseList.get(2).getCourseOfferingAt(1));

		r = new Registration();
		r.completeRegistration(studentList.get(2), courseList.get(3).getCourseOfferingAt(1));
		
		//We register everyone in MATH 211 because we want to drink the tears of depressed engineers (aka we need to test if running course works)
		for(int i = 0; i<studentList.size(); i++) {
			r = new Registration();
			r.completeRegistration(studentList.get(i), courseList.get(3).getCourseOfferingAt(0));
		}
	}
	
	public void writeStudentsToFile(String filename) {
		File output = new File(filename);
		try {
			output.createNewFile();
		} catch(IOException e) {
			System.err.println("Error: Could not create file with filename " + filename);
			e.printStackTrace();
		}
		
		try {
			FileWriter writer = new FileWriter(output);
			for(Student s: studentList) {
				writer.write(s.getStudentId() + ";" + s.getStudentName() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: unknown I/O error");
			e.printStackTrace();
		}
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


	// GETTERS AND SETTERS FOR CLASS IDs
	public int getIncrementStudentID(){
		return studentID++;
	}

	public int getIncrementCourseID(){
		return courseID++;
	}

	public int getIncrementCourseOfferingID(){
		return courseOfferingID++;
	}

	public int getIncrementRegistrationID(){
		return registrationID++;
	}

}
