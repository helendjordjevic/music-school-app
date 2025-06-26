package rs.ac.uns.ftn.db.school.ui_handler;

import java.sql.SQLException;
import java.util.List;

import rs.ac.uns.ftn.db.school.dto.CourseApplicationCountDTO;
import rs.ac.uns.ftn.db.school.dto.CourseFullReportDTO;
import rs.ac.uns.ftn.db.school.dto.CourseInstrumentDTO;
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
			System.out.println("3 - Prikaz svih kurseva sa njihovim profesorima");
			System.out.println("4 - Prikaz broja prihava po kursu");
			System.out.println("5 - Prikaz najcesce koriscenih instrumenata po kursu");
			System.out.println("6 - Prikaz kombinovanog izveštaja (profesor, broj prijava, najčešći instrument)");


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
				showCoursesWithProfessors();
				break;
			case "4":
				showCourseApplicationCounts();
				break;
			case "5":
				showMostUsedInstrumentsPerCourse();
				break;
			case "6":
				showFullCourseReport();
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

	private void showCoursesWithProfessors() {
		System.out.printf("%-8s %-30s %-15s %-15s %s\n", "ID Kursa", "Naziv Kursa", "Ime Profesora", "Prezime", "ID Prof");
		System.out.println("--------------------------------------------------------------------------");

		try {
			var list = courseService.getCoursesWithProfessors();
			for (var dto : list) {
				System.out.println(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void showCourseApplicationCounts() {
		System.out.println("Broj prijava po kursu:");
		try {
			List<CourseApplicationCountDTO> list = courseService.getCourseApplicationCounts();
			for (CourseApplicationCountDTO dto : list) {
				System.out.println(dto);
			}
		} catch (SQLException e) {
			System.err.println("Greška prilikom dohvata broja prijava: " + e.getMessage());
			e.printStackTrace();
		}
	}
	public void showMostUsedInstrumentsPerCourse() {
		try {
			List<CourseInstrumentDTO> list = courseService.getMostUsedInstrumentPerCourse();

			System.out.println("Najčešći instrumenti po kursu:");
			for (CourseInstrumentDTO dto : list) {
				System.out.println(dto);
			}
		} catch (SQLException e) {
			System.err.println("Greška pri učitavanju podataka: " + e.getMessage());
		}
	}

	private void showFullCourseReport() {
		try {
			List<CourseFullReportDTO> list = courseService.getFullCourseReport();
			for (CourseFullReportDTO dto : list) {
				System.out.println(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
