package rs.ac.uns.ftn.db.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.school.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.school.dao.CourseDAO;
import rs.ac.uns.ftn.db.school.dto.CourseApplicationCountDTO;
import rs.ac.uns.ftn.db.school.dto.CourseInstrumentDTO;
import rs.ac.uns.ftn.db.school.dto.CourseWithProfessorDTO;
import rs.ac.uns.ftn.db.school.model.Course;

public class CourseDAOImpl implements CourseDAO {
	
	@Override
	public int count() throws SQLException {
		String query = "select count(*) from KURS";

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
	public boolean delete(Course course) throws SQLException {
		return false;
	}

	@Override
	public int deleteAll() throws SQLException {
		return 0;
	}

	@Override
	public boolean deleteById(Integer id) throws SQLException {
		String query = "delete from KURS where ID_KU=?";
		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}

	}

	@Override
	public boolean existsById(Integer id) throws SQLException {
		return false;
	}

	
	public boolean existsByIdTransactional(Connection connection, Integer id) throws SQLException {
		String query = "select * from KURS where ID_KU=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.isBeforeFirst();
			}
		}
	}

	@Override
	public Iterable<Course> findAll() throws SQLException {
		String query = "select ID_KU, NAZ_KU, TRAJANJE_KU, KORISNIK_ID_PROF from KURS";
		List<Course> courses = new ArrayList<Course>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				
				Course course = new Course(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4));
				
				courses.add(course);
			}

		}
		return courses;
	}

	@Override
	public Iterable<Course> findAllById(Iterable<Integer> ids) throws SQLException {
		return null;
	}

	@Override
	public Course findById(Integer id) throws SQLException {
		return null;
	}

	@Override
	public boolean save(Course entity) throws SQLException {
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			return saveTransactional(connection, entity);
		}
	}

	@Override
	public int saveAll(Iterable<Course> entities) throws SQLException {
		return 0;
	}

	public boolean saveTransactional(Connection connection, Course course) throws SQLException {

		boolean exists = existsByIdTransactional(connection, course.getId());

		// Ako novi kurs, postavi ID iz sekvence
		if (!exists) {
			int nextCourseId = getNextCourseId(connection);
			course.setId(nextCourseId);
		}

		String insertCommand = "insert into KURS (ID_KU, NAZ_KU, TRAJANJE_KU, KORISNIK_ID_PROF) values (?, ?, ?, ?)";
		String updateCommand = "update KURS set NAZ_KU=?, TRAJANJE_KU=?, KORISNIK_ID_PROF=? WHERE ID_KU=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(exists ? updateCommand : insertCommand)) {
			int i = 1;
			if (!exists) {
				preparedStatement.setInt(i++, course.getId());  // ID_KU
			}
			preparedStatement.setString(i++, course.getName());  // NAZ_KU
			preparedStatement.setInt(i++, course.getDuration()); // TRAJANJE_KU
			preparedStatement.setInt(i++, course.getProfessorId()); // KORISNIK_ID_PROF

			if (exists) {
				preparedStatement.setInt(i++, course.getId()); // WHERE ID_KU=?
			}

			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}

	}

	public int getNextCourseId(Connection connection) throws SQLException {
		String sql = "SELECT kurs_seq.NEXTVAL FROM dual";
		try (PreparedStatement ps = connection.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new SQLException("Nisam mogao da dobijem sledeći ID iz sekvence kurs_seq.");
			}
		}
	}

	@Override
	public List<CourseWithProfessorDTO> getCoursesWithProfessors() throws SQLException {
		List<CourseWithProfessorDTO> result = new ArrayList<>();

		String query = """
            SELECT 
                k.ID_KU AS kurs_id, 
                k.NAZ_KU AS kurs_naziv, 
                kor.IME_KOR, 
                kor.PRZ_KOR, 
                kor.ID_PROF
            FROM KURS k
            JOIN KORISNIK kor ON k.KORISNIK_ID_PROF = kor.ID_PROF
            WHERE kor.TIP_KOR = 'Profesor'
        """;

		try (Connection conn = ConnectionUtil_HikariCP.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				result.add(new CourseWithProfessorDTO(
						rs.getInt("kurs_id"),
						rs.getString("kurs_naziv"),
						rs.getString("Ime_Kor"),
						rs.getString("Prz_Kor"),
						rs.getInt("Id_Prof")
				));
			}
		}
		return result;
	}

	@Override
	public List<CourseApplicationCountDTO> getCourseApplicationCounts() throws SQLException {
		List<CourseApplicationCountDTO> result = new ArrayList<>();

		String query = """
            SELECT 
                k.ID_KU AS kurs_id,
                k.NAZ_KU AS kurs_naziv,
                COUNT(p.KORISNIK_ID_ST) AS broj_prijava
            FROM KURS k
            LEFT JOIN PRIJAVA p ON k.ID_KU = p.KURS_ID_KU
            GROUP BY k.ID_KU, k.NAZ_KU
            ORDER BY broj_prijava DESC
        """;

		try (Connection conn = ConnectionUtil_HikariCP.getConnection();
			 PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				result.add(new CourseApplicationCountDTO(
						rs.getInt("kurs_id"),
						rs.getString("kurs_naziv"),
						rs.getInt("broj_prijava")
				));
			}
		}

		return result;
	}
	@Override
	public List<CourseInstrumentDTO> findMostUsedInstrumentPerCourse() throws SQLException {
		List<CourseInstrumentDTO> result = new ArrayList<>();


		String query = """
	WITH InstrumentCount AS (
		SELECT 
			p.KURS_ID_KU AS kurs_id,
			p.INSTRUMENT_ID_INS AS instrument_id,
			COUNT(*) AS broj_prijava
		FROM PRIJAVA p
		GROUP BY p.KURS_ID_KU, p.INSTRUMENT_ID_INS
	),
	MaxInstrument AS (
		SELECT
			kurs_id,
			MAX(broj_prijava) AS max_prijava
		FROM InstrumentCount
		GROUP BY kurs_id
	)
	SELECT
		ic.kurs_id,
		k.NAZ_KU AS naziv_kursa,
		ic.instrument_id,
		i.NAZ_INS AS naziv_instrumenta,
		ic.broj_prijava
	FROM InstrumentCount ic
	JOIN MaxInstrument mi ON ic.kurs_id = mi.kurs_id AND ic.broj_prijava = mi.max_prijava
	JOIN Instrument i ON ic.instrument_id = i.ID_INS
	JOIN Kurs k ON ic.kurs_id = k.ID_KU
	ORDER BY ic.kurs_id
	""";


		try (Connection conn = ConnectionUtil_HikariCP.getConnection();
			 PreparedStatement ps = conn.prepareStatement(query);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				CourseInstrumentDTO dto = new CourseInstrumentDTO(
						rs.getInt("kurs_id"),
						rs.getString("naziv_kursa"),
						rs.getInt("instrument_id"),
						rs.getString("naziv_instrumenta"),
						rs.getInt("broj_prijava")
				);
				result.add(dto);
			}
		}

		return result;
	}

}
