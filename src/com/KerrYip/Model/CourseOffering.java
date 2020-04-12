package com.KerrYip.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a section or offering of a specific course
 * 
 * @author tyleryip
 * @version 2.0
 * @since 04/07/20
 */
public class CourseOffering implements Serializable {

	/**
	 * This long is used for serialization
	 */
	private static final long serialVersionUID = 1L;

	private int secNum;
	private int secCap;
	private Course theCourse;
	private ArrayList<Registration> offeringRegList;
	private int id;

	/**
	 * The constructor for class CourseOffering
	 * 
	 * @param secNum the section number
	 * @param secCap the capacity of the course offering
	 */
	public CourseOffering(int secNum, int secCap) {
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList<Registration>();
	}

	/**
	 * The constructor for class CourseOffering
	 * @param courseOfferingID ID number used to store in the SQL database
	 * @param secNum the section number
	 * @param secCap the capacity of the course offering
	 */
	public CourseOffering(int secNum, int secCap, int courseOfferingID) {
		this.id = courseOfferingID;
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList<Registration>();
	}

	/**
	 * The constructor for class CourseOffering
	 * @param courseOfferingID ID number used to store in the SQL database
	 * @param course The course
	 * @param secNum the section number
	 * @param secCap the capacity of the course offering
	 */
	public CourseOffering(int courseOfferingID, Course course, int secNum, int secCap) {
		this.id = courseOfferingID;
		this.theCourse = course;
		this.setSecNum(secNum);
		this.setSecCap(secCap);
		offeringRegList = new ArrayList<Registration>();
	}

	/**
	 * Adds a registration to the course offering's registration list
	 * 
	 * @param registration the registration to add
	 */
	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		offeringRegList.add(registration);

	}

	@Override
	public String toString() {
		String st = "\nCourse: ";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseNum() + "\n";
		st += "Section Num: " + getSecNum() + ", section cap: " + getSecCap() + "\n";
		// We also want to print the names of all students in the section
		return st;
	}

	public String toData(){
		return getID() +";"+ getTheCourse().getID() +";"+ getSecNum() +";"+ getSecCap();
	}

	// GETTERS and SETTERS
	public int getSecNum() {
		return secNum;
	}

	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}

	public int getSecCap() {
		return secCap;
	}

	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}

	public Course getTheCourse() {
		return theCourse;
	}

	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}

	public ArrayList<Registration> getOfferingRegList() {
		return offeringRegList;
	}

	public void setOfferingRegList(ArrayList<Registration> offeringRegList) {
		this.offeringRegList = offeringRegList;
	}

	public int getID() { return id; }

	public void setID(int courseOfferingID) { this.id = courseOfferingID; }

}
