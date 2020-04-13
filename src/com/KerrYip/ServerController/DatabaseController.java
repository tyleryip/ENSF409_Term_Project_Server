package com.KerrYip.ServerController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;
import com.KerrYip.Model.Registration;
import com.KerrYip.Model.Student;

/**
 * This class manages our database, including loading and saving data on
 * students and courses
 * 
 * @author Tyler Yip
 * @author Will Kerr
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
	 * IDs for all variable types for SQL integration
	 */
	int studentID = 1;
	int courseID = 10000;
	int courseOfferingID = 20000;
	int registrationID = 30000;
	
	private Connection myConn;
	private Properties properties;
	private PreparedStatement pStat;
	private ResultSet myRs;
	

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

		properties = new Properties();
		properties.setProperty("user", "ENSF409");
		properties.setProperty("password", "ENSF_409");
		properties.setProperty("useSSL", "false");
		properties.setProperty("allowsPublicKeyRetrieval", "true");
		
		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_app_database", properties);
			System.out.println("[Database Controller] Connection with SQL Database was successfully established!");
		} catch(SQLException e){
			System.err.println("Error: Unknown SQL error has occured");
			e.printStackTrace();
		}
		
		readStudentsFromFile();
		readCoursesFromFile();
		readCourseOfferingsFromFile();
		readRegistrationsFromFile();
		readPreReqFromFile();

		updateIDs();
	}

	public void updateIDs() {
		studentID = studentList.get(studentList.size() - 1).getStudentId() + 1;
		courseID = courseList.get(courseList.size() - 1).getID() + 1;
		courseOfferingID = courseOfferingList.get(courseOfferingList.size() - 1).getID() + 1;
		registrationID = registrationList.get(registrationList.size() - 1).getID() + 1;
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
		courseList.add(new Course("ENGG", 233, getIncrementCourseID()));
		courseOfferingList.add(new CourseOffering(1, 100, getIncrementCourseOfferingID()));
		courseList.get(0).addOffering(courseOfferingList.get(0));

		courseList.add(new Course("ENSF", 409, getIncrementCourseID()));
		courseOfferingList.add(new CourseOffering(1, 100, getIncrementCourseOfferingID()));
		courseList.get(1).addOffering(courseOfferingList.get(1));
		courseOfferingList.add(new CourseOffering(2, 200, getIncrementCourseOfferingID()));
		courseList.get(1).addOffering(courseOfferingList.get(2));

		courseList.add(new Course("PHYS", 259, getIncrementCourseID()));
		courseOfferingList.add(new CourseOffering(1, 100, getIncrementCourseOfferingID()));
		courseList.get(2).addOffering(courseOfferingList.get(3));
		courseOfferingList.add(new CourseOffering(2, 320, getIncrementCourseOfferingID()));
		courseList.get(2).addOffering(courseOfferingList.get(4));

		courseList.add(new Course("MATH", 211, getIncrementCourseID()));
		courseOfferingList.add(new CourseOffering(1, 150, getIncrementCourseOfferingID()));
		courseList.get(3).addOffering(courseOfferingList.get(5));
		courseOfferingList.add(new CourseOffering(2, 250, getIncrementCourseOfferingID()));
		courseList.get(3).addOffering(courseOfferingList.get(6));
		courseOfferingList.add(new CourseOffering(3, 270, getIncrementCourseOfferingID()));
		courseList.get(3).addOffering(courseOfferingList.get(7));

		courseList.add(new Course("ENGG", 202, getIncrementCourseID()));
		courseOfferingList.add(new CourseOffering(1, 120, getIncrementCourseOfferingID()));
		courseList.get(4).addOffering(courseOfferingList.get(8));

		// Added prerequisites
		courseList.get(1).addPreReq(courseList.get(4));
		courseList.get(4).addPreReq(courseList.get(3));
		courseList.get(2).addPreReq(courseList.get(3));
	}

	private void registerStudentsInCourses() {
		// We register everyone in MATH 211 because we want to drink the tears of
		// depressed engineers (aka we need to test if running course works)
		Registration r;
		for (int i = 0; i < studentList.size(); i++) {
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
	 * Overwrites the student ArrayList to database
	 */
	public void writeStudentsToDatabase() {
		try {
			//Delete everything from the database to overwrite
			String query = "DELETE FROM student";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
			
			query = "INSERT INTO student (id, name) values (?, ?)";
			pStat = myConn.prepareStatement(query);
			for(Student s: studentList) {
				pStat.setInt(1, s.getStudentId());
				pStat.setString(2, s.getStudentName());
				pStat.executeUpdate();
			}
			pStat.close();
		} catch(SQLException e) {
			System.err.println("Error: SQL erros LOL");
		}
	}
	
	/**
	 * Overwrites the course ArrayList to database
	 */
	public void writeCoursesToDatabase() {
		try {
			//Delete everything from the database to overwrite
			String query = "DELETE FROM course";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
			
			query = "INSERT INTO course (id, name, num) values (?, ?, ?)";
			pStat = myConn.prepareStatement(query);
			for(Course c: courseList) {
				pStat.setInt(1, c.getID());
				pStat.setString(2, c.getCourseName());
				pStat.setInt(3, c.getCourseNum());
				pStat.executeUpdate();
			}
			pStat.close();
		} catch(SQLException e) {
			System.err.println("Error: SQL erros LOL");
		}
	}
	
	/**
	 * Overwrites the course offering ArrayList to database
	 */
	public void writeCourseOfferingsToDatabase() {
		try {
			//Delete everything from the database to overwrite
			String query = "DELETE FROM course_offering";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
			
			query = "INSERT INTO course_offering (id, course_id, sec_num, sec_cap) values (?, ?, ?, ?)";
			pStat = myConn.prepareStatement(query);
			for(CourseOffering co: courseOfferingList) {
				pStat.setInt(1, co.getID());
				pStat.setInt(2, co.getTheCourse().getID());
				pStat.setInt(3, co.getSecNum());
				pStat.setInt(4, co.getSecCap());
				pStat.executeUpdate();
			}
			pStat.close();
		} catch(SQLException e) {
			System.err.println("Error: SQL erros LOL");
		}
	}
	
	/**
	 * Overwrites the registration ArrayList to database
	 */
	public void writeRegistrationToDatabase() {
		try {
			//Delete everything from the database to overwrite
			String query = "DELETE FROM registration";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
			
			query = "INSERT INTO registration (id, student_id, course_offering_id, grade) values (?, ?, ?, ?)";
			pStat = myConn.prepareStatement(query);
			for(Registration r: registrationList) {
				pStat.setInt(1, r.getID());
				pStat.setInt(2, r.getTheStudent().getStudentId());
				pStat.setInt(3, r.getTheOffering().getID());
				pStat.setString(4, r.getGrade() + "");
				pStat.executeUpdate();
			}
			pStat.close();
		} catch(SQLException e) {
			System.err.println("Error: SQL erros LOL");
		}
	}
	
	/**
	 * Overwrites the prereq ArrayList to database
	 */
	public void writePreReqToDatabase() {
		try {
			//Delete everything from the database to overwrite
			String query = "DELETE FROM prereq";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
			
			query = "INSERT INTO prereq (id, parent_course_id, prereq_course_id) values (?, ?, ?)";
			pStat = myConn.prepareStatement(query);
			int i = 40000;
			//Write all prereqs to the database
			for(Course c: courseList) {
				if(c.getPreReq().size() > 0) {
					for(Course p: c.getPreReq()) {
						pStat.setInt(1, i++); //For each prereq that we add, we will up the counter on the IDs
						pStat.setInt(2, c.getID());
						pStat.setInt(3, p.getID());
						pStat.executeUpdate();
					}
				}
				
			}
			pStat.close();
		} catch(SQLException e) {
			System.err.println("Error: SQL erros LOL");
		}
	}

	/**
	 * Test method for writing the students to a file
	 * 
	 * @param filename the filename
	 */
	public void writeStudentsToFile(String filename) {
		File output = new File(filename);
		try {
			output.createNewFile();
		} catch (IOException e) {
			System.err.println("Error: Could not create file with filename " + filename);
			e.printStackTrace();
		}

		try {
			FileWriter writer = new FileWriter(output, false);
			for (Student s : studentList) {
				writer.write(s.toData() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: unknown I/O error");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for writing the courses to a file
	 * 
	 * @param filename the filename
	 */
	public void writeCoursesToFile(String filename) {
		File output = new File(filename);
		try {
			output.createNewFile();
		} catch (IOException e) {
			System.err.println("Error: Could not create file with filename " + filename);
			e.printStackTrace();
		}

		try {
			FileWriter writer = new FileWriter(output, false);
			for (Course c : courseList) {
				writer.write(c.toData() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: unknown I/O error");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for writing the course offerings to a file
	 * 
	 * @param filename the filename
	 */
	public void writeCourseOfferingsToFile(String filename) {
		File output = new File(filename);
		try {
			output.createNewFile();
		} catch (IOException e) {
			System.err.println("Error: Could not create file with filename " + filename);
			e.printStackTrace();
		}

		try {
			FileWriter writer = new FileWriter(output, false);
			for (CourseOffering co : courseOfferingList) {
				writer.write(co.toData() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: unknown I/O error");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for writing the registrations to a file
	 * 
	 * @param filename the filename
	 */
	public void writeRegistrationsToFile(String filename) {
		File output = new File(filename);
		try {
			output.createNewFile();
		} catch (IOException e) {
			System.err.println("Error: Could not create file with filename " + filename);
			e.printStackTrace();
		}

		try {
			FileWriter writer = new FileWriter(output, false);
			for (Registration r : registrationList) {
				writer.write(r.toData() + "\n");
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: unknown I/O error");
			e.printStackTrace();
		}
	}

	/**
	 * Test method for writing the prerequisites to a file
	 * 
	 * @param filename the filename
	 */
	public void writePreReqsToFile(String filename) {
		File output = new File(filename);
		try {
			output.createNewFile();
		} catch (IOException e) {
			System.err.println("Error: Could not create file with filename " + filename);
			e.printStackTrace();
		}

		try {
			FileWriter writer = new FileWriter(output, false);
			for (Course c : courseList) {
				writer.write(c.toPreReqData());
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Error: unknown I/O error");
			e.printStackTrace();
		}
	}

	/**
	 * Method for reading the Students from the database
	 */
	public void readStudentsFromFile() {
		try {
			String query = "SELECT * FROM student";
			PreparedStatement pStat = myConn.prepareStatement(query);
			myRs = pStat.executeQuery();
			while(myRs.next()){
				dataToStudent(myRs.getInt("id"),myRs.getString("name"));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: Could not read Student from database");
			e.printStackTrace();
		}
	}

	/**
	 * Method for reading the Courses from the database
	 */
	public void readCoursesFromFile() {
		try {
			String query = "SELECT * FROM course";
			PreparedStatement pStat = myConn.prepareStatement(query);
			myRs = pStat.executeQuery();
			while(myRs.next()){
				dataToCourse(myRs.getInt("id"),myRs.getString("name"),myRs.getInt("num"));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: Could not read Student from database");
			e.printStackTrace();
		}
	}

	/**
	 * Method for reading the Prereqs for courses from the database
	 */
	public void readPreReqFromFile() {
		try {
			String query = "SELECT * FROM prereq";
			PreparedStatement pStat = myConn.prepareStatement(query);
			myRs = pStat.executeQuery();
			while(myRs.next()){
				dataToPreReqs(myRs.getInt("parent_course_id"),myRs.getInt("prereq_course_id"));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: Could not read Student from database");
			e.printStackTrace();
		}
	}

	/**
	 * Method for reading the course offerings from the database
	 */
	public void readCourseOfferingsFromFile() {
		try {
			String query = "SELECT * FROM course_offering";
			PreparedStatement pStat = myConn.prepareStatement(query);
			myRs = pStat.executeQuery();
			while(myRs.next()){
				dataToCourseOffering(myRs.getInt("id"),myRs.getInt("course_id"),myRs.getInt("sec_num"),myRs.getInt("sec_cap"));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: Could not read Student from database");
			e.printStackTrace();
		}
	}

	/**
	 * Method for reading the registrations from the database
	 */
	public void readRegistrationsFromFile() {
		try {
			String query = "SELECT * FROM registration";
			PreparedStatement pStat = myConn.prepareStatement(query);
			myRs = pStat.executeQuery();
			while(myRs.next()){
				dataToRegistration(myRs.getInt("id"),myRs.getInt("student_id"),myRs.getInt("course_offering_id"),myRs.getString("grade"));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: Could not read Student from database");
			e.printStackTrace();
		}
	}

	/**
	 * Creates Student from the data provided. Will not make the Student if data is
	 * missing or not found
	 * @param name The name of the student
	 * @param id The ID of the student
	 */
	public void dataToStudent(int id, String name) {
		try {
			getStudentList().add(new Student(name,id));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates Registration from the data provided. Will not make the Registration
	 * 	 * if data is missing or not found
	 * @param id ID of registration
	 * @param studentID ID of student that is registerijg
	 * @param courseOfferingID ID of courseOffering that is being registered
	 * @param grade grade of the registration
	 */
	public void dataToRegistration(int id, int studentID, int courseOfferingID, String grade) {
		try {
			int s = searchStudent(studentID);
			int co = searchCourseOffering(courseOfferingID);
			if (s == -1) {
				System.err.println("Couldn't find Student for Registration");
				return;
			}
			if (co == -1) {
				System.err.println("Couldn't find CourseOffering for Registration");
				return;
			}
			getRegistrationList().add(new Registration(id, getStudentList().get(s),
					getCourseOfferingList().get(co), grade.charAt(0)));
			getStudentList().get(s).getStudentRegList()
					.add(getRegistrationList().get(getRegistrationList().size() - 1));
			getCourseOfferingList().get(co).getOfferingRegList()
					.add(getRegistrationList().get(getRegistrationList().size() - 1));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates CourseOffering from the data provided. Will not make the
	 * CourseOffering if data is missing or not found
	 * @param id ID of the course offering
	 * @param courseID ID of the course the offering is for
	 * @param num Number of offering
	 * @param cap Maximum capacity of offering
	 */
	public void dataToCourseOffering(int id, int courseID, int num, int cap) {
		try {
			int c = searchCourse(courseID);
			if (c == -1) {
				System.err.println("Couldn't find Course for Course Offering");
				return;
			}
			getCourseOfferingList().add(new CourseOffering(id, getCourseList().get(c), num, cap));
			getCourseList().get(c).getOfferingList()
					.add(getCourseOfferingList().get(getCourseOfferingList().size() - 1));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates Course from the data provided. Will not make the Course if data is
	 * 	 * missing or not found
	 * @param id Id of course
	 * @param name name of course
	 * @param num number of course
	 */
	public void dataToCourse(int id, String name, int num) {
		try {
			getCourseList().add(new Course(name,num,id));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds Prereqs to all the courses that have been added
	 * @param parentId ID of the course that will has a pre requisite
	 * @param prereqId ID of the course that will be the pre requisite
	 */
	public void dataToPreReqs(int parentId, int prereqId) {
		try {
			int parent = searchCourse(parentId);
			int preReq = searchCourse(prereqId);
			if (parent == -1) {
				System.err.println("Parent not found for preReq");
			}
			if (preReq == -1) {
				System.err.println("PreReq not found for preReq");
			}
			getCourseList().get(parent).getPreReq().add(getCourseList().get(preReq));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	// Searches for data type with matching ID
	/**
	 * Searches for the Student with the matching ID
	 * 
	 * @param id The ID of the student we are searching for
	 * @return Returns the index that the student is found if found, -1 if not found
	 */
	public int searchStudent(int id) {
		for (int i = 0; i < getStudentList().size(); i++) {
			if (id == getStudentList().get(i).getStudentId()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Searches for the Registration with the matching ID
	 * 
	 * @param id The ID of the registration we are searching for
	 * @return Returns the index that the registration is found if found, -1 if not
	 *         found
	 */
	public int searchRegistration(int id) {
		for (int i = 0; i < getRegistrationList().size(); i++) {
			if (id == getRegistrationList().get(i).getID()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Searches for the Course with the matching ID
	 * 
	 * @param id The ID of the Course we are searching for
	 * @return Returns the index that the Course is found if found, -1 if not found
	 */
	public int searchCourse(int id) {
		for (int i = 0; i < getCourseList().size(); i++) {
			if (id == getCourseList().get(i).getID()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Searches for the CourseOffering with the matching ID
	 * 
	 * @param id The ID of the CourseOffering we are searching for
	 * @return Returns the index that the CourseOffering is found if found, -1 if
	 *         not found
	 */
	public int searchCourseOffering(int id) {
		for (int i = 0; i < getCourseOfferingList().size(); i++) {
			if (id == getCourseOfferingList().get(i).getID()) {
				return i;
			}
		}
		return -1;
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

	public synchronized void setRegistrationList(ArrayList<Registration> registrationList) {
		this.registrationList = registrationList;
	}

	public synchronized ArrayList<CourseOffering> getCourseOfferingList() {
		return courseOfferingList;
	}

	public synchronized void setCourseOfferingList(ArrayList<CourseOffering> courseOfferingList) {
		this.courseOfferingList = courseOfferingList;
	}

	public ArrayList<Student> loadStudents() {
		return studentList;
	}

	public ArrayList<Course> loadCourses() {
		return courseList;
	}

	// GETTERS AND SETTERS FOR CLASS IDS
	public int getIncrementStudentID() {
		return studentID++;
	}

	public int getIncrementCourseID() {
		return courseID++;
	}

	public int getIncrementCourseOfferingID() {
		return courseOfferingID++;
	}

	public int getIncrementRegistrationID() {
		return registrationID++;
	}

	public static void main(String[] args) {
		DatabaseController dbc = new DatabaseController();
		dbc.readCoursesFromDatabase();
		dbc.readStudentsFromDatabase();
		dbc.registerStudentsInCourses();

		dbc.writeStudentsToFile("students.txt");
		dbc.writeCoursesToFile("courses.txt");
		dbc.writeCourseOfferingsToFile("courseofferings.txt");
		dbc.writeRegistrationsToFile("registrations.txt");
		dbc.writePreReqsToFile("prerequisites.txt");
	}

}
