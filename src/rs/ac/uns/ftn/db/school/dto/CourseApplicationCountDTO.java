package rs.ac.uns.ftn.db.school.dto;

public class CourseApplicationCountDTO {

    private int courseId;
    private String courseName;
    private int applicationCount;

    public CourseApplicationCountDTO(int courseId, String courseName, int applicationCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.applicationCount = applicationCount;
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

    public int getApplicationCount() {
        return applicationCount;
    }

    public void setApplicationCount(int applicationCount) {
        this.applicationCount = applicationCount;
    }

    @Override
    public String toString() {
        return String.format("Kurs ID: %d, Naziv: %s, Broj prijava: %d", courseId, courseName, applicationCount);
    }
}
