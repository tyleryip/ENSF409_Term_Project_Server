package test.KerrYip;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;
import com.KerrYip.Model.Registration;
import com.KerrYip.Model.Student;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StudentTest {
    @Test
    /**
     * Tests all methods in Student
     */
    public void StudentTest(){
        //Testing getters and setters
        testing_getStudentId_and_setStudentId();
        testing_getStudentName_and_setStudentName();
        testing_getPassword_and_setPassword();
        testing_setActive_and_isActive();
        testing_getStudentRegList_and_setStudentRegList();

        //Testing constructors
        testing_StudentConstructor();

        //Testing methods
        testing_addRegistration();
        testing_searchStudentReg();
        testing_toString();
    }

    @Test
    /**
     * Test getter and setter for studentId
     */
    public void testing_getStudentId_and_setStudentId(){
        //Making Student
        Student student = new Student("Tyler",16,"password");
        //Testing getter and setter
        student.setStudentId(20);
        int result = student.getStudentId();
        assertEquals(20, result);
    }

    @Test
    /**
     * Test getter and setter for studentName
     */
    public void testing_getStudentName_and_setStudentName(){
        //Making Student
        Student student = new Student("Tyler",16,"password");
        //Testing getter and setter
        student.setStudentName("Will");
        String result = student.getStudentName();
        assertEquals("Will", result);
    }

    @Test
    /**
     * Test getter and setter for courseNum
     */
    public void testing_getPassword_and_setPassword(){
        //Making Student
        Student student = new Student("Tyler",16,"password");
        //Testing getter and setter
        student.setPassword("balloon12");
        String result = student.getPassword();
        assertEquals("balloon12", result);
    }

    @Test
    /**
     * Test getter and setter for offeringList
     */
    public void testing_setActive_and_isActive(){
        //Making Student
        Student student = new Student("Tyler",16,"password");
        //Testing getter and setter
        student.setActive(true);
        boolean result = student.isActive();
        assertEquals(true, result);
    }

    @Test
    /**
     * Test getter and setter for preReq
     */
    public void testing_getStudentRegList_and_setStudentRegList(){
        //Making student
        Student student = new Student("Tyler",16,"password");
        ArrayList<Registration> testList = new ArrayList<Registration>();
        testList.add(new Registration(40001,'C'));
        //Testing getter and setter
        student.setStudentRegList(testList);
        ArrayList<Registration> result = student.getStudentRegList();
        assertEquals(testList,result);
    }

    @Test
    /**
     * Tests Constructor for Student
     */
    public void testing_StudentConstructor(){
        //Testing Constructor
        Student student = new Student("Tyler",16,"password");
        String result = student.getStudentId() + ";" + student.getStudentName() + ";" + student.getPassword();
        assertEquals("16;Tyler;password",result);
    }

    @Test
    /**
     * Tests method addRegistration which adds an Registration to the list in Student
     */
    public void testing_addRegistration(){
        //Making student and registrations
        Student student = new Student("Tyler",16,"password");
        Registration testRegistration1 = new Registration(40001,'C');
        Registration testRegistration2 = new Registration(40002,'A');
        ArrayList<Registration> testList = new ArrayList<Registration>();
        testList.add(testRegistration1);
        testList.add(testRegistration2);
        //Testing addRegistration()
        student.addRegistration(testRegistration1);
        student.addRegistration(testRegistration2);
        ArrayList<Registration> result = student.getStudentRegList();
        assertEquals(testList,result);
    }

    @Test
    /**
     * Tests method searchStudentReg() which searches the Student Reg List if they have the course
     */
    public void testing_searchStudentReg(){
        //Makes student and registration
        Student student = new Student("Tyler",16,"password");
        Course course = new Course("ENSF",409);
        CourseOffering courseOffering = new CourseOffering(20000,course,1,100);
        Registration testRegistration = new Registration(40001,'C');
        testRegistration.completeRegistration(student,courseOffering);
        //Testing searchStudentReg()
        Course search = new Course("ENSF",409);
        Registration result = student.searchStudentReg(search);
        assertEquals(testRegistration,result);
    }

    @Test
    /**
     * Tests method toString() which puts Student into a String
     */
    public void testing_toString(){
        //Making Student
        Student student = new Student("Tyler",16,"password");
        //Testing toString()
        String expected = "Student Name : Tyler\nStudent Id: 16\n\n";
        assertEquals(expected,student.toString());
    }
}
