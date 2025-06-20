package rs.ac.uns.ftn.db.school.model;


public class Instrument {
	
	private int id;
	private String name;
	
	public Instrument(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Instrument() {
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
	
	public static String getFormattedHeader() {
		return String.format("%-8s %-35s", "ID Instrument", "NAZIV");
	}

	@Override
	public String toString() {
		return "Instrument [id=" + id + ", name=" + name + "]";
	}
	
	
	

}
