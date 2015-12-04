import java.util.*;

/**
 *
 * @author Simon Baird
 */
public class DefaultModelMeeting implements ModelMeeting, Cloneable{

	private final int id;
	private Calendar date;
	private String notes;
	private Set<ModelContact> contacts;

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
	*	Allow equality on id
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

	@Override
	public int hashCode() {

		int hash = 2047;
		hash = 113 * hash + id;
//		return hash;
		return id;
	}
}

