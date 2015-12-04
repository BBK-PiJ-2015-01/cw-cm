import java.util.*;
/**
 * 
 *
 * @author sbaird02
 */
public class SerializableContactManagerModel implements ContactManagerModel {

	private final String NULL_PARAM_MSG = "The supplied argument was null";

	@Override
	public Set<? extends Contact> getContacts() {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public Set<? extends Meeting> getMeetings() {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public int addContact(Contact contact) {

		if (contact == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		return 1;
	}

	@Override
	public Contact getContact(int id) {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public void removeContact(Contact contact) {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public void updateContact(Contact contact) {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public int addMeeting(Meeting meeting) {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public Contact getMeeting(int id) {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public void removeMeeting(Meeting meeting) {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public void updateMeeting(Meeting meeting) {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

}

