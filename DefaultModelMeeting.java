import java.util.*;
import java.io.*;
/**
 * A simple implementation of the ModelMeeting interface. This is used in the
 * <code>SerializableFilePersistenceUnit</code> class as Serializable content.
 *
 * @author sbaird02
 */
public class DefaultModelMeeting implements ModelMeeting, Cloneable, Serializable {

	private final int id;
	private Calendar date;
	private String notes;
	private Set<ModelContact> contacts;

	/**
	* Create with default model meeting data. 
	*
	* @param id the unique Meeting identifier. 
	*/
	public DefaultModelMeeting(int id) {

		this.id = id;
	}

	@Override
	public int getId() {
	
		return id;
	}

	@Override
	public Calendar getDate() {

		return date;
	}

	@Override
	public void setDate(Calendar date) {

		this.date = date;
	}

	@Override
	public Set<Contact> getContacts() {

		if (contacts == null)  {
			return null;
		}
		Set<Contact> returnContacts = new HashSet<Contact>();
		contacts.stream().forEach((mc) -> returnContacts.add(mc));
		return returnContacts;
	}

	@Override
	public void setContacts(Set<Contact> contacts) {
		
		if (contacts == null)  {
			this.contacts = null;
			return;
		}
		this.contacts = new HashSet<ModelContact>();
		if (contacts.isEmpty())  {
			return;
		}
		// Convert the contacts to ModelContacts
		contacts.stream().forEach((c) -> this.contacts.add(getModelContactInstance(c)));
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
	* This will call clone() on member variables where applicable.
	*/
	public DefaultModelMeeting clone() {
		
		DefaultModelMeeting clone = new DefaultModelMeeting(id);
		if (date != null) {
			clone.date = (Calendar)date.clone();
		}
		if (notes != null) {
			clone.notes = new String(notes);
		}
		if (contacts != null) {
			clone.contacts = new HashSet<ModelContact>();
			contacts.stream().forEach((c) -> clone.contacts.add(c.clone()));
		}
		return clone;		
	}

	private ModelContact getModelContactInstance(Contact contact) {
		
		ModelContact mc = new DefaultModelContact(contact.getId());
		mc.setName(contact.getName());
		mc.addNotes(contact.getNotes());
		return mc;		
	}

	/**
	* Test for equality on id. Any Object implementing
	* the Meeting interface will  be considered equal if
	* it has the same id.
	*
	* @param other the Object to test for equality.	
	*
	*/
	@Override
	public boolean equals(Object other) {

		if (other == null || !(other instanceof Meeting)) {
		    return false;
		}
		Meeting otherMeeting = (Meeting) other;
		return this.getId() == otherMeeting.getId();
	}

	/**
	* Hashing based on id.
	*
	*/
	@Override
	public int hashCode() {

		int hash = 16383;
		hash = 137 * hash + id;
		return hash;
	}
}

