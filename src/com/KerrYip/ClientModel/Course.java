package com.KerrYip.ClientModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The purpose of this class is to be able to serialize Course objects to send
 * back and forth between client and server
 * 
 * @author tyleryip
 * @version 1.0
 * @since 04/08/20
 */
public class Course implements Serializable {

	public Course(String courseName, int courseNum) {
		this.courseName = courseName;
		this.courseNum = courseNum;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2108036660174376139L;

	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq;
	private ArrayList<CourseOffering> offeringList;
}
