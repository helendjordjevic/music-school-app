package rs.ac.uns.ftn.db.school.ui_handler;

import java.sql.SQLException;
import java.util.List;

import rs.ac.uns.ftn.db.school.model.Application;
import rs.ac.uns.ftn.db.school.service.ApplicationService;

public class ApplicationUIHandler {
	
	private static final ApplicationService applicationService = new ApplicationService();

	public void handleApplicationMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad prijavama:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Prikaz prijava po kursevima");
			System.out.println("4 - Prikaz broja prijava po studentu");
			System.out.println("5 - Statistika kurseva");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja prijavama");

			answer = MainUIHandler.sc.nextLine();

			switch (answer) {
			case "1":
				showAll();
				break;
			case "2":
				showById(); // ne radi
				break;
			case "3":
				showCourseApplications();
				break;
			case "4":
				showApplicationCountPerStudent();
				break;
			case "5":
				break;
			case "6":
				//TODO: implementirati
				break;
			}

		} while (!answer.equalsIgnoreCase("X"));
	}

	private void showAll() {
		System.out.println(Application.getFormattedHeader());

		try {
			for (Application application : applicationService.getAll()) {
				System.out.println(application);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showById() {
		System.out.println("ID prijave: ");
		int id_sc = Integer.parseInt(MainUIHandler.sc.nextLine());
		try {
			Application application = applicationService.getById(id_sc);
			System.out.println(Application.getFormattedHeader());
			System.out.println(application);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showCourseApplications() {
		System.out.println("\n=== Prijave studenata na kurseve ===");
		try {
			applicationService.getAllCourseApplications()
					.forEach(System.out::println);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showApplicationCountPerStudent() {
		System.out.println("\n=== Broj prijava po studentu ===");
		try {
			applicationService.getApplicationCountPerStudent()
					.forEach(System.out::println);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}





}
