package rs.ac.uns.ftn.db.school.ui_handler;

import java.sql.SQLException;

import rs.ac.uns.ftn.db.school.model.Instrument;
import rs.ac.uns.ftn.db.school.service.InstrumentService;

public class InstrumentUIHandler {
	
	private static final InstrumentService instrumentService = new InstrumentService();

	public void handleInstrumentMenu() {
		String answer;
		do {
			System.out.println("\nOdaberite opciju za rad nad instrumentima:");
			System.out.println("1 - Prikaz svih");
			System.out.println("2 - Prikaz po identifikatoru");
			System.out.println("3 - Unos jednog instrumenta");
			System.out.println("4 - Unos vise instrumenata");
			System.out.println("5 - Izmena po identifikatoru");
			System.out.println("6 - Brisanje po identifikatoru");
			System.out.println("X - Izlazak iz rukovanja instrumentima");

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
		System.out.println(Instrument.getFormattedHeader());

		try {
			for (Instrument instrument : instrumentService.getAll()) {
				System.out.println(instrument);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showById() {
		System.out.println("ID instrumenta: ");
		int id_sc = Integer.parseInt(MainUIHandler.sc.nextLine());
		try {
			Instrument instrument = instrumentService.getById(id_sc);
			System.out.println(Instrument.getFormattedHeader());
			System.out.println(instrument);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
