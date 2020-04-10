package com.KerrYip.ClientModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The purpose of this class is to be able to serialize CourseOffering objects
 * to send back and forth between client and server
 * 
 * @author tyleryip
 * @version 1.0
 * @since 04/08/20
 */
public class CourseOffering implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3885505324085384368L;

	private int secNum;
	private int secCap;
	private Course theCourse;
	// private ArrayList<Student> studentList;
	private ArrayList<Registration> offeringRegList;

	@Override
	public String toString() {
		String st = "\nCourse: ";
		st += theCourse.getCourseName() + " " + theCourse.getCourseNum() + "\n";
		st += "Section Num: " + secNum + ", section cap: " + secCap + "\n";
		// We also want to print the names of all students in the section
		return st;
	}
}
