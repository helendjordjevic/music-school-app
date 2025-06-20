package rs.ac.uns.ftn.db.school.model;

public class Application {
	
	private int courseId;
	private int instrumentId;
	private int studentId;
	
	public Application( int courseId, int instrumentId, int studentId) {
		super();
		this.courseId = courseId;
		this.instrumentId = instrumentId;
		this.studentId = studentId;
	}
	
	public Application() {
		super();
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(int instrumentId) {
		this.instrumentId = instrumentId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public static String getFormattedHeader() {
		return String.format("%-8s %-35s %-12s", "ID Studenta", "ID Kursa", "ID Instrumenta");
	}

	@Override
	public String toString() {
		return "Application [courseId=" + courseId + ", instrumentId=" + instrumentId + ", studentId=" + studentId
				+ "]";
	}
	

	
}
