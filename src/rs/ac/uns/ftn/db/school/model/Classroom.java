package rs.ac.uns.ftn.db.school.model;

public class Classroom {
	
	private int id;
	private String ClassroomName;
	
	
	public Classroom(int id, String classroomName) {
		super();
		this.id = id;
		ClassroomName = classroomName;
	}
	
	public Classroom() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassroomName() {
		return ClassroomName;
	}

	public void setClassroomName(String classroomName) {
		ClassroomName = classroomName;
	}

	public static String getFormattedHeader() {
		return String.format("%-8s %-35s", "ID Ucionice", "NAZIV");
	}

	@Override
	public String toString() {
		return "Classroom [id=" + id + ", ClassroomName=" + ClassroomName + "]";
	}
	
}
