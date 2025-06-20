package rs.ac.uns.ftn.db.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.school.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.school.dao.ApplicationDAO;
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
		//String updateCommand = "update PRIJAVA set KURS_ID_KU=?, INSTRUMENT_ID_INS=?, KORISNIK_ID_ST=?";
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

	
}
