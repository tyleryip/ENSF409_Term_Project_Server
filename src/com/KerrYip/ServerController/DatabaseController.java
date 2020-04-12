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

	/**
	 * Creates Student from the data provided. Will not make the Student if data is missing or not found
	 * @param data Data from the database used to make student
	 */
	public void dataToStudent(String data){
		String[] variables = data.split(";");
		try {
			getStudentList().add(new Student(variables[1], Integer.parseInt(variables[0])));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}

	/**
	 * Creates Registration from the data provided. Will not make the Registration if data is missing or not found
	 * @param data Data from the database used to make student
	 */
	public void dataToRegistration(String data){
		String[] variables = data.split(";");
		try {
			Student s = searchStudent(Integer.parseInt(variables[1]));
			CourseOffering co = searchCourseOffering(Integer.parseInt(variables[2]));
			if(co == null || s == null){
				System.err.println("Couldn't find data");
				return;
			}
			getRegistrationList().add(new Registration(Integer.parseInt(variables[0]),s,co,variables[3].charAt(0)));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}

	/**
	 * Creates CourseOffering from the data provided. Will not make the CourseOffering if data is missing or not found
	 * @param data Data from the database used to make student
	 */
	public void dataToCourseOffering(String data){
		String[] variables = data.split(";");
		try {
			Course c = searchCourse(Integer.parseInt(variables[1]));
			if(c == null){
				System.err.println("Couldn't find data");
				return;
			}
			getCourseOfferingList().add(new CourseOffering(Integer.parseInt(variables[0]),c,Integer.parseInt(variables[2]),Integer.parseInt(variables[3])));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}

	/**
	 * Creates Course from the data provided. Will not make the Course if data is missing or not found
	 * @param data Data from the database used to make Course
	 */
	public void dataToCourse(String data){
		String[] variables = data.split(";");
		ArrayList<CourseOffering> courseOfferings = new ArrayList<CourseOffering>();
		CourseOffering co;
		try {
			for(int i = 3; i < variables.length; i++){
				co = searchCourseOffering(Integer.parseInt(variables[i]));
				if(co == null){
					System.err.println("Couldn't find data");
					return;
				}
				courseOfferings.add(co);
			}
			getCourseList().add(new Course(variables[1], Integer.parseInt(variables[2]),Integer.parseInt(variables[0]),courseOfferings));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}

	//Searches for data type with matching ID
	/**
	 * Searches for the Student with the matching ID
	 * @param id The ID of the student we are searching for
	 * @return Returns the student if found, null if not
	 */
	public Student searchStudent(int id){
		for(Student s: getStudentList()){
			if(s.getStudentId() == id){
				return s;
			}
		}
		return null;
	}

	/**
	 * Searches for the Registration with the matching ID
	 * @param id The ID of the registration we are searching for
	 * @return Returns the registration if found, null if not
	 */
	public Registration searchRegistration(int id){
		for(Registration r: getRegistrationList()){
			if(r.getID() == id){
				return r;
			}
		}
		return null;
	}

	/**
	 * Searches for the Course with the matching ID
	 * @param id The ID of the Course we are searching for
	 * @return Returns the Course if found, null if not
	 */
	public Course searchCourse(int id){
		for(Course c: getCourseList()){
			if(c.getID() == id){
				return c;
			}
		}
		return null;
	}

	/**
	 * Searches for the CourseOffering with the matching ID
	 * @param id The ID of the CourseOffering we are searching for
	 * @return Returns the CourseOffering if found, null if not
	 */
	public CourseOffering searchCourseOffering(int id){
		for(CourseOffering co: getCourseOfferingList()){
			if(co.getID() == id){
				return co;
			}
		}
		return null;
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

	public synchronized ArrayList<Registration> getRegistrationList() {
		return registrationList;
	}

	public synchronized ArrayList<CourseOffering> getCourseOfferingList() {
		return courseOfferingList;
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
