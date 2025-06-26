package rs.ac.uns.ftn.db.school.dto;

public class CourseInstrumentDTO {

    private int courseId;
    private String courseName;
    private int instrumentId;
    private String instrumentName;
    private int applicationCount;

    public CourseInstrumentDTO(int courseId, String courseName, int instrumentId, String instrumentName, int applicationCount) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.instrumentId = instrumentId;
        this.instrumentName = instrumentName;
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

    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public int getApplicationCount() {
        return applicationCount;
    }

    public void setApplicationCount(int applicationCount) {
        this.applicationCount = applicationCount;
    }

    @Override
    public String toString() {
        return String.format("Kurs: %s (ID: %d), Instrument: %s (ID: %d), Broj prijava: %d",
                courseName, courseId, instrumentName, instrumentId, applicationCount);
    }
}
