import java.util.Calendar;
import java.util.Set;

/**
 *
 * @author Simon Baird
 */
public class DefaultModelMeeting implements ModelMeeting{

	private final int id;

	public DefaultModelMeeting(int id) {

		this.id = id;
	}

	@Override
	public int getId() {
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public Calendar getDate() {
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public void setDate(Calendar date) {
	throw new UnsupportedOperationException("Unsupported operation.");
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
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public void getNotes(String notes) {
	throw new UnsupportedOperationException("Unsupported operation.");
	}
    
}

