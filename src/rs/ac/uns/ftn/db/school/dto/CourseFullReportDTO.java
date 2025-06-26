package rs.ac.uns.ftn.db.school.dto;

public class CourseFullReportDTO {
    private int courseId;
    private String courseName;
    private String professorFirstName;
    private String professorLastName;
    private int applicationCount;
    private String mostUsedInstrument;

    public CourseFullReportDTO() {}

    public CourseFullReportDTO(int courseId, String courseName, String professorFirstName, String professorLastName, int applicationCount, String mostUsedInstrument) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.professorFirstName = professorFirstName;
        this.professorLastName = professorLastName;
        this.applicationCount = applicationCount;
        this.mostUsedInstrument = mostUsedInstrument;
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

    public int getApplicationCount() {
        return applicationCount;
    }

    public void setApplicationCount(int applicationCount) {
        this.applicationCount = applicationCount;
    }

    public String getMostUsedInstrument() {
        return mostUsedInstrument;
    }

    public void setMostUsedInstrument(String mostUsedInstrument) {
        this.mostUsedInstrument = mostUsedInstrument;
    }

    @Override
    public String toString() {
        return "CourseFullReportDTO{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", professorFirstName='" + professorFirstName + '\'' +
                ", professorLastName='" + professorLastName + '\'' +
                ", applicationCount=" + applicationCount +
                ", mostUsedInstrument='" + mostUsedInstrument + '\'' +
                '}';
    }
}
