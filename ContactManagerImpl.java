
import java.util.*;


/**
 *
 * @author user0001
 */
public class ContactManagerImpl implements ContactManager {

	private final String NULL_PARAM_MSG = "A null param was supplied";
	private final String INVALID_ID_MSG = "Supplied id was invalid";
	private final String INVALID_DATE_MSG = "Supplied date was invalid";
	private final String INVALID_PARAM_MSG = "Supplied param was invalid";
	private final TimeZone tz = new SimpleTimeZone(0, "GMT");


	private Set<Contact> contacts = new HashSet<>();
	private Set<Meeting> meetings = new HashSet<>();

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
	
		if (contacts == null || date == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (contacts.isEmpty() || !this.contacts.containsAll(contacts)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		if (!date.after(Calendar.getInstance())) {
			throw new IllegalArgumentException(INVALID_DATE_MSG);
		}
		// TO DO: Replace with factory implementation
		MeetingImpl m = new MeetingImpl(getNextMeetingId());
		m.setDate(date);
		m.setContacts(contacts);
		meetings.add(m);
		return m.getId();
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
		
		for (Meeting meeting: meetings) {
			if (meeting.getId() == id) {
				return meeting;
			}
		}
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
	throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {

		return Collections.emptyList();
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
	throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {

		if (contacts == null || date == null || text == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (contacts.isEmpty() || !this.contacts.containsAll(contacts)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
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
	private int getNextMeetingId() {
	
		return meetings.size() + 1;
	}
}

