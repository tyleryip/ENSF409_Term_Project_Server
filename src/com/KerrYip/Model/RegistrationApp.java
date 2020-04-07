package com.KerrYip.Model;
import java.util.Scanner;

public class RegistrationApp {
	
	public static Scanner scan = new Scanner(System.in);
	
	public static void main (String [] args) {

		CourseCatalogue cat = new CourseCatalogue();
		StudentManager SM = new StudentManager();
		
		//Simulating adding students to courses to show functionalities of the menu below
		//NOTE: THESE LINES OF CODE ARE PURELY FOR SIMULATION, THEY DONT ADD FUNCTIONALITY
		System.out.println("[System] Loading in simulated student profiles...");
		
		CourseOffering a = cat.searchCat("ENGG", 233).getCourseOfferingAt(0);
		Registration r = new Registration();
		r.completeRegistration(SM.searchStudent("Sara"), a);
		
		a = cat.searchCat("ENGG", 233).getCourseOfferingAt(0);
		r = new Registration();
		r.completeRegistration(SM.searchStudent("Sam"), a);
		
		a = cat.searchCat("PHYS", 259).getCourseOfferingAt(0);
		r = new Registration();
		r.completeRegistration(SM.searchStudent("Sam"), a);
		
		a = cat.searchCat("PHYS", 259).getCourseOfferingAt(0);
		r = new Registration();
		r.completeRegistration(SM.searchStudent("David"), a);
		
		a = cat.searchCat("PHYS", 259).getCourseOfferingAt(0);
		r = new Registration();
		r.completeRegistration(SM.searchStudent("Tom"), a);
		
		a = cat.searchCat("PHYS", 259).getCourseOfferingAt(0);
		r = new Registration();
		r.completeRegistration(SM.searchStudent("Megan"), a);
		
		a = cat.searchCat("PHYS", 259).getCourseOfferingAt(0);
		r = new Registration();
		r.completeRegistration(SM.searchStudent("Jenny"), a);
		
		a = cat.searchCat("PHYS", 259).getCourseOfferingAt(0);
		r = new Registration();
		r.completeRegistration(SM.searchStudent("Alex"), a);
		
		a = cat.searchCat("PHYS", 259).getCourseOfferingAt(0);
		r = new Registration();
		r.completeRegistration(SM.searchStudent("John"), a);
		
		a = cat.searchCat("PHYS", 259).getCourseOfferingAt(0);
		r = new Registration();
		r.completeRegistration(SM.searchStudent("Sara"), a);
		
		a = cat.searchCat("PHYS", 259).getCourseOfferingAt(0);
		r = new Registration();
		r.completeRegistration(SM.searchStudent("Mike"), a);
		
		System.out.println("[System] Finished loading simulated student profiles...\n");
		
		//The following code deals with all the input and menu functionality. 
		//Note that none of the code in the menu options actually has logic for the course catalogue and other classes.
		//Any logic in the options only deals with taking proper input from the user and then takes the input and calls other back end methods.
		int menu = 0;
		int selection = 0;
		
		do { 
			//Signing in to an existing student name will utilize the existing student object's registrations
			System.out.println("Please make a sign-in selection from the following options:");
			System.out.println("--------------------------------------------------");
			System.out.println("1 - Student Sign-In");
			System.out.println("2 - Admin Sign-In");
			System.out.println("3 - Quit");
			System.out.println("--------------------------------------------------");
			selection = scan.nextInt();
			switch(selection) {
			case 1: 
				scan.nextLine();
				System.out.println("Please enter your name:");
				String name = scan.nextLine();
				Student current = SM.searchStudent(name);
				if(current == null) { //If this student does not exist, creates a new student and logs in
					SM.addStudent(name);
					current = SM.searchStudent(name);
					pressEnter();
				}
				else {
					System.out.println(name + " was successfully signed-in to the registration system");
				}
				do {
					System.out.println("Please make a selection from the following options:");
					System.out.println("--------------------------------------------------");
					System.out.println("1 - Search for a course");
					System.out.println("2 - View all courses in the course catalogue");
					System.out.println("3 - View all your courses");
					System.out.println("4 - Add a course to your current course load");
					System.out.println("5 - Remove a course to your current course load");
					System.out.println("6 - Logout");
					System.out.println("--------------------------------------------------");
					
					menu = scan.nextInt();
					
					switch(menu) {
					case 1:
						//Search catalogue courses
						scan.nextLine();
						System.out.println("Please enter the name and number of the course you would like to search for (ex. ENGG 233)");
						cat.searchCourseCatalogue(scan.nextLine());
						pressEnter();
						break;
						
					case 2:
						//View all courses in the catalogue
						System.out.println(cat);
						pressEnter();
						break;
						
					case 3:
						//View all your current courses
						for(Registration c: current.getStudentRegList()) {
							System.out.println(c.toString());
						}
						pressEnter();
						break;
						
					case 4:
						//Add a course to your course load
						scan.nextLine();
						System.out.println("Please enter the name of the course you would like to add (ex. ENGG 233)");
						String courseNameNum = scan.nextLine();
						
						Course add = cat.searchCat(courseNameNum);
						if(add == null) {
							break;
						}
						System.out.println(add.toString());
						System.out.println("Please enter the section of the course you would like to attend (ex. 1)");
						CourseOffering offering = add.getCourseOfferingAt(scan.nextInt()-1);
						if(offering == null) {
							break;
						}
						Registration reg = new Registration();
						reg.completeRegistration(current, offering);
						pressEnter();
						break;
						
					case 5:
						//Remove a course from your course load
						scan.nextLine();
						System.out.println("Please enter the name of the course you would like to remove (ex. ENGG 233)");
						String courseToRemoveName = scan.nextLine();

						Course courseRemove = cat.searchCat(courseToRemoveName);
						if(courseRemove == null) {
							break;
						}
						Registration removeReg = current.searchStudentReg(courseRemove);
						if(removeReg == null) {
							break;
						}
						current.getStudentRegList().remove(removeReg);
						System.out.println("Course was removed successfully");
						pressEnter();
						break;
						
					}
				} while(menu != 6);
				current = null;
				System.out.println("User loggged out successfully");
				pressEnter();
				break;
				
			case 2: //Administrator sign on
				System.out.println("Administator was successfully signed-in to the registration system");
				do {
					System.out.println("Please make a selection from the following options:");
					System.out.println("--------------------------------------------------");
					System.out.println("1 - Search for a course");
					System.out.println("2 - Add a course to the students courses");
					System.out.println("3 - Remove a course from the students courses");
					System.out.println("4 - View all courses in the course catalogue");
					System.out.println("5 - View all courses taken by a student");
					System.out.println("6 - Start semester (try to run all courses, if less than 8 students, will cancel that course)");
					System.out.println("7 - Quit");
					System.out.println("--------------------------------------------------");
					
					//Place holders for accepting inputs into the menu
					String input = "";
					String input2 = "";
					int inputNum = 0;
					int inputNum2 = 0;
					Course placeHolder;
					
					menu = scan.nextInt();
					
					switch(menu) {
					case 1:
						//Search catalogue courses
						scan.nextLine();
						System.out.println("Please enter the name and number of the course you would like to search for (ex. ENGG 233)");
						cat.searchCourseCatalogue(scan.nextLine());
						pressEnter();
						break;
						
					case 2:
						//Add course to available student courses
						scan.nextLine();
						System.out.println("Please enter the name and number of the course you would like to add: (ex. ENGG 233)");
						input = scan.nextLine();
						placeHolder = cat.addCourse(input);
						if(placeHolder == null) {
							break;
						}
						System.out.println("Please enter the number of prequisites " + input + " has:");
						inputNum = scan.nextInt();
						for(int i = 0; i<inputNum; i++) {
							scan.nextLine();
							System.out.println("Please enter the name and number of the course you would like to add as a prequisite: (ex. ENGG 233)");
							input2 = scan.nextLine();
							cat.addPreRequisite(input ,input2);
						}
						System.out.println("Please enter the number of course offerings " + input + " has:");
						inputNum = scan.nextInt();
						for(int i = 0; i<inputNum; i++) {
							scan.nextLine();
							System.out.println("Please enter the number capacity of section " + (i+1) + " has:");
							inputNum2 = scan.nextInt();
							cat.createCourseOffering(placeHolder, (i+1), inputNum2);
						}
						pressEnter();
						break;
						
					case 3:
						//Remove courses from available students courses
						scan.nextLine();
						System.out.println("Please enter the name and number of the course you would like to remove: (ex. ENGG 233)");
						input = scan.nextLine();
						cat.removeCourse(input);
						pressEnter();
						break;
						
					case 4:
						//View all courses in catalogue
						System.out.println(cat);
						pressEnter();
						break;
						
					case 5:
						//view all courses taken by a student
						scan.nextLine();
						System.out.println("Please enter the name of the student you would like to view courses for");
						String searchName = scan.nextLine();
						Student thisStudent = SM.searchStudent(searchName);
						if(thisStudent == null) {
							break;
						}
						thisStudent.toString();
						for(Registration c: thisStudent.getStudentRegList()) {
							System.out.println(c.toString());
						}
						pressEnter();
						break;
						
					case 6: 
						//When testing this function, by default PHYS 259 section 1 will be the only class that will run as it is the only class with 8 students
						for(Course c: cat.getCourseList()) {
							for(CourseOffering o: c.getOfferingList()) {
								if(o.getOfferingRegList().size() < 8) {
									System.out.println(c.getCourseName() + " " + c.getCourseNum() + " section " + o.getSecNum() + " has less than 8 people, the section will be cancelled.");
								}
								else {
									System.out.println(c.getCourseName() + " " + c.getCourseNum() + " section " + o.getSecNum() + " has started classes successfully.");
								}
							}
						}
						pressEnter();
						break;
					}
					
				} while(menu != 7);
				System.out.println("User loggged out successfully");
				pressEnter();
				break;
			}
			
		} while(selection != 3);
		
		System.out.println("Application terminated, good bye!");
	}
	
	/**
	 * Prompts the user to press enter to continue
	 */
	public static void pressEnter() {
		System.out.println("PRESS ENTER TO CONTINUE");
		scan.nextLine();
	}

}
