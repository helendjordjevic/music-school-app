package rs.ac.uns.ftn.db.school.service;

import java.sql.SQLException;
import java.util.ArrayList;

import rs.ac.uns.ftn.db.school.dao.UserDAO;
import rs.ac.uns.ftn.db.school.dao.impl.UserDAOImpl;
import rs.ac.uns.ftn.db.school.model.User;

public class UserService {
	

	private static final UserDAO userDAO = new UserDAOImpl();

	public ArrayList<User> getAll() throws SQLException {
		return (ArrayList<User>) userDAO.findAll();
	}

	public User getById(int id) throws SQLException {
		return userDAO.findById(id);
	}


}
