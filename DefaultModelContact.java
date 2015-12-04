import java.util.*;

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
	public void addNotes(String notes) {

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

		int hash = 78125;
		hash = 103 * hash + id;
		hash = 103 * hash + Objects.hashCode(name);
		hash = 103 * hash + Objects.hashCode(notes);
		return hash;
	}
}

