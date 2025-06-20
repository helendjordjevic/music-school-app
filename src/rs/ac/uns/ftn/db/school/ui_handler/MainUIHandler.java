package rs.ac.uns.ftn.db.school.ui_handler;

import java.util.Scanner;

public class MainUIHandler {
	
	public static Scanner sc = new Scanner(System.in);

	private final ClassroomUIHandler classroomUIHandler = new ClassroomUIHandler();
	private final CourseUIHandler courseUIHandler = new CourseUIHandler();
	private final InstrumentUIHandler instrumentUIHandler = new InstrumentUIHandler();
	private final SessionUIHandler sessionUIHandler = new SessionUIHandler();
	private final UserUIHandler userUIHandler = new UserUIHandler();

	
	public void handleMainMenu() {

		String answer;
		do {
			System.out.println("\nOdaberite opciju:");
			System.out.println("1 - Rukovanje ucionicama");
			System.out.println("2 - Rukovanje kursevima");
			System.out.println("3 - Rukovanje instrumentima");
			System.out.println("4 - Rukovanje terminima");
			System.out.println("5 - Rukovanje korisnicima");
	
			System.out.println("X - Izlazak iz programa");

			answer = sc.nextLine();

			switch (answer) {
			case "1":
				classroomUIHandler.handleClassroomMenu();
				break;
				
			case "2":
				courseUIHandler.handleCourseMenu();
				break;
				
			case "3":
				instrumentUIHandler.handleInstrumentMenu();
				break;
				
			case "4":
				sessionUIHandler.handleSessionMenu();
				break;
				
			case "5":
				userUIHandler.handleUserMenu();
				break;
				
			}

		} while (!answer.equalsIgnoreCase("X"));

		sc.close();
	}


}
