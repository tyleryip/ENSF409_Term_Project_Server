package com.KerrYip.ServerController;

import java.util.ArrayList;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;

public class CourseOfferingController {

    private DatabaseController databaseController;
    private ArrayList<CourseOffering> myCourseOfferingList;

    /**
     * Constructor for the class RegistrationController
     *
     * @param db the DatabaseController for the student manager to use
     */
    public CourseOfferingController(DatabaseController db) {
        this.databaseController = db;
        setMyCourseOfferingList(databaseController.getCourseOfferingList());
    }
    
    public void removeAllCourseOfferings(Course c) {
    	for(int i = 0; i<myCourseOfferingList.size(); i++) {
    		if(myCourseOfferingList.get(i).getTheCourse().getNameNum().equalsIgnoreCase(c.getNameNum())){
    			myCourseOfferingList.remove(i);
    		}
    	}
    }
    
    public void syncData() {
    	databaseController.setCourseOfferingList(myCourseOfferingList);
    }

    /**
     * Makes new course offering, adds it to databaseController and returns it
     * @param co the course offering with data for the new one to be added to the system
     * @param c The course the course offering is for
     * @return Returns the new Course Offering
     */
    public CourseOffering makeCourseOffering(CourseOffering co, int c){
        CourseOffering temp = new CourseOffering(databaseController.getIncrementCourseOfferingID(),getDatabaseController().getCourseList().get(c),co.getSecNum(),co.getSecCap());
        databaseController.getCourseOfferingList().add(temp);
        return temp;
    }

    public DatabaseController getDatabaseController() { return databaseController; }
    public void setDatabaseController(DatabaseController databaseController) { this.databaseController = databaseController; }
    public ArrayList<CourseOffering> getMyCourseOfferingList() { return myCourseOfferingList; }
    public void setMyCourseOfferingList(ArrayList<CourseOffering> myCourseOfferingList) { this.myCourseOfferingList = myCourseOfferingList; }

}
