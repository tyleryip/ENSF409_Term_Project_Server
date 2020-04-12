package com.KerrYip.ServerController;

import java.util.ArrayList;
import java.util.Iterator;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.Registration;
import com.KerrYip.Model.Student;

/**
 * This class manages everything to do with students
 * 
 * @author tyleryip
 * @version 1.0
 * @since 04/07/20
 *
 */
public class StudentController {

	private DatabaseController databaseController;

	private ArrayList<Student> myStudentList;

	/**
	 * Constructor for the class StudentController
	 * 
	 * @param db the DatabaseController for the student manager to use
	 */
	public StudentController(DatabaseController db) {
		this.databaseController = db;
		setMyStudentList(databaseController.getStudentList());
	}

	/**
	 * Searches for a student with a matching name
	 * 
	 * @param name the name of desired student
	 * @return the student with a matching name
	 */
	public Student searchStudent(String name) {
		Iterator<Student> itr = myStudentList.iterator();
		while (itr.hasNext()) {
			Student check = itr.next();
			if (check.getStudentName().equalsIgnoreCase(name)) {
				return check;
			}
		}
		System.err.println("Could not find student with name: " + name);
		return null;

	}

	/**
	 * Searches for a student with a matching ID number
	 * 
	 * @param id the id number of the desired student
	 * @return the student with a matching ID number, otherwise returns null
	 */
	public Student searchStudent(int id) {
		Iterator<Student> itr = myStudentList.iterator();
		while (itr.hasNext()) {
			Student check = itr.next();
			if (check.getStudentId() == id) {
				return check;
			}
		}
		System.err.println("Could not find student with id: " + id);
		return null;

	}

	/**
	 * Removes a specific course from any student that is currently registered in
	 * the course
	 * 
	 * @param c the course to remove
	 */
	public void removeCourseFromAll(Course c) {
		for (Student s : myStudentList) {
			for (int i = 0; i < s.getStudentRegList().size(); i++) {
				if (s.getStudentRegList().get(i).getTheOffering().getTheCourse().getNameNum()
						.equalsIgnoreCase(c.getNameNum())) {
					s.getStudentRegList().remove(i);
				}
			}
		}
	}

	public int searchStudentIndex(int id) {
		Iterator<Student> itr = myStudentList.iterator();
		int i = 0;
		while (itr.hasNext()) {
			Student check = itr.next();
			if (check.getStudentId() == id) {
				return i;
			}
			i++;
		}
		System.err.println("Could not find student with id: " + id);
		return -1;

	}

	public void syncData() {
		databaseController.setStudentList(myStudentList);
	}

	/**
	 * Adds a student to the student list
	 * 
	 * @param name the name of the student
	 */
	public void addStudent(String name, int id) {
		Student newStudent = new Student(name, id);
		databaseController.getStudentList().add(newStudent);
		System.out.println("[Server] New student " + name + " created successfully with an id of: " + id);
	}
	
	/**
	 * Adds a student to the student list and automatically assigns ID
	 * 
	 * @param name the name of the student
	 */
	public void addStudent(String name) {
		int newID = myStudentList.size() + 1;
		Student newStudent = new Student(name, newID);
		databaseController.getStudentList().add(newStudent);
		System.out.println("[Server] New student " + name + " created successfully with an id of: " + newID);
	}
	
	/**
	 * Checks to see if the proposed ID is available for use
	 * @param newID the ID to check
	 * @return true if the ID is not in use, false otherwise
	 */
	public boolean isUniqueID(int newID) {
		if(newID < 0) {
			System.err.println("Error: ID numbers cannot be negative");
			return false;
		}
		for(Student s: myStudentList) {
			if(s.getStudentId() == newID) {
				return false;
			}
		}
		return true;
	}

	public DatabaseController getDatabaseController() {
		return databaseController;
	}

	public void setDatabaseController(DatabaseController databaseController) {
		this.databaseController = databaseController;
	}

	public ArrayList<Student> getMyStudentList() {
		return myStudentList;
	}

	public void setMyStudentList(ArrayList<Student> myStudentList) {
		this.myStudentList = myStudentList;
	}

}
