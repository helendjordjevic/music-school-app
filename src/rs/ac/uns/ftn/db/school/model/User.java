package rs.ac.uns.ftn.db.school.model;

import java.util.Date;

import rs.ac.uns.ftn.db.school.model.enums.UserType;

public class User {
	
	private int id;
	private String name;
	private String lastname;
	private String email;
	private UserType userType;
	private int schoolId;
	private int studentId;
	private Date enrollmentDate;
	private int mentorId;
	private int professorId;
	private String expertise;
	
	public User() {
		super();
	}

	public User(int id, String name, String lastname, String email, UserType userType, int schoolId, int studentId,
			Date enrollmentDate, int mentorId, int professorId, String expertise) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.userType = userType;
		this.schoolId = schoolId;
		this.studentId = studentId;
		this.enrollmentDate = enrollmentDate;
		this.mentorId = mentorId;
		this.professorId = professorId;
		this.expertise = expertise;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public int getMentorId() {
		return mentorId;
	}

	public void setMentorId(int mentorId) {
		this.mentorId = mentorId;
	}

	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}
	
	public static String getFormattedHeader() {
		return String.format("%-8s %-35s %-12s %-6s %-8s %-35s %-12s %-6s %-8s %-8s %-35s", "ID Korisnika", "Ime", "Prezime", "Email", "Tip korisnika","ID Skole", "ID Studenta", "Datum upisa", "Mentor", "ID Profesora", "Strucnost profesora");
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", lastname=" + lastname + ", email=" + email + ", userType="
				+ userType + ", schoolId=" + schoolId + ", studentId=" + studentId + ", enrollmentDate="
				+ enrollmentDate + ", mentorId=" + mentorId + ", professorId=" + professorId + ", expertise="
				+ expertise + "]";
	}

	
	
	
}
