package rs.ac.uns.ftn.db.school.dao;

import rs.ac.uns.ftn.db.school.dto.CourseApplicationCountDTO;
import rs.ac.uns.ftn.db.school.dto.CourseInstrumentDTO;
import rs.ac.uns.ftn.db.school.dto.CourseWithProfessorDTO;
import rs.ac.uns.ftn.db.school.model.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseDAO extends CRUDDao<Course, Integer> {

    public List<CourseWithProfessorDTO> getCoursesWithProfessors() throws SQLException;
    List<CourseApplicationCountDTO> getCourseApplicationCounts() throws SQLException;
    List<CourseInstrumentDTO> findMostUsedInstrumentPerCourse() throws SQLException;

}
