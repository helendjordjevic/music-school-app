package rs.ac.uns.ftn.db.school.service;

import java.sql.SQLException;
import java.util.ArrayList;

import rs.ac.uns.ftn.db.school.dao.ClassroomDAO;
import rs.ac.uns.ftn.db.school.dao.impl.ClassroomDAOImpl;
import rs.ac.uns.ftn.db.school.model.Classroom;

public class ClassroomService {
	
	private static final ClassroomDAO classroomDAO = new ClassroomDAOImpl();

	public ArrayList<Classroom> getAll() throws SQLException {
		return (ArrayList<Classroom>) classroomDAO.findAll();
	}
	
	public Classroom getById(int id) throws SQLException {
		return classroomDAO.findById(id);
	}

}
