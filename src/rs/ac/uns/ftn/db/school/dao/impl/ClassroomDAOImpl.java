package rs.ac.uns.ftn.db.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.school.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.school.dao.ClassroomDAO;
import rs.ac.uns.ftn.db.school.model.Classroom;

public class ClassroomDAOImpl implements ClassroomDAO{
	
	@Override
	public int count() throws SQLException {
		String query = "select count(*) from UCIONICA";

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
	public boolean delete(Classroom classroom) throws SQLException {
		return false;
	}

	@Override
	public int deleteAll() throws SQLException {
		return 0;
	}

	@Override
	public boolean deleteById(Integer id) throws SQLException {
		String query = "delete from UCIONICA where ID_UCI=?";
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
		String query = "select * from UCIONICA where ID_UCI=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.isBeforeFirst();
			}
		}
	}

	@Override
	public Iterable<Classroom> findAll() throws SQLException {
		String query = "select ID_UCI, NAZ_UCI from UCIONICA";
		List<Classroom> classrooms = new ArrayList<Classroom>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				
				Classroom classroom = new Classroom(resultSet.getInt(1), resultSet.getString(2));
				
				classrooms.add(classroom);
			}

		}
		return classrooms;
	}

	@Override
	public Iterable<Classroom> findAllById(Iterable<Integer> ids) throws SQLException {
		return null;
	}

	@Override
	public Classroom findById(Integer id) throws SQLException {
		return null;
	}

	@Override
	public boolean save(Classroom entity) throws SQLException {
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			return saveTransactional(connection, entity);
		}
	}

	@Override
	public int saveAll(Iterable<Classroom> entities) throws SQLException {
		return 0;
	}

	private boolean saveTransactional(Connection connection, Classroom classroom) throws SQLException {

		String insertCommand = "insert into UCIONICA (NAZ_UCI) values (?)";
		String updateCommand = "update UCIONICA set NAZ_UCI=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				existsByIdTransactional(connection, classroom.getId()) ? updateCommand : insertCommand)) {
			int i = 1;
			preparedStatement.setString(i++, classroom.getClassroomName());
			int rowsAffected =  preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}
	}


}
