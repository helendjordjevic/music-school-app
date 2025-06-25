package rs.ac.uns.ftn.db.school.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import rs.ac.uns.ftn.db.school.dto.CourseApplicationDTO;
import rs.ac.uns.ftn.db.school.dto.StudentApplicationCountDTO;
import rs.ac.uns.ftn.db.school.model.Application;

public interface ApplicationDAO extends CRUDDao<Application, Integer> {
	public boolean deleteById(Integer korisnikId, Integer kursId) throws SQLException;
	public boolean existsByIdTransactional(Connection connection, Integer korisnikId, Integer kursId) throws SQLException;

	List<CourseApplicationDTO> findAllWithCourseNames() throws SQLException;
	List<StudentApplicationCountDTO> countApplicationsPerStudent() throws SQLException;

	List<Object[]> getStudentsPerCourseAndInstrument() throws SQLException;
	Map<Integer, Integer> getMostUsedClassroomPerCourse() throws SQLException;
	Map<Integer, String> getIdNameMap(String tableName, String idColumn, String nameColumn) throws SQLException;


}
