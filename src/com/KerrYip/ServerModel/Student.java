package com.KerrYip.ServerModel;

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
public class Student implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3885505324085384368L;
	
	private String studentName;
	private int studentId;
	// private ArrayList<CourseOffering> offeringList;
	private ArrayList<Registration> studentRegList;

	public Student(String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
	}

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

	public Registration searchStudentReg(Course theCourse) {
		for (Registration reg : studentRegList) {
			if (reg.getTheOffering().getTheCourse().equals(theCourse)) {
				return reg;
			}
		}
		System.out.println("Error: Could not find the registration matching that course!");
		return null;
	}

	@Override
	public String toString() {
		String st = "Student Name: " + getStudentName() + "\n" + "Student Id: " + getStudentId() + "\n\n";
		return st;
	}

	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		studentRegList.add(registration);
	}

}
