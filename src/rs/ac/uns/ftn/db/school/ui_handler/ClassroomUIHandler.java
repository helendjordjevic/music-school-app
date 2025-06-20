package rs.ac.uns.ftn.db.school.ui_handler;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.school.model.Classroom;
import rs.ac.uns.ftn.db.school.service.ClassroomService;

public class ClassroomUIHandler {
	
	private static final ClassroomService classroomService = new ClassroomService();

	public void handleClassroomMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad ucionicama:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jedne ucionice");
			System.out.println("4 - Unos vise ucionica");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja ucionicama");

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
		System.out.println(Classroom.getFormattedHeader());

		try {
			for (Classroom classroom : classroomService.getAll()) {
				System.out.println(classroom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showById() {
		System.out.println("ID ucionice: ");
		int id_sc = Integer.parseInt(MainUIHandler.sc.nextLine());
		try {
			Classroom classroom = classroomService.getById(id_sc);
			System.out.println(Classroom.getFormattedHeader());
			System.out.println(classroom);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
