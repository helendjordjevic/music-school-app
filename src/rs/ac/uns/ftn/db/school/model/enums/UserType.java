package rs.ac.uns.ftn.db.school.model.enums;

public enum UserType {

	PROFESSOR("Profesor"),
	STUDENT("Student");

	private final String dbValue;

	UserType(String dbValue) {
		this.dbValue = dbValue;
	}

	public String getDbValue() {
		return dbValue;
	}

	@Override
	public String toString() {
		return dbValue;
	}

}
