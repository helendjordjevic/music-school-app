package rs.ac.uns.ftn.db.school.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.school.dao.ApplicationDAO;
import rs.ac.uns.ftn.db.school.dao.impl.ApplicationDAOImpl;
import rs.ac.uns.ftn.db.school.dto.CourseApplicationDTO;
import rs.ac.uns.ftn.db.school.dto.StudentApplicationCountDTO;
import rs.ac.uns.ftn.db.school.model.Application;

public class ApplicationService {
	
	private static final ApplicationDAO applicationDAO = new ApplicationDAOImpl();

	public ArrayList<Application> getAll() throws SQLException {
		return (ArrayList<Application>) applicationDAO.findAll();
	}
	
	public Application getById(int id) throws SQLException {
		return applicationDAO.findById(id);
	}

	public List<CourseApplicationDTO> getAllCourseApplications() throws SQLException {
		return ((ApplicationDAOImpl) applicationDAO).findAllWithCourseNames();
	}

	public List<StudentApplicationCountDTO> getApplicationCountPerStudent() throws SQLException {
		return ((ApplicationDAOImpl) applicationDAO).countApplicationsPerStudent();
	}



}
