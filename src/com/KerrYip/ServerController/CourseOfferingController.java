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
     * Makes new registration, adds it to databaseController and returns it
     * @return Returns the new registration
     */
    public void addCourseOffering(CourseOffering co){
        databaseController.getCourseOfferingList().add(co);
    }

    public DatabaseController getDatabaseController() { return databaseController; }
    public void setDatabaseController(DatabaseController databaseController) { this.databaseController = databaseController; }
    public ArrayList<CourseOffering> getMyCourseOfferingList() { return myCourseOfferingList; }
    public void setMyCourseOfferingList(ArrayList<CourseOffering> myCourseOfferingList) { this.myCourseOfferingList = myCourseOfferingList; }

}
