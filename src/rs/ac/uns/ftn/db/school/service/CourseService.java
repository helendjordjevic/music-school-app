package rs.ac.uns.ftn.db.school.service;

import java.sql.SQLException;
import java.util.ArrayList;

import rs.ac.uns.ftn.db.school.dao.CourseDAO;
import rs.ac.uns.ftn.db.school.dao.impl.CourseDAOImpl;
import rs.ac.uns.ftn.db.school.model.Course;

public class CourseService {
	
	private static final CourseDAO courseDAO = new CourseDAOImpl();

	public ArrayList<Course> getAll() throws SQLException {
		return (ArrayList<Course>) courseDAO.findAll();
	}
	
	public Course getById(int id) throws SQLException {
		return courseDAO.findById(id);
	}

}
