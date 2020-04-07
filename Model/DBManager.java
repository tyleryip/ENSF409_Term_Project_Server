package com.KerrYip.Model;
import java.util.ArrayList;

//This class is simulating a database for our
//program
public class DBManager {
	
	ArrayList <Course> courseList;
	ArrayList <CourseOffering> offeringList;

	public DBManager () {
		courseList = new ArrayList<Course>();
	}

	public ArrayList<Course> readCoursesFromDataBase() {
		// Simulating courses
		courseList.add(new Course ("ENGG", 233));
		courseList.get(0).addOffering(new CourseOffering(1, 100));
		
		courseList.add(new Course ("ENSF", 409));
		courseList.get(1).addOffering(new CourseOffering(1, 100));
		courseList.get(1).addOffering(new CourseOffering(2, 200));

		
		courseList.add(new Course ("PHYS", 259));
		courseList.get(2).addOffering(new CourseOffering(1, 100));
		courseList.get(2).addOffering(new CourseOffering(2, 320));
		
		courseList.add(new Course ("MATH", 211));
		courseList.get(3).addOffering(new CourseOffering(1, 150));
		courseList.get(3).addOffering(new CourseOffering(2, 250));
		courseList.get(3).addOffering(new CourseOffering(3, 270));
		
		courseList.add(new Course ("ENGG", 202));
		courseList.get(4).addOffering(new CourseOffering(1, 120));
		
		//Added prerequisites
		courseList.get(1).addPreReq(courseList.get(4));
		courseList.get(4).addPreReq(courseList.get(3));
		courseList.get(2).addPreReq(courseList.get(3));
		
		return courseList;
	}

}
