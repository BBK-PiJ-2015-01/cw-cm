public class DefaultModelContact implements ModelContact {

	private final int id;
	private String name;
	private String notes;

	public DefaultModelContact (int id) {

		this.id = id;
	}

	@Override
	public int getId() {

		return id;
	}

	@Override
	public String getName() {

		return name;
	}

	@Override
	public void setName(String name) {

		this.name = name;
	}

	@Override
	public String getNotes() {

		return notes;
	}

	@Override
	public void addNotes(String notes) {

		this.notes = notes;
	}
    
}

