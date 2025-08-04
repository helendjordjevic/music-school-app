package rs.ac.uns.ftn.db.school.ui_handler;

import rs.ac.uns.ftn.db.school.model.Course;
import rs.ac.uns.ftn.db.school.model.User;
import rs.ac.uns.ftn.db.school.model.enums.UserType;
import rs.ac.uns.ftn.db.school.service.ProfessorCourseService;

import java.sql.Connection;
import java.sql.SQLException;

public class ProfessorCourseUIHandler {

    private static final ProfessorCourseService service = new ProfessorCourseService();

    public void handleProfessorCourseMenu(Connection conn) {
        String answer;
        do {
            System.out.println("\nOdaberite opciju za rad sa profesorom i kursevima:");
            System.out.println("1 - Dodavanje profesora i kursa");
            System.out.println("X - Izlazak");

            answer = MainUIHandler.sc.nextLine();

            switch (answer) {
                case "1":
                    insertProfessorAndCourse(conn);
                    break;
                case "2":
                    break;
                case "3":
                    break;
            }
        } while (!answer.equalsIgnoreCase("X"));
    }

    private void insertProfessorAndCourse(Connection conn) {
        try {
            

            System.out.println("Ime profesora:");
            String ime = MainUIHandler.sc.nextLine();

            System.out.println("Prezime profesora:");
            String prezime = MainUIHandler.sc.nextLine();

            System.out.println("Email profesora:");
            String email = MainUIHandler.sc.nextLine();

            System.out.println("ID muzičke škole:");
            int schoolId = Integer.parseInt(MainUIHandler.sc.nextLine());

            System.out.println("Stručnost profesora:");
            String strucnost = MainUIHandler.sc.nextLine();

            User professor = new User();
            professor.setId(0); 
            professor.setName(ime);
            professor.setLastname(prezime);
            professor.setEmail(email);
            professor.setUserType(UserType.PROFESSOR);
            professor.setSchoolId(schoolId);
            professor.setProfessorId(0); 
            professor.setExpertise(strucnost);

            professor.setStudentId(0);
            professor.setMentorId(0);
            professor.setEnrollmentDate(null);

            System.out.println("Naziv kursa:");
            String naziv = MainUIHandler.sc.nextLine();

            System.out.println("Trajanje kursa (u časovima):");
            int trajanje = Integer.parseInt(MainUIHandler.sc.nextLine());

            Course course = new Course();
            course.setId(0);  
            course.setName(naziv);
            course.setDuration(trajanje);
            
            course.setProfessorId(0);

            boolean uspeh = service.insertProfessorAndCourse(conn, professor, course);

            if (uspeh) {
                System.out.println("Profesor i kurs uspešno dodati.");
                System.out.println("Generisani ID profesora: " + professor.getId());
                System.out.println("Generisani ID profesora za kurs (professorId): " + professor.getProfessorId());
                System.out.println("Generisani ID kursa: " + course.getId());
                System.out.println("Naziv kursa: " + course.getName());
            } else {
                System.out.println("Došlo je do greške prilikom dodavanja.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Neispravan unos broja.");
        } catch (SQLException e) {
            System.err.println("Greška pri radu sa bazom: " + e.getMessage());
        }
    }

}
