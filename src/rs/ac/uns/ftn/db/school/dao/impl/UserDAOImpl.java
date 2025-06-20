package rs.ac.uns.ftn.db.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.school.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.school.dao.UserDAO;
import rs.ac.uns.ftn.db.school.model.User;
import rs.ac.uns.ftn.db.school.model.enums.UserType;

public class UserDAOImpl implements UserDAO{
	
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
	            } else  {
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

	private boolean saveTransactional(Connection connection, User user) throws SQLException {

		String insertCommand = "insert into KORISNIK (IME_KOR, PRZ_KOR, EMAIL_KOR, TIP_KOR, MUZICKA_SKOLA_ID_MUZSK, ID_ST, DATUP_ST, KORISNIK_ID_ST, ID_PROF, STRUCNOST_PROF) values (?,?,?,?,?,?,?,?,?,?)";
		String updateCommand = "update KORISNIK set IME_KOR=?, PRZ_KOR=?, EMAIL_KOR=?, TIP_KOR=?, MUZICKA_SKOLA_ID_MUZSK=?, ID_ST=?, DATUP_ST=?, KORISNIK_ID_ST=?, ID_PROF=?, STRUCNOST_PROF=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				existsByIdTransactional(connection, user.getId()) ? updateCommand : insertCommand)) {
			int i = 1;
			preparedStatement.setString(i++, user.getName());
			preparedStatement.setString(i++, user.getLastname());
			preparedStatement.setString(i++, user.getEmail());
			preparedStatement.setString(i++, user.getUserType().toString());
			preparedStatement.setInt(i++, user.getSchoolId());
			preparedStatement.setInt(i++, user.getStudentId());
			preparedStatement.setDate(i++, new java.sql.Date(user.getEnrollmentDate().getTime()));
			preparedStatement.setInt(i++, user.getMentorId());
			preparedStatement.setInt(i++, user.getProfessorId());
			preparedStatement.setString(i++, user.getExpertise());

			int rowsAffected =  preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}
	}
	
	
}
