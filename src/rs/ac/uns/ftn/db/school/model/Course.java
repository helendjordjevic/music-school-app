package rs.ac.uns.ftn.db.school.model;

public class Course {
	
	private int id;
	private String name;
	private int duration;
	private int professorId;
	
	public Course(int id, String name, int duration, int professorId) {
		super();
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.professorId = professorId;
	}

	public Course() {
		super();
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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}
	
	public static String getFormattedHeader() {
		return String.format("%-8s %-35s %-12s %-6s", "ID Kursa", "NAZIV", "Trajanje kursa", "Predaje profesor sa ID-jem:");
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", duration=" + duration + ", professorId=" + professorId + "]";
	}
	
	
	
}
