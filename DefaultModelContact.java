public class DefaultModelContact implements ModelContact {

	private final int id;
	private String name;

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
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public void addNotes(String note) {
	throw new UnsupportedOperationException("Unsupported operation.");
	}
    
}

