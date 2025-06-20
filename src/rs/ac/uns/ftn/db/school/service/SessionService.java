package rs.ac.uns.ftn.db.school.service;

import java.sql.SQLException;
import java.util.ArrayList;

import rs.ac.uns.ftn.db.school.dao.SessionDAO;
import rs.ac.uns.ftn.db.school.dao.impl.SessionDAOImpl;
import rs.ac.uns.ftn.db.school.model.Session;

public class SessionService {
	
	private static final SessionDAO sessionDAO = new SessionDAOImpl();

	public ArrayList<Session> getAll() throws SQLException {
		return (ArrayList<Session>) sessionDAO.findAll();
	}
	
	public Session getById(int id) throws SQLException {
		return sessionDAO.findById(id);
	}

}
