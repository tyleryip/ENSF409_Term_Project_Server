package com.KerrYip.Model;
import java.util.ArrayList;
import java.util.Iterator;

public class StudentManager {

	private ArrayList<Student> studentList;
	
	public StudentManager() {
		studentList = new ArrayList<Student>();
		readStudentsFromDataBase();
	}
	
	public void readStudentsFromDataBase() {
		//Simulating students
		studentList.add(new Student ("Sara", 1));
		studentList.add(new Student ("Sam", 2));
		studentList.add(new Student ("Tom", 3));
		studentList.add(new Student ("David", 4));
		studentList.add(new Student ("John", 5));
		studentList.add(new Student ("Jenny", 6));
		studentList.add(new Student ("Alex", 7));
		studentList.add(new Student ("Megan", 8));
		studentList.add(new Student ("Mike", 8));
		
	}

	public Student searchStudent(String name) {
		Iterator<Student> itr = studentList.iterator();
		while(itr.hasNext()){
			Student check = itr.next();
			if(check.getStudentName().equalsIgnoreCase(name)) {
				return check;
			}
		}
		System.out.println("Could not find student with name: " + name);
		return null;
		
	}
	
	public void addStudent(String name) {
		int id = studentList.size();
		Student newStudent = new Student(name, id+1);
		studentList.add(newStudent);
		System.out.println("New student " + name + " created successfully with an id of: " + id);
	}
}
