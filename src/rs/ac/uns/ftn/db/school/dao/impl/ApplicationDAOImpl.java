package rs.ac.uns.ftn.db.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rs.ac.uns.ftn.db.school.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.school.dao.ApplicationDAO;
import rs.ac.uns.ftn.db.school.dto.CourseApplicationDTO;
import rs.ac.uns.ftn.db.school.dto.StudentApplicationCountDTO;
import rs.ac.uns.ftn.db.school.model.Application;

public class ApplicationDAOImpl implements ApplicationDAO{
	
	@Override
	public int count() throws SQLException {
		String query = "select count(*) from PRIJAVA";

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next())
				return resultSet.getInt(1);
			else {
				return -1;
			}
		}
	}

	@Override
	public boolean delete(Application application) throws SQLException {
		return false;
	}
	@Override
	public boolean deleteById(Integer id) throws SQLException {
		return false;
	}



	@Override
	public int deleteAll() throws SQLException {
		return 0;
	}

	@Override
	public boolean deleteById(Integer korisnikId, Integer kursId) throws SQLException {
		String query = "delete from PRIJAVA where KORISNIK_ID_ST=? and KURS_ID_KU=?";
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, korisnikId);
			preparedStatement.setInt(2, kursId);

			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}

	}

	@Override
	public boolean existsById(Integer id) throws SQLException {
		return false;
	}

	public boolean existsByIdTransactional(Connection connection, Integer korisnikId, Integer kursId) throws SQLException {
		String query = "select * from PRIJAVA where KORISNIK_ID_ST=?  and KURS_ID_KU=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, korisnikId);
			preparedStatement.setInt(2, kursId);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.isBeforeFirst();
			}
		}
	}

	@Override
	public Iterable<Application> findAll() throws SQLException {
		String query = "select KURS_ID_KU, INSTRUMENT_ID_INS, KORISNIK_ID_ST from PRIJAVA";
		List<Application> applications = new ArrayList<Application>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				
				Application application = new Application(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3));
				
				applications.add(application);
			}

		}
		return applications;
	}

	@Override
	public Iterable<Application> findAllById(Iterable<Integer> ids) throws SQLException {
		return null;
	}

	@Override
	public Application findById(Integer id) throws SQLException {
		return null;
	}

	@Override
	public boolean save(Application entity) throws SQLException {
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			return saveTransactional(connection, entity);
		}
	}

	@Override
	public int saveAll(Iterable<Application> entities) throws SQLException {
		return 0;
	}

	private boolean saveTransactional(Connection connection, Application application) throws SQLException {

		String insertCommand = "insert into PRIJAVA (KURS_ID_KU, INSTRUMENT_ID_INS, KORISNIK_ID_ST) values (?,?,?)";
		if(existsByIdTransactional(connection, application.getStudentId(),  application.getCourseId())) {
			return false;
			
		}
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(insertCommand)) {
			int i = 1;
			preparedStatement.setInt(i++, application.getCourseId());
			preparedStatement.setInt(i++, application.getInstrumentId());
			preparedStatement.setInt(i++, application.getStudentId());
			int rowsAffected =  preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}
	}

	@Override
	public List<CourseApplicationDTO> findAllWithCourseNames() throws SQLException {
		List<CourseApplicationDTO> result = new ArrayList<>();

		String query = """
        SELECT 
            k.ID_KU,
            k.NAZ_KU,
            p.KORISNIK_ID_ST
        FROM KURS k
        JOIN PRIJAVA p ON p.KURS_ID_KU = k.ID_KU
        ORDER BY p.KORISNIK_ID_ST, k.ID_KU
        """;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				int courseId = resultSet.getInt("ID_KU");
				String courseName = resultSet.getString("NAZ_KU");
				int studentId = resultSet.getInt("KORISNIK_ID_ST");

				result.add(new CourseApplicationDTO(courseId, courseName, studentId));
			}
		}

		return result;
	}

	@Override
	public List<StudentApplicationCountDTO> countApplicationsPerStudent() throws SQLException {
		List<StudentApplicationCountDTO> result = new ArrayList<>();

		String query = """
        SELECT 
            KORISNIK_ID_ST AS student_id,
            COUNT(*) AS broj_prijava
        FROM PRIJAVA
        GROUP BY KORISNIK_ID_ST
        ORDER BY broj_prijava DESC
        """;

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				int studentId = resultSet.getInt("student_id");
				int count = resultSet.getInt("broj_prijava");

				result.add(new StudentApplicationCountDTO(studentId, count));
			}
		}

		return result;
	}

	public List<Object[]> getStudentsPerCourseAndInstrument() throws SQLException {
		String query = """
        SELECT KURS_ID_KU, INSTRUMENT_ID_INS, COUNT(DISTINCT KORISNIK_ID_ST)
        FROM PRIJAVA
        GROUP BY KURS_ID_KU, INSTRUMENT_ID_INS
    """;

		List<Object[]> result = new ArrayList<>();

		try (Connection conn = ConnectionUtil_HikariCP.getConnection();
			 PreparedStatement ps = conn.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				result.add(new Object[] {
						rs.getInt(1), // courseId
						rs.getInt(2), // instrumentId
						rs.getInt(3)  // numberOfStudents
				});
			}
		}

		return result;
	}

	public Map<Integer, Integer> getMostUsedClassroomPerCourse() throws SQLException {
		String query = """
        SELECT KURS_ID_KU, UCIONICA_ID_UCI FROM (
            SELECT 
                KURS_ID_KU, 
                UCIONICA_ID_UCI, 
                COUNT(*) AS broj, 
                RANK() OVER (PARTITION BY KURS_ID_KU ORDER BY COUNT(*) DESC) AS r
            FROM TERMIN
            GROUP BY KURS_ID_KU, UCIONICA_ID_UCI
        )
        WHERE r = 1
    """;

		Map<Integer, Integer> map = new HashMap<>();

		try (Connection conn = ConnectionUtil_HikariCP.getConnection();
			 PreparedStatement ps = conn.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				map.put(rs.getInt(1), rs.getInt(2));
			}
		}

		return map;
	}

	public Map<Integer, String> getIdNameMap(String tableName, String idColumn, String nameColumn) throws SQLException {
		String query = String.format("SELECT %s, %s FROM %s", idColumn, nameColumn, tableName);

		Map<Integer, String> result = new HashMap<>();

		try (Connection conn = ConnectionUtil_HikariCP.getConnection();
			 PreparedStatement ps = conn.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				result.put(rs.getInt(1), rs.getString(2));
			}
		}

		return result;
	}

}
