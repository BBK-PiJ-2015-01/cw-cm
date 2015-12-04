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
	public Set<ModelContact> getContacts() {

		return contacts;
	}

	@Override
	public void setContacts(Set<ModelContact> contacts) {
		
		this.contacts = contacts;
	}

	@Override
	public String getNotes() {

		return notes;
	}

	@Override
	public void setNotes(String notes) {

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
}

