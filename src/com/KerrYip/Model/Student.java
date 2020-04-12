package com.KerrYip.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a student
 * 
 * @author tyleryip
 * @version 2.0
 * @since 04/07/20
 *
 */
public class Student implements Serializable {

	/**
	 * This long is used for serialization
	 */
	private static final long serialVersionUID = 1L;

	private String studentName;
	private int studentId;
	private boolean active;
	private ArrayList<Registration> studentRegList;

	/**
	 * Constructor for class student
	 * 
	 * @param studentName the name of the student
	 * @param studentId   the ID number of the student
	 */
	public Student(String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		this.setActive(false);
		studentRegList = new ArrayList<Registration>();
	}

	/**
	 * Searchs a student's registration list for a registration that matches a
	 * specified course
	 * 
	 * @param theCourse the course to search
	 * @return the registration if found, null otherwise
	 */
	public Registration searchStudentReg(Course theCourse) {
		for (int i = 0; i < studentRegList.size(); i++) {
			if (studentRegList.get(i).getTheOffering().getTheCourse().getNameNum().equals(theCourse.getNameNum())) {
				return studentRegList.get(i);
			}
		}
		System.out.println("Error: Could not find the registration matching that course!");
		return null;
	}

	/**
	 * Adds a registration to the student's list of registrations
	 * 
	 * @param registration the registration to add
	 */
	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		studentRegList.add(registration);
	}

	@Override
	public String toString() {
		String st = "Student Name: " + getStudentName() + "\n" + "Student Id: " + getStudentId() + "\n\n";
		return st;
	}

	public String toData(){
		return getStudentId() + ";" + getStudentName();
	}

	// GETTERS and SETTERS
	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public ArrayList<Registration> getStudentRegList() {
		return studentRegList;
	}

	public void setStudentRegList(ArrayList<Registration> studentRegList) {
		this.studentRegList = studentRegList;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
