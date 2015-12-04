public class DefaultModelContact implements ModelContact, Cloneable {

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
	public void setNotes(String notes) {

		this.notes = notes;
	}
    
	public DefaultModelContact clone() {

		DefaultModelContact clone = new DefaultModelContact(id);
		if (name != null) {
			clone.name = new String(name);
		}
		if (notes != null) {
			clone.notes = new String(notes);
		}
		return clone;
	}
}

