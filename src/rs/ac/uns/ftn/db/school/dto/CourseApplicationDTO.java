package rs.ac.uns.ftn.db.school.dto;

public class CourseApplicationDTO {
    private int courseId;
    private String courseName;
    private int studentId;

    public CourseApplicationDTO() {
        super();
    }

    public CourseApplicationDTO(int courseId, String courseName, int studentId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.studentId = studentId;
    }

    public int getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getStudentId() { return studentId; }

    @Override
    public String toString() {
        return String.format("Kurs ID: %d, Naziv: %s, Student ID: %d", courseId, courseName, studentId);
    }
}
