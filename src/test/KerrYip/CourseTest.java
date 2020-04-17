package test.KerrYip;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Test Class for Unit Testing all components of Class Course
 */
public class CourseTest {

    @Test
    /**
     * Tests all methods in Course
     */
    public void CourseTest(){
        //Testing getters and setters
        testing_getID_and_setID();
        testing_getCourseName_and_setCourseName();
        testing_getCourseNum_and_setCourseNum();
        testing_getOfferingList_and_setOfferingList();
        testing_getPreReq_and_setPreReq();
        testing_getNameNum();

        //Testing constructors
        testing_CourseConstructor();
        testing_CourseConstructorWithID();

        //Testing methods
        testing_getCourseOfferingAt();
        testing_addOffering();
        testing_addPreReq();
        testing_compareTo();
        testing_toString();
    }

    @Test
    /**
     * Test getter and setter for ID
     */
    public void testing_getID_and_setID(){
        //Making Course
        Course course = new Course("ENSF",409);
        //Testing getter and setter
        course.setID(11);
        int result = course.getID();
        assertEquals(11, result);
    }

    @Test
    /**
     * Test getter and setter for courseName
     */
    public void testing_getCourseName_and_setCourseName(){
        //Making Course
        Course course = new Course("ENSF",409);
        //Testing getter and setter
        course.setCourseName("ENGG");
        String result = course.getCourseName();
        assertEquals("ENGG", result);
    }

    @Test
    /**
     * Test getter and setter for courseNum
     */
    public void testing_getCourseNum_and_setCourseNum(){
        //Making Course
        Course course = new Course("ENSF",409);
        //Testing getter and setter
        course.setCourseNum(233);
        int result = course.getCourseNum();
        assertEquals(233, result);
    }

    @Test
    /**
     * Test getter and setter for offeringList
     */
    public void testing_getOfferingList_and_setOfferingList(){
        //Making course
        Course course = new Course("ENSF",409);
        ArrayList<CourseOffering> testList = new ArrayList<CourseOffering>();
        testList.add(new CourseOffering(20000,course,1,100));
        //Testing getter and setter
        course.setOfferingList(testList);
        ArrayList<CourseOffering> result = course.getOfferingList();
        assertEquals(testList,result);
    }

    @Test
    /**
     * Test getter and setter for preReq
     */
    public void testing_getPreReq_and_setPreReq(){
        //Making course
        Course course = new Course("ENSF",409);
        ArrayList<Course> testList = new ArrayList<Course>();
        testList.add(new Course("ENGG",233));
        //Testing getter and setter
        course.setPreReq(testList);
        ArrayList<Course> result = course.getPreReq();
        assertEquals(testList,result);
    }

    @Test
    /**
     * Test method getNameNum()
     */
    public void testing_getNameNum(){
        //Making course
        Course course = new Course("ENSF",409);
        course.setCourseNum(233);
        course.setCourseName("ENGG");
        //Testing getNameNum()
        String result = course.getNameNum();
        assertEquals("ENGG 233", result);
    }

    @Test
    /**
     * Tests Constructor with the name and number set only
     */
    public void testing_CourseConstructor(){
        //Testing Constructor
        Course course = new Course("ENSF",409);
        String result = course.getNameNum();
        assertEquals("ENSF 409",result);
    }

    @Test
    /**
     * Tests Constructor with the name, number and ID set
     */
    public void testing_CourseConstructorWithID(){
        //Testing Constructor
        Course course = new Course("ENSF",409,20005);
        int resultID = course.getID();
        String resultNameNum = course.getNameNum();
        assertEquals("ENSF 409;20005",resultNameNum + ";" + resultID);
    }

    @Test
    /**
     * Tests method getCourseOfferingAt() which gets the offering at a specific index in the list
     */
    public void testing_getCourseOfferingAt(){
        //Making Course
        Course course = new Course("ENSF",409);
        ArrayList<CourseOffering> testList = new ArrayList<CourseOffering>();
        testList.add(new CourseOffering(20000,course,1,100));
        //The expected CourseOffering
        CourseOffering expected = new CourseOffering(20001,course,2,75);
        testList.add(expected);
        testList.add(new CourseOffering(20002,course,3,150));
        course.setOfferingList(testList);

        //Testing getCourseOfferingAt()
        CourseOffering result = course.getCourseOfferingAt(1);
        assertEquals(expected,result);
    }

    @Test
    /**
     * Tests method addOffering which adds an Course Offering to the list in Course
     */
    public void testing_addOffering(){
        //Making course and courseOfferings
        Course course = new Course("ENSF",409);
        CourseOffering testOffering1 = new CourseOffering(20000,course,1,100);
        CourseOffering testOffering2 = new CourseOffering(20001,course,2,75);
        ArrayList<CourseOffering> testList = new ArrayList<CourseOffering>();
        testList.add(testOffering1);
        testList.add(testOffering2);
        //Testing addOffering()
        course.addOffering(testOffering1);
        course.addOffering(testOffering2);
        ArrayList<CourseOffering> result = course.getOfferingList();
        assertEquals(testList,result);
    }

    @Test
    /**
     * Tests method addPreReq which adds an Course Pre-Requisite to the list in Course
     */
    public void testing_addPreReq(){
        //Making course and courseOfferings
        Course course = new Course("ENSF",409);
        Course testCourse1 = new Course("ENGG",233);
        Course testCourse2 = new Course("MATH",275);
        ArrayList<Course> testList = new ArrayList<Course>();
        testList.add(testCourse1);
        testList.add(testCourse2);
        //Testing addPreReq()
        course.addPreReq(testCourse1);
        course.addPreReq(testCourse2);
        ArrayList<Course> result = course.getPreReq();
        assertEquals(testList,result);
    }

    @Test
    /**
     * Tests method compareTo() which compares the name and number of two Courses
     */
    public void testing_compareTo(){
        //Making courses
        Course course1 = new Course("ENSF",409);
        Course course2 = new Course("ENGG",233);
        //Testing compareTo()
        int result = course1.compareTo(course2);
        assertEquals(12,result);
    }

    @Test
    /**
     * Tests method toString() which puts Course into a String
     */
    public void testing_toString(){
        //Making courses
        Course course = new Course("ENSF",409);
        ArrayList<CourseOffering> offeringList = new ArrayList<CourseOffering>();
        offeringList.add(new CourseOffering(20000,course,1,100));
        ArrayList<Course> preReqList = new ArrayList<Course>();
        preReqList.add(new Course("ENGG",233));
        course.setOfferingList(offeringList);
        course.setPreReq(preReqList);
        //Testing toString()
        String expected = "ENSF 409\n---------------------------------------\nAll course sections:\n\nCourse: ENSF 409\nSection Num: 1, section cap: 100\n\n" +
                "All course prerequisites:\nENGG 233\n---------------------------------------\n";
        assertEquals(expected,course.toString());
    }
}

