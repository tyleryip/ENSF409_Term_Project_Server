package com.KerrYip.ServerController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
		registrationList = new ArrayList<Registration>();
		courseOfferingList = new ArrayList<CourseOffering>();

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
		courseOfferingList.add(new CourseOffering(1, 100,getIncrementCourseOfferingID()));
		courseList.get(0).addOffering(courseOfferingList.get(0));

		courseList.add(new Course("ENSF", 409,getIncrementCourseID()));
		courseOfferingList.add(new CourseOffering(1, 100,getIncrementCourseOfferingID()));
		courseList.get(1).addOffering(courseOfferingList.get(1));
		courseOfferingList.add(new CourseOffering(2, 200,getIncrementCourseOfferingID()));
		courseList.get(1).addOffering(courseOfferingList.get(2));

		courseList.add(new Course("PHYS", 259,getIncrementCourseID()));
		courseOfferingList.add(new CourseOffering(1, 100,getIncrementCourseOfferingID()));
		courseList.get(2).addOffering(courseOfferingList.get(3));
		courseOfferingList.add(new CourseOffering(2, 320,getIncrementCourseOfferingID()));
		courseList.get(2).addOffering(courseOfferingList.get(4));

		courseList.add(new Course("MATH", 211,getIncrementCourseID()));
		courseOfferingList.add(new CourseOffering(1, 150,getIncrementCourseOfferingID()));
		courseList.get(3).addOffering(courseOfferingList.get(5));
		courseOfferingList.add(new CourseOffering(2, 250,getIncrementCourseOfferingID()));
		courseList.get(3).addOffering(courseOfferingList.get(6));
		courseOfferingList.add(new CourseOffering(3, 270,getIncrementCourseOfferingID()));
		courseList.get(3).addOffering(courseOfferingList.get(7));

		courseList.add(new Course("ENGG", 202,getIncrementCourseID()));
		courseOfferingList.add(new CourseOffering(1, 120,getIncrementCourseOfferingID()));
		courseList.get(4).addOffering(courseOfferingList.get(8));

		// Added prerequisites
		courseList.get(1).addPreReq(courseList.get(4));
		courseList.get(4).addPreReq(courseList.get(3));
		courseList.get(2).addPreReq(courseList.get(3));
	}

	private void registerStudentsInCourses() {
		//We register everyone in MATH 211 because we want to drink the tears of depressed engineers (aka we need to test if running course works)
		Registration r;
		for(int i = 0; i<studentList.size(); i++) {
			r = new Registration(getIncrementRegistrationID());
			r.completeRegistration(studentList.get(i), courseList.get(3).getCourseOfferingAt(0));
			registrationList.add(r);
		}

		r = new Registration(getIncrementRegistrationID());
		r.completeRegistration(studentList.get(1), courseList.get(2).getCourseOfferingAt(1));
		registrationList.add(r);

		r = new Registration(getIncrementRegistrationID());
		r.completeRegistration(studentList.get(2), courseList.get(3).getCourseOfferingAt(1));
		registrationList.add(r);
	}

	/**
	 * Test method for writing the students to a file
	 * @param filename the filename
	 */
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
				writer.write(s.toData() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: unknown I/O error");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for reading the Students from a file
	 * @param filename the filename
	 */
	public void readStudentsFromFile(String filename) {
		File input = new File(filename);
		try {
			Scanner scan = new Scanner(input);
			String s;
			while (scan.hasNext()) {
				s = scan.nextLine();
				dataToStudent(s);
			}
			scan.close();
		} catch (IOException e) {
			System.err.println("Error: Could not read file with filename " + filename);
			e.printStackTrace();
		}
	}


	/**
	 * Test method for writing the courses to a file
	 * @param filename the filename
	 */
	public void writeCoursesToFile(String filename) {
		File output = new File(filename);
		try {
			output.createNewFile();
		} catch(IOException e) {
			System.err.println("Error: Could not create file with filename " + filename);
			e.printStackTrace();
		}
		
		try {
			FileWriter writer = new FileWriter(output);
			for(Course c: courseList) {
				writer.write(c.toData() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: unknown I/O error");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for reading the Courses from a file
	 * @param filename the filename
	 */
	public void readCoursesFromFile(String filename) {
		File input = new File(filename);
		try {
			Scanner scan = new Scanner(input);
			String s;
			while (scan.hasNext()) {
				s = scan.nextLine();
				dataToCourse(s);
			}
			scan.close();
		} catch (IOException e) {
			System.err.println("Error: Could not read file with filename " + filename);
			e.printStackTrace();
		}
		input = new File(filename);
		try {
			Scanner scan = new Scanner(input);
			String s;
			while (scan.hasNext()) {
				s = scan.nextLine();
				dataToCoursePreReqs(s);
			}
			scan.close();
		} catch (IOException e) {
			System.err.println("Error: Could not read file with filename " + filename);
			e.printStackTrace();
		}
	}

	/**
	 * Test method for writing the course offerings to a file
	 * @param filename the filename
	 */
	public void writeCourseOfferingsToFile(String filename) {
		File output = new File(filename);
		try {
			output.createNewFile();
		} catch(IOException e) {
			System.err.println("Error: Could not create file with filename " + filename);
			e.printStackTrace();
		}
		
		try {
			FileWriter writer = new FileWriter(output);
			for(CourseOffering co: courseOfferingList) {
				writer.write(co.toData() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: unknown I/O error");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for reading the Course Offerings from a file
	 * @param filename the filename
	 */
	public void readCourseOfferingsFromFile(String filename) {
		File input = new File(filename);
		try {
			Scanner scan = new Scanner(input);
			String s;
			while (scan.hasNext()) {
				s = scan.nextLine();
				dataToCourseOffering(s);
			}
			scan.close();
		} catch (IOException e) {
			System.err.println("Error: Could not read file with filename " + filename);
			e.printStackTrace();
		}
	}

	/**
	 * Test method for writing the registrations to a file
	 * @param filename the filename
	 */
	public void writeRegistrationsToFile(String filename) {
		File output = new File(filename);
		try {
			output.createNewFile();
		} catch(IOException e) {
			System.err.println("Error: Could not create file with filename " + filename);
			e.printStackTrace();
		}
		
		try {
			FileWriter writer = new FileWriter(output);
			for(Registration r: registrationList) {
				writer.write(r.toData() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: unknown I/O error");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for reading the registrations from a file
	 * @param filename the filename
	 */
	public void readRegistrationsFromFile(String filename) {
		File input = new File(filename);
		try {
			Scanner scan = new Scanner(input);
			String s;
			while (scan.hasNext()) {
				s = scan.nextLine();
				dataToRegistration(s);
			}
			scan.close();
		} catch (IOException e) {
			System.err.println("Error: Could not read file with filename " + filename);
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
			int s = searchStudent(Integer.parseInt(variables[1]));
			int co = searchCourseOffering(Integer.parseInt(variables[2]));
			if(co == -1 || s == -1){
				System.err.println("Couldn't find data");
				return;
			}
			getRegistrationList().add(new Registration(Integer.parseInt(variables[0]),getStudentList().get(s),getCourseOfferingList().get(co),variables[3].charAt(0)));
			getStudentList().get(s).getStudentRegList().add(getRegistrationList().get(getRegistrationList().size()-1));
			getCourseOfferingList().get(co).getOfferingRegList().add(getRegistrationList().get(getRegistrationList().size()-1));
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
			int c = searchCourse(Integer.parseInt(variables[1]));
			if(c == -1){
				System.err.println("Couldn't find data");
				return;
			}
			getCourseOfferingList().add(new CourseOffering(Integer.parseInt(variables[0]),getCourseList().get(c),Integer.parseInt(variables[2]),Integer.parseInt(variables[3])));
			getCourseList().get(c).getOfferingList().add(getCourseOfferingList().get(getCourseOfferingList().size()-1));
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
		try{
			getCourseList().add(new Course(variables[1], Integer.parseInt(variables[2]), Integer.parseInt(variables[0])));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		}
	}

	/**
	 * Adds Prereqs to all the courses that have been added
	 * @param data Data from the database used to make the Prereqs for Course
	 */
	public void dataToCoursePreReqs(String data) {
		String[] variables = data.split(";");
		ArrayList<Course> prereqList = new ArrayList<Course>();
		Course tempCourse;
		try {
			for (int i = 3; i < variables.length; i++) {
				tempCourse = getCourseList().get(searchCourse(Integer.parseInt(variables[i])));
				if (tempCourse == null) {
					System.err.println("Couldn't find data");
					return;
				}
				prereqList.add(tempCourse);
			}
			int i = (searchCourse(Integer.parseInt(variables[0])));
			if(i != -1){
				return;
			}
			getCourseList().get(i).setPreReq(prereqList);
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
	 * @return Returns the index that the student is found if found, -1 if not found
	 */
	public int searchStudent(int id){
		for(int i = 0; i < getStudentList().size(); i++){
			if(id == getStudentList().get(i).getStudentId()){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Searches for the Registration with the matching ID
	 * @param id The ID of the registration we are searching for
	 * @return Returns the index that the registration is found if found, -1 if not found
	 */
	public int searchRegistration(int id){
		for(int i = 0; i < getRegistrationList().size(); i++){
			if(id == getRegistrationList().get(i).getID()){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Searches for the Course with the matching ID
	 * @param id The ID of the Course we are searching for
	 * @return Returns the index that the Course is found if found, -1 if not found
	 */
	public int searchCourse(int id){
		for(int i = 0; i < getCourseList().size(); i++){
			if(id == getCourseList().get(i).getID()){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Searches for the CourseOffering with the matching ID
	 * @param id The ID of the CourseOffering we are searching for
	 * @return Returns the index that the CourseOffering is found if found, -1 if not found
	 */
	public int searchCourseOffering(int id){
		for(int i = 0; i < getCourseOfferingList().size(); i++){
			if(id == getCourseOfferingList().get(i).getID()){
				return i;
			}
		}
		return -1;
	}


	// GETTERS and SETTERS
	public synchronized ArrayList<Student> getStudentList() { return studentList; }

	public synchronized void setStudentList(ArrayList<Student> studentList) { this.studentList = studentList; }

	public synchronized ArrayList<Course> getCourseList() { return courseList; }

	public synchronized void setCourseList(ArrayList<Course> courseList) { this.courseList = courseList; }

	public synchronized ArrayList<Registration> getRegistrationList() { return registrationList; }
	
	public synchronized void setRegistrationList(ArrayList<Registration> registrationList) {this.registrationList = registrationList; }

	public synchronized ArrayList<CourseOffering> getCourseOfferingList() { return courseOfferingList; }
	
	public synchronized void setCourseOfferingList(ArrayList<CourseOffering> courseOfferingList)  {this.courseOfferingList = courseOfferingList; }

	public ArrayList<Student> loadStudents() { return studentList; }

	public ArrayList<Course> loadCourses() { return courseList; }


	// GETTERS AND SETTERS FOR CLASS IDS
	public int getIncrementStudentID(){ return studentID++; }

	public int getIncrementCourseID(){ return courseID++; }

	public int getIncrementCourseOfferingID(){ return courseOfferingID++; }

	public int getIncrementRegistrationID(){ return registrationID++; }

	public static void main(String [] args) {
		DatabaseController dbc = new DatabaseController();
		dbc.writeStudentsToFile("students.txt");
		dbc.writeCoursesToFile("courses.txt");
		dbc.writeCourseOfferingsToFile("courseofferings.txt");
		dbc.writeRegistrationsToFile("regsitrations.txt");
	}
	
}
