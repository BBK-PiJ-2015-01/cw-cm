import java.util.*;

/**
 * 
 *
 * @author sbaird02
 */
public class ContactImpl implements Contact {

	private int id;
	private String name;
	private String notes;

	/**
	* Create supplying name and notes.
	*
	* @param id the unique Contact identifier. 
	* @param name the name of Contact.
	* @param notes the notes made about the Contact.
	*
	*/
	public ContactImpl(int id, String name, String notes) {

		if (name == null || notes == null) {
			throw new NullPointerException();
		}
		if(id < 1) {
			throw new IllegalArgumentException();
		}
		construct(id, name, notes);
	}
	/**
	* Create supplying notes only.
	*
	* @param id the unique Contact identifier. 
	* @param name the name of Contact.
	*
	*/
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
	* Test for equality on id. Any Object implementing
	* the Contact interface will  be considered equal if
	* it has the same id.
	*
	* @param other the Object to test for equality.	
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

	/**
	* Hashing based on id. 
	*
	*
	*/
	@Override
	public int hashCode() {

		int hash = 78125;
		hash = 103 * hash + id;
		return hash;
	}
}
