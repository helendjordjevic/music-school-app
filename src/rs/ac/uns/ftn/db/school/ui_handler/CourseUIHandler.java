package rs.ac.uns.ftn.db.school.ui_handler;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.school.model.Course;
import rs.ac.uns.ftn.db.school.service.CourseService;

public class CourseUIHandler {
	
	private static final CourseService courseService = new CourseService();

	public void handleCourseMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad kursevima:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jednog kursa");
			System.out.println("4 - Unos vise kurseva");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja kursevima");

			answer = MainUIHandler.sc.nextLine();

			switch (answer) {
			case "1":
				showAll();
				break;
			case "2":
				showById();
				break;
			case "3":
				//TODO: implementirati
				break;
			case "4":
				//TODO: implementirati
				break;
			case "5":
				//TODO: implementirati
				break;
			case "6":
				//TODO: implementirati
				break;
			}

		} while (!answer.equalsIgnoreCase("X"));
	}

	private void showAll() {
		System.out.println(Course.getFormattedHeader());

		try {
			for (Course course : courseService.getAll()) {
				System.out.println(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showById() {
		System.out.println("ID kursa: ");
		int id_sc = Integer.parseInt(MainUIHandler.sc.nextLine());
		try {
			Course course = courseService.getById(id_sc);
			System.out.println(Course.getFormattedHeader());
			System.out.println(course);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
