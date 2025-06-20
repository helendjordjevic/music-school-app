package rs.ac.uns.ftn.db.school.model;

import java.util.Date;

public class Session {

	private int id;
	private Date startDateTime ;
	private Date endDateTime ;
	private int courseId;
	private int classRoomId;
	
	public Session(int id, Date startDateTime, Date endDateTime, int courseId, int classRoomId) {
		super();
		this.id = id;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.courseId = courseId;
		this.classRoomId = classRoomId;
	}
	
	public Session() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Date getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getClassRoomId() {
		return classRoomId;
	}

	public void setClassRoomId(int classRoomId) {
		this.classRoomId = classRoomId;
	}
	
	public static String getFormattedHeader() {
		return String.format("%-8s %-35s %-12s %-6s  %-20s", "ID Termina", "Pocetak", "Kraj", "Kurs sa ID-jem:", "ID Ucionice");
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", startDateTime=" + startDateTime + ", endDateTime=" + endDateTime + ", courseId="
				+ courseId + ", classRoomId=" + classRoomId + "]";
	}
	
	
	
}
