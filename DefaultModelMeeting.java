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
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public void getContacts(Set<Contact> contacts) {
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public String getNotes() {

		return notes;
	}

	@Override
	public void setNotes(String notes) {
	throw new UnsupportedOperationException("Unsupported operation.");
	}
    
}

