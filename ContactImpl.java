import java.util.*;

/**
 *
 * 
 */
public class ContactImpl implements Contact {

	private int id;
	private String name;
	private String notes;

	public ContactImpl(int id, String name, String notes) {

		if (name == null || notes == null) {
			throw new NullPointerException();
		}
		if(id < 1) {
			throw new IllegalArgumentException();
		}
		construct(id, name, notes);
	}

	public ContactImpl(int id, String name) {

		if (name == null ) {
			throw new NullPointerException();
		}
		if(id < 1) {
			throw new IllegalArgumentException();
		}
		construct(id, name, null);
	}

	private void construct(int id, String name, String notes) {

		this.id = id;
		this.name = name;
		this.notes = notes;
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
	public String getNotes() {

		return notes == null ? "" : notes;
	}

	@Override
	public void addNotes(String notes) {

		this.notes = notes;
	}

	/**
	*	Allow equality on id
	*
	*/
	@Override
	public boolean equals(Object other) {

		if (other == null || !(other instanceof Contact)) {
		    return false;
		}

		Contact otherContactImpl = (Contact) other;
		return this.getId() == otherContactImpl.getId();
	}

	@Override
	public int hashCode() {

		int hash = 4849;
		hash = 131 * hash + id;
		return hash;
	}
}
