package rs.ac.uns.ftn.db.school.dto;

public class CourseWithProfessorDTO {

    private int courseId;
    private String courseName;
    private String professorFirstName;
    private String professorLastName;
    private int professorId;

    public CourseWithProfessorDTO(int courseId, String courseName, String professorFirstName, String professorLastName, int professorId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.professorFirstName = professorFirstName;
        this.professorLastName = professorLastName;
        this.professorId = professorId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getProfessorFirstName() {
        return professorFirstName;
    }

    public void setProfessorFirstName(String professorFirstName) {
        this.professorFirstName = professorFirstName;
    }

    public String getProfessorLastName() {
        return professorLastName;
    }

    public void setProfessorLastName(String professorLastName) {
        this.professorLastName = professorLastName;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    @Override
    public String toString() {
        return String.format("%-8d %-30s %-15s %-15s (ID: %d)", courseId, courseName, professorFirstName, professorLastName, professorId);
    }
}
