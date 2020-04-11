package com.KerrYip.ServerController;

import java.util.ArrayList;
import java.util.Iterator;

import com.KerrYip.ServerModel.Student;

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

	/**
	 * Constructor for the class StudentManager
	 * 
	 * @param db the DatabaseController for the student manager to use
	 */
	public StudentController(DatabaseController db) {
		this.databaseController = db;
	}

	/**
	 * Searches for a student with a matching name
	 * 
	 * @param name the name of desired student
	 * @return the student with a matching name
	 */
	public Student searchStudent(String name) {
		Iterator<Student> itr = databaseController.getStudentList().iterator();
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
		Iterator<Student> itr = databaseController.getStudentList().iterator();
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
	 * Adds a student to the student list
	 * 
	 * @param name the name of the student
	 */
	public void addStudent(String name) {
		int id = databaseController.getStudentList().size();
		Student newStudent = new Student(name, id + 1);
		databaseController.getStudentList().add(newStudent);
		System.out.println("[Server] New student " + name + " created successfully with an id of: " + id);
	}

	public DatabaseController getDatabaseController() {
		return databaseController;
	}

	public void setDatabaseController(DatabaseController databaseController) {
		this.databaseController = databaseController;
	}

}
