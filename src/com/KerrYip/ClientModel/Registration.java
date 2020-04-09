package com.KerrYip.ClientModel;

import java.io.Serializable;

/**
 * The purpose of this class is to be able to serialize Registration objects to
 * send back and forth between client and server
 * 
 * @author tyleryip
 * @version 1.0
 * @since 04/08/20
 */
public class Registration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9101593004016454800L;

	private Student theStudent;
	private CourseOffering theOffering;
	private char grade;
}
