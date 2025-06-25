package rs.ac.uns.ftn.db.school.dto;

public class StudentApplicationCountDTO {
    private int studentId;
    private int numberOfApplications;

    public StudentApplicationCountDTO() {
        super();
    }
    public StudentApplicationCountDTO(int studentId, int numberOfApplications) {
        super();
        this.studentId = studentId;
        this.numberOfApplications = numberOfApplications;
    }

    public int getStudentId() { return studentId; }
    public int getNumberOfApplications() { return numberOfApplications; }

    @Override
    public String toString() {
        return String.format("Student ID: %d, Broj prijava: %d", studentId, numberOfApplications);
    }

}
