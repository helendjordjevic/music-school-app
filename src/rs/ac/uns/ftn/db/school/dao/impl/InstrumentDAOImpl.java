package rs.ac.uns.ftn.db.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.school.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.school.dao.InstrumentDAO;
import rs.ac.uns.ftn.db.school.model.Instrument;

public class InstrumentDAOImpl implements InstrumentDAO {
	
	@Override
	public int count() throws SQLException {
		String query = "select count(*) from INSTRUMENT";

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
	public boolean delete(Instrument instrument) throws SQLException {
		return false;
	}

	@Override
	public int deleteAll() throws SQLException {
		return 0;
	}

	@Override
	public boolean deleteById(Integer id) throws SQLException {
		String query = "delete from INSTRUMENT where ID_INS=?";
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
		String query = "select * from INSTRUMENT where ID_INS=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.isBeforeFirst();
			}
		}
	}

	@Override
	public Iterable<Instrument> findAll() throws SQLException {
		String query = "select ID_INS, NAZ_INS from INSTRUMENT";
		List<Instrument> instruments = new ArrayList<Instrument>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				
				Instrument instrument = new Instrument(resultSet.getInt(1), resultSet.getString(2));
				
				instruments.add(instrument);
			}

		}
		return instruments;
	}

	@Override
	public Iterable<Instrument> findAllById(Iterable<Integer> ids) throws SQLException {
		return null;
	}

	@Override
	public Instrument findById(Integer id) throws SQLException {
		return null;
	}

	@Override
	public boolean save(Instrument entity) throws SQLException {
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			return saveTransactional(connection, entity);
		}
	}

	@Override
	public int saveAll(Iterable<Instrument> entities) throws SQLException {
		return 0;
	}

	private boolean saveTransactional(Connection connection, Instrument instrument) throws SQLException {

		String insertCommand = "insert into INSTRUMENT (NAZ_INS) values (?)";
		String updateCommand = "update INSTRUMENT set NAZ_INS=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				existsByIdTransactional(connection, instrument.getId()) ? updateCommand : insertCommand)) {
			int i = 1;
			preparedStatement.setString(i++, instrument.getName());
			int rowsAffected =  preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}
	}

}
