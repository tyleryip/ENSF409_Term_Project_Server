package com.KerrYip.Model;
import java.util.ArrayList;

public class Course {

	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq;
	private ArrayList<CourseOffering> offeringList;

	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}

	public ArrayList<CourseOffering> getOfferingList() {
		return offeringList;
	}

	public void setOfferingList(ArrayList<CourseOffering> offeringList) {
		this.offeringList = offeringList;
	}

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
	
	public void addPreReq(Course preReqCourse) {
		preReq.add(preReqCourse);
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
	
	@Override
	public String toString () {
		String st = "\n";
		st += getCourseName() + " " + getCourseNum ();
		st += "\n---------------------------------------";
		st += "\nAll course sections:\n";
		for (CourseOffering c : offeringList)
			st += c;
		st += "\nAll course prerequisites:\n";
		for(Course c: preReq)
			st += c.getCourseName() + " " + c.getCourseNum();
		st += "\n---------------------------------------\n";
		return st;
	}

	public CourseOffering getCourseOfferingAt(int i) {
		// TODO Auto-generated method stub
		if (i < 0 || i >= offeringList.size()) {
			System.out.println("Offering " + i + " does not exist.");
			return null;
			}
		else
			return offeringList.get(i);
	}

}
