package com.KerrYip.ClientModel;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * The purpose of this class is to be able to serialize Student objects to send back and forth between client and server
 * @author tyleryip
 * @version 1.0
 * @since 04/08/20
 */
public class Student implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4063287046883356763L;
	
	private String studentName;
	private int studentId;
	// private ArrayList<CourseOffering> offeringList;
	private ArrayList<Registration> studentRegList;
	
}
