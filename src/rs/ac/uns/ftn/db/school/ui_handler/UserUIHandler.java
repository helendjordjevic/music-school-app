package rs.ac.uns.ftn.db.school.ui_handler;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.school.model.User;
import rs.ac.uns.ftn.db.school.service.UserService;

public class UserUIHandler {
	
	private static final UserService userService = new UserService();

	public void handleUserMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad korisnicima:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jednog korisnika");
			System.out.println("4 - Unos vise korisnika");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja korisnicima");

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
		System.out.println(User.getFormattedHeader());

		try {
			for (User user : userService.getAll()) {
				System.out.println(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Kraj");
	}

	private void showById() {
		System.out.println("ID korsnika: ");
		int id_sc = Integer.parseInt(MainUIHandler.sc.nextLine());
		try {
			User user = userService.getById(id_sc);
			System.out.println(User.getFormattedHeader());
			System.out.println(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
