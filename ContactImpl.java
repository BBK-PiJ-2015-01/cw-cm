import java.util.*;

/**
 *
 * 
 */
public class ContactImpl implements Contact {

	private final int id;
	private String name;
	private String notes;


	public ContactImpl(int id) {

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

	public void setName(String name) {
	
		this.name = name;
	}

	@Override
	public String getNotes() {

		return notes == null ? new String() : notes;
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

		if (other == null || this.getClass() != other.getClass()) {
		    return false;
		}
		ContactImpl otherContactImpl = (ContactImpl) other;
		return this.id == otherContactImpl.id;
	}

	@Override
	public int hashCode() {
		int hash = 16381;
		hash = 83 * hash + id;
		hash = 83 * hash + Objects.hashCode(name);
		hash = 83 * hash + Objects.hashCode(notes);
		return hash;
	}
}
