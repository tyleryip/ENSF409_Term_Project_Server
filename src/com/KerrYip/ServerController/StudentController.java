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
	private ArrayList<Student> studentList;

	public StudentController(DatabaseController db) {
		this.databaseController = db;
		studentList = new ArrayList<Student>();
		loadFromDatabase();
	}

	private void loadFromDatabase() {
		setStudentList(databaseController.loadStudents());
	}

	public Student searchStudent(String name) {
		Iterator<Student> itr = studentList.iterator();
		while (itr.hasNext()) {
			Student check = itr.next();
			if (check.getStudentName().equalsIgnoreCase(name)) {
				return check;
			}
		}
		System.err.println("Could not find student with name: " + name);
		return null;

	}

	public Student searchStudent(int id) {
		Iterator<Student> itr = studentList.iterator();
		while (itr.hasNext()) {
			Student check = itr.next();
			if (check.getStudentId() == id) {
				return check;
			}
		}
		System.err.println("Could not find student with id: " + id);
		return null;

	}

	public void addStudent(String name) {
		int id = studentList.size();
		Student newStudent = new Student(name, id + 1);
		studentList.add(newStudent);
		System.out.println("New student " + name + " created successfully with an id of: " + id);
	}

	public DatabaseController getDatabaseController() {
		return databaseController;
	}

	public void setDatabaseController(DatabaseController databaseController) {
		this.databaseController = databaseController;
	}

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}
}
