package rs.ac.uns.ftn.db.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.school.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.school.dao.CourseDAO;
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

	private boolean saveTransactional(Connection connection, Course course) throws SQLException {

		String insertCommand = "insert into KURS (NAZ_KU, TRAJANJE_KU, KORISNIK_ID_PROF) values (?,?,?)";
		String updateCommand = "update KURS set NAZ_KU=?, TRAJANJE_KU=?, KORISNIK_ID_PROF=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				existsByIdTransactional(connection, course.getId()) ? updateCommand : insertCommand)) {
			int i = 1;
			preparedStatement.setString(i++, course.getName());
			preparedStatement.setInt(i++, course.getDuration());
			preparedStatement.setInt(i++, course.getProfessorId());
			int rowsAffected =  preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}
	}


}
