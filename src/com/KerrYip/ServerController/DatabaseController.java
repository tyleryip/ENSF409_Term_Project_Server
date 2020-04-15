package com.KerrYip.ServerController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

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
	/*
	 * IDs for all variable types for SQL integration
	 */
	int studentID = 1;
	int courseID = 10000;
	int courseOfferingID = 20000;
	int registrationID = 30000;
	int prereqID = 40000;

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
		properties = new Properties();
		properties.setProperty("user", "ENSF409");
		properties.setProperty("password", "ENSF_409");
		properties.setProperty("useSSL", "false");
		properties.setProperty("allowsPublicKeyRetrieval", "true");

		try {
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration_app_database", properties);
			System.out.println("[Database Controller] Connection with SQL Database was successfully established!");
		} catch (SQLException e) {
			System.err.println("Error: Unknown SQL error has occured");
			e.printStackTrace();
		}
		
		createTables();
	}

	/**
	 * Method for reading the Students from the database
	 */
	public ArrayList<Student> readStudentsFromFile() {
		ArrayList<Student> fromFile = new ArrayList<Student>();

		try {
			String query = "SELECT * FROM student";
			PreparedStatement pStat = myConn.prepareStatement(query);
			myRs = pStat.executeQuery();
			while (myRs.next()) {
				fromFile.add(dataToStudent(myRs.getInt("id"), myRs.getString("name"), myRs.getString("password")));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: Could not read Student from database");
			e.printStackTrace();
		}

		return fromFile;
	}

	/**
	 * Method for reading the Courses from the database
	 */
	public ArrayList<Course> readCoursesFromFile() {
		ArrayList<Course> fromFile = new ArrayList<Course>();

		try {
			String query = "SELECT * FROM course";
			PreparedStatement pStat = myConn.prepareStatement(query);
			myRs = pStat.executeQuery();
			while (myRs.next()) {
				fromFile.add(dataToCourse(myRs.getInt("id"), myRs.getString("name"), myRs.getInt("num")));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: Could not read Student from database");
			e.printStackTrace();
		}

		return fromFile;
	}

	/**
	 * Method for reading the Prereqs for courses from the database
	 */
	public void readPreReqFromFile(ArrayList<Course> courseList) {
		try {
			String query = "SELECT * FROM prereq";
			PreparedStatement pStat = myConn.prepareStatement(query);
			myRs = pStat.executeQuery();
			while (myRs.next()) {
				dataToPreReqs(courseList, myRs.getInt("parent_course_id"), myRs.getInt("prereq_course_id"));
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
	public ArrayList<CourseOffering> readCourseOfferingsFromFile(ArrayList<Course> courseList) {
		ArrayList<CourseOffering> fromFile = new ArrayList<CourseOffering>();

		try {
			String query = "SELECT * FROM course_offering";
			PreparedStatement pStat = myConn.prepareStatement(query);
			myRs = pStat.executeQuery();
			while (myRs.next()) {
				fromFile.add(dataToCourseOffering(courseList, myRs.getInt("id"), myRs.getInt("course_id"),
						myRs.getInt("sec_num"), myRs.getInt("sec_cap")));
			}
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: Could not read Student from database");
			e.printStackTrace();
		}

		return fromFile;
	}

	/**
	 * Method for reading the registrations from the database
	 */
	public ArrayList<Registration> readRegistrationsFromFile(ArrayList<CourseOffering> courseOfferingList,
			ArrayList<Student> studentList) {
		ArrayList<Registration> fromFile = new ArrayList<Registration>();

		try {
			String query = "SELECT * FROM registration";
			PreparedStatement pStat = myConn.prepareStatement(query);
			myRs = pStat.executeQuery();
			while (myRs.next()) {
				fromFile.add(dataToRegistration(courseOfferingList, studentList, myRs.getInt("id"),
						myRs.getInt("student_id"), myRs.getInt("course_offering_id"), myRs.getString("grade")));
			}
			pStat.close();
			return fromFile;
		} catch (SQLException e) {
			System.err.println("Error: Could not read Student from database");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Creates Student from the data provided. Will not make the Student if data is
	 * missing or not found
	 * 
	 * @param name The name of the student
	 * @param id   The ID of the student
	 */
	public Student dataToStudent(int id, String name, String password) {
		try {
			return new Student(name, id, password);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Creates Registration from the data provided. Will not make the Registration *
	 * if data is missing or not found
	 * 
	 * @param id               ID of registration
	 * @param studentID        ID of student that is registerijg
	 * @param courseOfferingID ID of courseOffering that is being registered
	 * @param grade            grade of the registration
	 */
	public Registration dataToRegistration(ArrayList<CourseOffering> courseOfferingList, ArrayList<Student> studentList,
			int id, int studentID, int courseOfferingID, String grade) {
		try {
			int s = searchStudent(studentList, studentID);
			int co = searchCourseOffering(courseOfferingList, courseOfferingID);
			if (s == -1) {
				System.err.println("Couldn't find Student for Registration");
				return null;
			}
			if (co == -1) {
				System.err.println("Couldn't find CourseOffering for Registration");
				return null;
			}
			Registration newReg = new Registration(id, grade.charAt(0));
			// The following method call ensures that the student and course offering will
			// recieve the registration
			newReg.completeRegistration(studentList.get(s), courseOfferingList.get(co));
			return newReg;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Creates CourseOffering from the data provided. Will not make the
	 * CourseOffering if data is missing or not found
	 * 
	 * @param id       ID of the course offering
	 * @param courseID ID of the course the offering is for
	 * @param num      Number of offering
	 * @param cap      Maximum capacity of offering
	 */
	public CourseOffering dataToCourseOffering(ArrayList<Course> courseList, int id, int courseID, int num, int cap) {
		try {
			int c = searchCourse(courseList, courseID);
			if (c == -1) {
				System.err.println("Couldn't find Course for Course Offering");
				return null;
			}
			CourseOffering newOffering = new CourseOffering(id, courseList.get(c), num, cap);
			courseList.get(c).addOffering(newOffering);
			return newOffering;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Creates Course from the data provided. Will not make the Course if data is *
	 * missing or not found
	 * 
	 * @param id   Id of course
	 * @param name name of course
	 * @param num  number of course
	 */
	public Course dataToCourse(int id, String name, int num) {
		try {
			return (new Course(name, num, id));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Adds Prereqs to all the courses that have been added
	 * 
	 * @param parentId ID of the course that will has a pre requisite
	 * @param prereqId ID of the course that will be the pre requisite
	 */
	public void dataToPreReqs(ArrayList<Course> courseList, int parentId, int prereqId) {
		try {
			int parent = searchCourse(courseList, parentId);
			int preReq = searchCourse(courseList, prereqId);
			if (parent == -1) {
				System.err.println("Parent not found for preReq");
			}
			if (preReq == -1) {
				System.err.println("PreReq not found for preReq");
			}
			courseList.get(parent).addPreReq(courseList.get(preReq));
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
	public int searchStudent(ArrayList<Student> list, int id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getStudentId() == id) {
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
	public int searchRegistration(ArrayList<Registration> list, int id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getID() == id) {
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
	public int searchCourse(ArrayList<Course> list, int id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getID() == id) {
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
	public int searchCourseOffering(ArrayList<CourseOffering> list, int id) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getID() == id) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Inserts a new student into the SQL database
	 * 
	 * @param s the student to insert
	 */
	public synchronized void insertStudentToDatabase(Student s) {
		try {
			String query = "INSERT INTO student(id, name, password) values (?,?,?)";
			pStat = myConn.prepareStatement(query);
			pStat.setInt(1, s.getStudentId());
			pStat.setString(2, s.getStudentName());
			pStat.setString(3, s.getPassword());
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with writing student into student table");
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a new course into the SQL database
	 * 
	 * @param c the course to insert
	 */
	public synchronized void insertCourseToDatabase(Course c) {
		try {
			String query = "INSERT INTO course(id, name, num) values (?, ?, ?)";
			pStat = myConn.prepareStatement(query);
			pStat.setInt(1, c.getID());
			pStat.setString(2, c.getCourseName());
			pStat.setInt(3, c.getCourseNum());
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with writing course into course table");
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a new course offering into the SQL database
	 * 
	 * @param co the course offering to insert
	 */
	public synchronized void insertCourseOfferingToDatabase(CourseOffering co) {
		try {
			String query = "INSERT INTO course_offering (id, course_id, sec_num, sec_cap) values (?, ?, ?, ?)";
			pStat = myConn.prepareStatement(query);
			pStat.setInt(1, co.getID());
			pStat.setInt(2, co.getTheCourse().getID());
			pStat.setInt(3, co.getSecNum());
			pStat.setInt(4, co.getSecCap());
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with writing course offering into course offering table");
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a new registration into the SQL database
	 * 
	 * @param r the registration to insert
	 */
	public synchronized void insertRegistrationToDatabase(Registration r) {
		try {
			String query = "INSERT INTO registration (id, student_id, course_offering_id, grade) values (?, ?, ?, ?)";
			pStat = myConn.prepareStatement(query);
			pStat.setInt(1, r.getID());
			pStat.setInt(2, r.getTheStudent().getStudentId());
			pStat.setInt(3, r.getTheOffering().getID());
			pStat.setString(4, r.getGrade() + "");
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with writing registration into registration table");
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a new registration into the SQL database
	 * 
	 * @param r the registration to insert
	 */
	public synchronized void insertPreReqToDatabase(Course parent, Course prereq) {
		try {
			String query = "INSERT INTO prereq (id, parent_course_id, prereq_course_id) values (?, ?, ?)";
			pStat = myConn.prepareStatement(query);
			pStat.setInt(1, getIncrementPreReqID());
			pStat.setInt(2, parent.getID());
			pStat.setInt(3, prereq.getID());
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with writing prereq into prereq table");
			e.printStackTrace();
		}
	}

	/**
	 * Removes a course from the database
	 * 
	 * @param c the course to remove
	 */
	public synchronized void deleteCourseFromDatabase(Course c) {
		try {
			String query = "DELETE FROM course WHERE id = ?";
			pStat = myConn.prepareStatement(query);
			pStat.setInt(1, c.getID());
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with deleting course from course offering table");
			e.printStackTrace();
		}
	}

	/**
	 * Removes a course offering from the database
	 * 
	 * @param co the course offering to remove
	 */
	public synchronized void deleteCourseOfferingFromDatabase(CourseOffering co) {
		try {
			String query = "DELETE FROM course_offering WHERE id = ?";
			pStat = myConn.prepareStatement(query);
			pStat.setInt(1, co.getID());
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with deleting course offering from course offering table");
			e.printStackTrace();
		}
	}

	/**
	 * Removes a registration from the database
	 * 
	 * @param r the registration to remove
	 */
	public synchronized void deleteRegistrationFromDatabase(Registration r) {
		try {
			String query = "DELETE FROM registration WHERE id = ?";
			pStat = myConn.prepareStatement(query);
			pStat.setInt(1, r.getID());
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with deleting registration from registration table");
			e.printStackTrace();
		}
	}

	/**
	 * Removes a Course from the database
	 * 
	 * @param prereq the Course to remove
	 */
	public synchronized void deletePreReqFromDatabase(Course prereq) {
		try {
			String query = "DELETE FROM registration WHERE prereq_course_id = ?";
			pStat = myConn.prepareStatement(query);
			pStat.setInt(1, prereq.getID());
			pStat.executeUpdate();
			pStat.close();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with deleting prereq from prereq table");
			e.printStackTrace();
		}
	}
	
	/**
	 * This method creates the necessary tables in the database if they be missing
	 */
	public void createTables() {
		try {
			String query = "CREATE TABLE IF NOT EXISTS student("
					+ "id INT PRIMARY KEY NOT NULL,"
					+ "name VARCHAR(45) NOT NULL,"
					+ "password VARCHAR(45) NOT NULL"
					+ ")";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with creating table student");
			e.printStackTrace();
		}
		
		try {
			String query = "CREATE TABLE IF NOT EXISTS course("
					+ "id INT PRIMARY KEY NOT NULL,"
					+ "name VARCHAR(45) NOT NULL,"
					+ "num INT NOT NULL"
					+ ")";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with creating table course");
			e.printStackTrace();
		}
		
		try {
			String query = "CREATE TABLE IF NOT EXISTS course_offering("
					+ "id INT PRIMARY KEY NOT NULL,"
					+ "course_id VARCHAR(45) NOT NULL,"
					+ "sec_num INT NOT NULL,"
					+ "sec_cap INT NOT NULL"
					+ ")";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with creating table course_offering");
			e.printStackTrace();
		}
		
		try {
			String query = "CREATE TABLE IF NOT EXISTS registration("
					+ "id INT PRIMARY KEY NOT NULL,"
					+ "student_id INT NOT NULL,"
					+ "course_offering_id INT NOT NULL,"
					+ "grade CHAR(1) NOT NULL"
					+ ")";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with creating table registration");
			e.printStackTrace();
		}
		
		try {
			String query = "CREATE TABLE IF NOT EXISTS prereq("
					+ "id INT PRIMARY KEY NOT NULL,"
					+ "parent_course_id INT NOT NULL,"
					+ "prereq_course_id INT NOT NULL"
					+ ")";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with creating table student");
			e.printStackTrace();
		}
		
		try {
			String query = "CREATE TABLE IF NOT EXISTS admin("
					+ "id INT PRIMARY KEY NOT NULL,"
					+ "username VARCHAR(45) NOT NULL,"
					+ "password VARCHAR(45) NOT NULL"
					+ ")";
			pStat = myConn.prepareStatement(query);
			pStat.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error: SQL error with creating table admin");
			e.printStackTrace();
		}
		
	}

	private int getIncrementPreReqID() {
		return prereqID++;
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

	public void updateRegistrationID(int updatedRegistrationID) {
		registrationID = updatedRegistrationID;
	}

	public void updateCourseOfferingID(int updatedCourseOfferingID) {
		courseOfferingID = updatedCourseOfferingID;
	}

	public void updateCourseID(int updatedCourseID) {
		courseID = updatedCourseID;
	}

	public void updateStudentID(int updatedStudentID) {
		studentID = updatedStudentID;
	}

	public void updatePreReqID(int updatedPreReqID) {
		prereqID = updatedPreReqID;
	}

}
