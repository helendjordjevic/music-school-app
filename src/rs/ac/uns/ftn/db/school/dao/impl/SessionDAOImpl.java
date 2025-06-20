package rs.ac.uns.ftn.db.school.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rs.ac.uns.ftn.db.school.connection.ConnectionUtil_HikariCP;
import rs.ac.uns.ftn.db.school.dao.SessionDAO;
import rs.ac.uns.ftn.db.school.model.Session;

public class SessionDAOImpl implements SessionDAO{
	
	@Override
	public int count() throws SQLException {
		String query = "select count(*) from TERMIN";

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
	public boolean delete(Session session) throws SQLException {
		return false;
	}

	@Override
	public int deleteAll() throws SQLException {
		return 0;
	}

	@Override
	public boolean deleteById(Integer id) throws SQLException {
		String query = "delete from SESSION where ID_TER=?";
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
		String query = "select * from TERMIN where ID_TER=?";
		try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			preparedStatement.setInt(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.isBeforeFirst();
			}
		}
	}

	@Override
	public Iterable<Session> findAll() throws SQLException {
		String query = "SELECT Id_Ter, TO_CHAR(Pocetak_Ter, 'YYYY-MM-DD HH24:MI') AS Pocetak_Ter, \r\n"
				+ "    TO_CHAR(Kraj_Ter, 'YYYY-MM-DD HH24:MI') AS Kraj_Ter, Kurs_Id_Ku, Ucionica_Id_Uci FROM Termin; ";
				
		List<Session> sessions = new ArrayList<Session>();

		try (Connection connection = ConnectionUtil_HikariCP.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				
				Timestamp beginningtimestamp = resultSet.getTimestamp(2);
				Date beginningWithTime = null;
				if (beginningtimestamp != null)
					beginningWithTime = new Date(beginningtimestamp.getTime());
				
				Timestamp endtimestamp = resultSet.getTimestamp(2);
				Date endWithTime = null;
				if (endtimestamp != null)
					endWithTime = new Date(endtimestamp.getTime());
				
				Session session = new Session(resultSet.getInt(1), beginningWithTime, endWithTime, resultSet.getInt(4), resultSet.getInt(5));
				sessions.add(session);
			}

		}
		return sessions;
	}
			
	@Override
	public Iterable<Session> findAllById(Iterable<Integer> ids) throws SQLException {
		return null;
	}

	@Override
	public Session findById(Integer id) throws SQLException {
		return null;
	}

	@Override
	public boolean save(Session entity) throws SQLException {
		try (Connection connection = ConnectionUtil_HikariCP.getConnection()) {
			return saveTransactional(connection, entity);
		}
	}

	@Override
	public int saveAll(Iterable<Session> entities) throws SQLException {
		return 0;
	}

	private boolean saveTransactional(Connection connection, Session session) throws SQLException {

		String insertCommand = "insert into TERMIN ( POCETAK_TER, KRAJ_TER, KURS_ID_KU, UCIONICA_ID_UCI) values (TO_DATE(?, 'YYYY-MM-DD HH24:mi'),TO_DATE(?, 'YYYY-MM-DD HH24:mi'),?,?)";
		String updateCommand = "update TERMIN set  POCETAK_TER=TO_DATE(?, 'YYYY-MM-DD HH24:mi'), KRAJ_TER=TO_DATE(?, 'YYYY-MM-DD HH24:mi'), KURS_ID_KU=?, UCIONICA_ID_UCI=?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				existsByIdTransactional(connection, session.getId()) ? updateCommand : insertCommand)) {
			int i = 1;
			
			String timePattern = "yyyy-MM-dd HH:mm";
			DateFormat df = new SimpleDateFormat(timePattern);
			
			
			String startDateForBase = df.format(session.getStartDateTime());
			preparedStatement.setString(i++, startDateForBase);
			
			String endDateForBase = df.format(session.getEndDateTime());
			preparedStatement.setString(i++, endDateForBase);
	       
	        
			preparedStatement.setInt(i++, session.getCourseId());
			preparedStatement.setInt(i++, session.getClassRoomId());
			int rowsAffected =  preparedStatement.executeUpdate();
			return rowsAffected == 1;
		}
	}


}
