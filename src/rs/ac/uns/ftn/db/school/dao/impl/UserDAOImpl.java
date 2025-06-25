package rs.ac.uns.ftn.db.school.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.school.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.school.dao.UserDAO;
import rs.ac.uns.ftn.db.school.model.User;
import rs.ac.uns.ftn.db.school.model.enums.UserType;

public class UserDAOImpl implements UserDAO {

	@Override
	public int count() throws SQLException {
		String query = "select count(*) from KORISNIK";

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
	public boolean delete(User user) throws SQLException {
		return false;
	}

	@Override
	public int deleteAll() throws SQLException {
		return 0;
	}

	@Override
	public boolean deleteById(Integer id) throws SQLException {
		String query = "delete from KORISNIK where ID_KOR=?";
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
		String query = "select * from KORISNIK where ID_KOR=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.isBeforeFirst();
			}
		}
	}

	@Override
	public Iterable<User> findAll() throws SQLException {
		String query = "select ID_KOR, IME_KOR, PRZ_KOR, EMAIL_KOR, TIP_KOR, MUZICKA_SKOLA_ID_MUZSK, ID_ST, DATUP_ST, KORISNIK_ID_ST, ID_PROF, STRUCNOST_PROF  from KORISNIK";
		List<User> users = new ArrayList<User>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(query);
			 ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {

				UserType userType = null;

				String userTypeString = resultSet.getString(5);


				if (userTypeString.equals("Student")) {
					userType = UserType.STUDENT;
				} else {
					userType = UserType.PROFESSOR;
				}

				User user = new User(resultSet.getInt(1),
						resultSet.getString(2),
						resultSet.getString(3),
						resultSet.getString(4),
						userType,
						resultSet.getInt(6),
						resultSet.getInt(7),
						resultSet.getDate(8),
						resultSet.getInt(9),
						resultSet.getInt(10),
						resultSet.getString(11));

				users.add(user);
			}

		}
		return users;
	}

	@Override
	public Iterable<User> findAllById(Iterable<Integer> ids) throws SQLException {
		return null;
	}

	@Override
	public User findById(Integer id) throws SQLException {
		return null;
	}

	@Override
	public boolean save(User entity) throws SQLException {
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			return saveTransactional(connection, entity);
		}
	}

	@Override
	public int saveAll(Iterable<User> entities) throws SQLException {
		return 0;
	}

	public boolean saveTransactional(Connection connection, User user) throws SQLException {
		boolean exists = false;

		if (user.getId() != 0) {
			exists = existsByIdTransactional(connection, user.getId());
		}

		// Ako je novi korisnik (profesor ili student), id iz sekvence ide preko triggera za Id_Kor
		// Ali profesor dobija dodatno Id_Prof iz sekvence - zato mi sami dobijamo ID za profesora
		if (!exists && user.getUserType() == UserType.PROFESSOR) {
			int nextProfId = getNextProfessorId(connection);
			user.setProfessorId(nextProfId);
		}

		String insertCommand = "INSERT INTO KORISNIK (IME_KOR, PRZ_KOR, EMAIL_KOR, TIP_KOR, MUZICKA_SKOLA_ID_MUZSK, ID_ST, DATUP_ST, KORISNIK_ID_ST, ID_PROF, STRUCNOST_PROF) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		String updateCommand = "UPDATE KORISNIK SET IME_KOR=?, PRZ_KOR=?, EMAIL_KOR=?, TIP_KOR=?, MUZICKA_SKOLA_ID_MUZSK=?, ID_ST=?, DATUP_ST=?, KORISNIK_ID_ST=?, ID_PROF=?, STRUCNOST_PROF=? WHERE ID_KOR=?";

		try (PreparedStatement ps = connection.prepareStatement(exists ? updateCommand : insertCommand)) {

			int i = 1;
			ps.setString(i++, user.getName());
			ps.setString(i++, user.getLastname());
			ps.setString(i++, user.getEmail());
			ps.setString(i++, user.getUserType().getDbValue());
			ps.setInt(i++, user.getSchoolId());

			// STUDENT polja
			if (user.getUserType() == UserType.STUDENT) {
				ps.setInt(i++, user.getStudentId());

				if (user.getEnrollmentDate() != null) {
					ps.setDate(i++, new java.sql.Date(user.getEnrollmentDate().getTime()));
				} else {
					ps.setNull(i++, java.sql.Types.DATE);
				}

				if (user.getMentorId() != 0) {
					ps.setInt(i++, user.getMentorId());
				} else {
					ps.setNull(i++, java.sql.Types.INTEGER);
				}

				ps.setNull(i++, java.sql.Types.INTEGER);  // ID_PROF
				ps.setNull(i++, java.sql.Types.VARCHAR);  // STRUCNOST_PROF
			}
			// PROFESOR polja
			else if (user.getUserType() == UserType.PROFESSOR) {
				ps.setNull(i++, java.sql.Types.INTEGER); // ID_ST
				ps.setNull(i++, java.sql.Types.DATE);    // DATUP_ST
				ps.setNull(i++, java.sql.Types.INTEGER); // KORISNIK_ID_ST

				ps.setInt(i++, user.getProfessorId());

				if (user.getExpertise() != null) {
					ps.setString(i++, user.getExpertise());
				} else {
					ps.setNull(i++, java.sql.Types.VARCHAR);
				}
			}
			// Ostali korisnici - null polja
			else {
				ps.setNull(i++, java.sql.Types.INTEGER);
				ps.setNull(i++, java.sql.Types.DATE);
				ps.setNull(i++, java.sql.Types.INTEGER);
				ps.setNull(i++, java.sql.Types.INTEGER);
				ps.setNull(i++, java.sql.Types.VARCHAR);
			}

			if (exists) {
				ps.setInt(i++, user.getId()); // WHERE ID_KOR = ?
			}

			int rowsAffected = ps.executeUpdate();

			if (!exists && rowsAffected == 1) {
				// Pošto Oracle ne podržava getGeneratedKeys za sekvence i trigger,
				// dohvatimo ID korisnika po emailu (pretpostavljamo da je jedinstven)
				String query = "SELECT Id_Kor FROM Korisnik WHERE Email_Kor = ?";
				try (PreparedStatement psQuery = connection.prepareStatement(query)) {
					psQuery.setString(1, user.getEmail());
					try (ResultSet rs = psQuery.executeQuery()) {
						if (rs.next()) {
							user.setId(rs.getInt("Id_Kor"));
						}
					}
				}
			}

			return rowsAffected == 1;
		}
	}

	public int getNextProfessorId(Connection connection) throws SQLException {
		String sql = "SELECT profesor_seq.NEXTVAL FROM dual";
		try (PreparedStatement ps = connection.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				throw new SQLException("Ne mogu da dobijem sledeći Id_Prof iz sekvence");
			}
		}
	}
}
