
import java.util.*;


/**
 *
 * @author user0001
 */
public class ContactManagerImpl implements ContactManager {

	private final String NULL_PARAM_MSG = "A null param was supplied";
	private final String INVALID_ID_MSG = "Supplied id was invalid";


	private Set<Contact> contacts = new HashSet<>();

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
	throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
	throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
	throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public Meeting getMeeting(int id) {
	throw new UnsupportedOperationException("Not implemented."); 
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
	throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
	throw new UnsupportedOperationException("Not implemented."); 
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
	throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
	throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public void addMeetingNotes(int id, String text) {
	throw new UnsupportedOperationException("Not implemented."); 
	}

	@Override
	public void addNewContact(String name, String notes) {

		if (name == null || notes == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		// TO DO: Replace with factory implementation
		ContactImpl newContact = new ContactImpl(getNextContactId());
		newContact.setName(name);
		newContact.addNotes(notes);
		contacts.add(newContact);
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		
		if (contacts.isEmpty()) {
			throw new IllegalArgumentException(INVALID_ID_MSG);
		}
		Set<Contact> returnContacts = new HashSet<>();
		for(int id : ids) {
			boolean found = false;
			for (Contact contact: contacts) {
				if (id == contact.getId()) {
					found = true;
					returnContacts.add(contact);
					break;
				}
			}
			if (!found) {
				throw new IllegalArgumentException(INVALID_ID_MSG + ": " + id);
			}
		}
		return returnContacts;		
	}

	@Override
	public Set<Contact> getContacts(String name) {

		if (name == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		Set<Contact> returnContacts = new HashSet<>();
		for (Contact contact: contacts) {
			if (name.equals(contact.getName())) {
				returnContacts.add(contact);
			}
		}
		return returnContacts;
	}

	@Override
	public void flush() {
	throw new UnsupportedOperationException("Not implemented.");
	}
	//
	//
	//
	private int getNextContactId() {
	
		return contacts.size() + 1;
	}	
	

}

