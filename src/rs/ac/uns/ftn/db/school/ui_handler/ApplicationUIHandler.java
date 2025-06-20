package rs.ac.uns.ftn.db.school.ui_handler;

import java.sql.SQLException;

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
			System.out.println("3 - Unos jedne prijave");
			System.out.println("4 - Unos vise prijava");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja prijavama");

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

}
