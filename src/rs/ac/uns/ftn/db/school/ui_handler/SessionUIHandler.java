package rs.ac.uns.ftn.db.school.ui_handler;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.school.model.Session;
import rs.ac.uns.ftn.db.school.service.SessionService;

public class SessionUIHandler {
	
	private static final SessionService sessionService = new SessionService();

	public void handleSessionMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad terminima:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jednog termina");
			System.out.println("4 - Unos vise termina");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja terminima");

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
		System.out.println(Session.getFormattedHeader());

		try {
			for (Session session : sessionService.getAll()) {
				System.out.println(session);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showById() {
		System.out.println("ID termina: ");
		int id_sc = Integer.parseInt(MainUIHandler.sc.nextLine());
		try {
			Session session = sessionService.getById(id_sc);
			System.out.println(Session.getFormattedHeader());
			System.out.println(session);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
