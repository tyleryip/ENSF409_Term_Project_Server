package test.KerrYip;

import com.KerrYip.Model.Course;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CourseTest {

	@Test
	public void CourseTest() {
		should_create_ENSF_409();
	}

	@Test
	public void should_create_ENSF_409() {
		Course course = new Course("ENSF", 409);
		String output = course.getNameNum();
		assertEquals("ENSF 409", output);
	}
}
