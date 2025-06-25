package rs.ac.uns.ftn.db.school.service;

import rs.ac.uns.ftn.db.school.dao.impl.CourseDAOImpl;
import rs.ac.uns.ftn.db.school.dao.impl.UserDAOImpl;
import rs.ac.uns.ftn.db.school.model.Course;
import rs.ac.uns.ftn.db.school.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class ProfessorCourseService {

    private final UserDAOImpl userDAO = new UserDAOImpl();
    private final CourseDAOImpl courseDAO = new CourseDAOImpl();

    public boolean insertProfessorAndCourse(Connection connection, User professor, Course course) throws SQLException {
        boolean success = false;

        try {
            connection.setAutoCommit(false);

            // Sačuvaj profesora, on će sam da dobije id i professorId (jer trigger i getNextProfessorId rade svoj deo)
            boolean savedProfessor = userDAO.saveTransactional(connection, professor);

            if (!savedProfessor) {
                connection.rollback();
                return false;
            }

            // Sada postavi professorId u kurs na novog profesora
            course.setProfessorId(professor.getProfessorId());

            // Sačuvaj kurs
            boolean savedCourse = courseDAO.saveTransactional(connection, course);

            if (!savedCourse) {
                connection.rollback();
                return false;
            }

            connection.commit();
            success = true;
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(true);
        }

        return success;
    }
}
