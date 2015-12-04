public class DefaultModelContact implements ModelContact {

	private final int id;

	public DefaultModelContact (int id) {

		this.id = id;
	}

	@Override
	public int getId() {

		return id;
	}

	@Override
	public String getName() {
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public void setName(String name) {
	throw new UnsupportedOperationException("Unsupported operation.");
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

