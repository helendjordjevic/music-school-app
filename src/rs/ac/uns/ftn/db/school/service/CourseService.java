package rs.ac.uns.ftn.db.school.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import rs.ac.uns.ftn.db.school.dao.CourseDAO;
import rs.ac.uns.ftn.db.school.dao.impl.CourseDAOImpl;
import rs.ac.uns.ftn.db.school.dto.CourseApplicationCountDTO;
import rs.ac.uns.ftn.db.school.dto.CourseFullReportDTO;
import rs.ac.uns.ftn.db.school.dto.CourseInstrumentDTO;
import rs.ac.uns.ftn.db.school.dto.CourseWithProfessorDTO;
import rs.ac.uns.ftn.db.school.model.Course;

public class CourseService {
	
	private static final CourseDAO courseDAO = new CourseDAOImpl();

	public ArrayList<Course> getAll() throws SQLException {
		return (ArrayList<Course>) courseDAO.findAll();
	}
	
	public Course getById(int id) throws SQLException {
		return courseDAO.findById(id);
	}

	public List<CourseWithProfessorDTO> getCoursesWithProfessors() throws SQLException {
		return courseDAO.getCoursesWithProfessors();
	}

	public List<CourseApplicationCountDTO> getCourseApplicationCounts() throws SQLException {
		return courseDAO.getCourseApplicationCounts();
	}

	public List<CourseInstrumentDTO> getMostUsedInstrumentPerCourse() throws SQLException {
		return courseDAO.findMostUsedInstrumentPerCourse();
	}

	public List<CourseFullReportDTO> getFullCourseReport() throws SQLException {
		var coursesWithProfessors = courseDAO.getCoursesWithProfessors();
		var applicationsPerCourse = courseDAO.getCourseApplicationCounts();
		var mostUsedInstruments = courseDAO.findMostUsedInstrumentPerCourse();

		List<CourseFullReportDTO> result = new ArrayList<>();

		for (var prof : coursesWithProfessors) {
			CourseFullReportDTO dto = new CourseFullReportDTO();
			dto.setCourseId(prof.getCourseId());
			dto.setCourseName(prof.getCourseName());
			dto.setProfessorFirstName(prof.getProfessorFirstName());
			dto.setProfessorLastName(prof.getProfessorLastName());

			// nađi broj prijava
			applicationsPerCourse.stream()
					.filter(p -> p.getCourseId() == prof.getCourseId())
					.findFirst()
					.ifPresent(p -> dto.setApplicationCount(p.getApplicationCount()));

			// nađi najčešći instrument
			mostUsedInstruments.stream()
					.filter(i -> i.getCourseId() == prof.getCourseId())
					.findFirst()
					.ifPresent(i -> dto.setMostUsedInstrument(i.getInstrumentName()));

			result.add(dto);
		}

		return result;
	}
}
