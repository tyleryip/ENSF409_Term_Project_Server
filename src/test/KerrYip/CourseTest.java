package test.KerrYip;

import com.KerrYip.Model.Course;
import com.KerrYip.Model.CourseOffering;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CourseTest {

    @Test
    public void CourseTest(){
        testing_getCourseName_and_setCourseName();
        testing_getCourseNum_and_setCourseNum();
        testing_getNameNum();
    }

    @Test
    public void testing_getCourseName_and_setCourseName(){
        Course course = new Course("ENSF",409);
        course.setCourseName("ENGG");
        String result = course.getCourseName();
        assertEquals("ENGG", result);
    }

    @Test
    public void testing_getCourseNum_and_setCourseNum(){
        Course course = new Course("ENSF",409);
        course.setCourseNum(233);
        int result = course.getCourseNum();
        assertEquals(233, result);
    }

    @Test
    public void testing_getOfferingList_and_setOfferingList(){
        Course course = new Course("ENSF",409);
        ArrayList<CourseOffering> testList = new ArrayList<CourseOffering>();
        testList.add(new CourseOffering(20000,course,1,100));
        course.setOfferingList(testList);
        ArrayList<CourseOffering> result = course.getOfferingList();
        assertEquals(233, result);
    }

    @Test
    public void testing_getNameNum(){
        Course course = new Course("ENSF",409);
        String result = course.getNameNum();
        assertEquals("ENSF 409", result);
    }
}
