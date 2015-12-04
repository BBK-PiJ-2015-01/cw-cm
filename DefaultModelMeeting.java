import java.util.Calendar;
import java.util.Set;

/**
 *
 * @author Simon Baird
 */
public class DefaultModelMeeting implements ModelMeeting{

	private final int id;
	private Calendar date;
	private String notes;
	private Set<Contact> contacts;

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

		return contacts;
	}

	@Override
	public void setContacts(Set<Contact> contacts) {

		
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public String getNotes() {

		return notes;
	}

	@Override
	public void setNotes(String notes) {

		this.notes = notes;
	}
}

