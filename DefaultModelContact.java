import java.util.*;
import java.io.*;
/**
 * A simple implementation of the ModelContact interface. This is used in the
 * SerializableFilePersistenceUnit class as Serializable content.
 * the data to be stored.
 *
 * @author sbaird02
 */
public class DefaultModelContact implements ModelContact, Cloneable, Serializable {

	private final int id;
	private String name;
	private String notes;

	/**
	* Create supplying unique identifier.
	*
	* @param id the unique Contact identifier. 
	*
	*/
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
    	/**
	* Implementation of clone to allow object isolation within a model.
	*
	*/
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

