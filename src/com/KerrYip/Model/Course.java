package com.KerrYip.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a course a student can enroll in or an administrator
 * can modify
 * 
 * @author tyleryip
 * @version 2.0
 * @since 04/07/20
 *
 */
public class Course implements Serializable {

	/**
	 * This long is used for serialization
	 */
	private static final long serialVersionUID = 1L;

	private String courseName;
	private int courseNum;
	
	//This id number is used to store in the SQL database
	private int id;
	private ArrayList<Course> preReq;
	private ArrayList<CourseOffering> offeringList;

	/**
	 * Constructor for the class Course
	 * 
	 * @param courseName the name of the course
	 * @param courseNum  the number of the course
	 */
	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}

	/**
	 * Constructor for the class Course
	 * @param courseID ID number used to store in the SQL database
	 * @param courseName the name of the course
	 * @param courseNum  the number of the course
	 */
	public Course(String courseName, int courseNum, int courseID) {
		this.id = courseID;
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}

	/**
	 * Adds a course offering to this course
	 * 
	 * @param offering the course offering to add
	 */
	public void addOffering(CourseOffering offering) {
		if (offering != null) {
			offering.setTheCourse(this);
			if (!offering.getTheCourse().getCourseName().equals(courseName)
					|| offering.getTheCourse().getCourseNum() != courseNum) {
				System.err.println("Error! This section belongs to another course!");
				return;
			}

			offeringList.add(offering);
		}
	}

	/**
	 * Adds a prerequisite course to this course
	 * 
	 * @param preReqCourse the coruse to add as a prereq
	 */
	public void addPreReq(Course preReqCourse) {
		preReq.add(preReqCourse);
	}

	public CourseOffering getCourseOfferingAt(int i) {
		// TODO Auto-generated method stub
		if (i < 0 || i >= offeringList.size()) {
			System.out.println("Offering " + i + " does not exist.");
			return null;
		} else
			return offeringList.get(i);
	}

	@Override
	public String toString() {
		String st = "";
		st += getCourseName() + " " + getCourseNum();
		st += "\n---------------------------------------";
		st += "\nAll course sections:\n";
		if (offeringList.size() <= 0) {
			for (CourseOffering c : offeringList)
				st += c;
		}
		st += "\nAll course prerequisites:\n";
		if (preReq.size() <= 0) {
			for (Course c : preReq)
				st += c.getCourseName() + " " + c.getCourseNum();
		}
		st += "\n---------------------------------------\n";
		return st;
	}

	public String toData(){
		String st = getID() + ";" + getCourseName() + ";" + getCourseNum();
		for(Course c: getPreReq()){
			st += ";" + c.getID();
		}
		return st;
	}

	// GETTERS and SETTERS
	public ArrayList<CourseOffering> getOfferingList() {
		return offeringList;
	}

	public void setOfferingList(ArrayList<CourseOffering> offeringList) {
		this.offeringList = offeringList;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}

	public ArrayList<Course> getPreReq() {
		return preReq;
	}

	public void setPreReq(ArrayList<Course> preReq) {
		this.preReq = preReq;
	}

	public String getNameNum() {
		return this.courseName + " " + this.getCourseNum();
	}

	public int getID() { return id; }

	public void setID(int courseID) { this.id = courseID; }

}
