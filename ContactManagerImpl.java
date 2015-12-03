
import java.util.*;

/**
 *
 * @author Simon Baird
 */
public class ContactManagerImpl implements ContactManager {

	private final String NULL_PARAM_MSG = "A null param was supplied";
	private final String INVALID_ID_MSG = "Supplied id was invalid";
	private final String INVALID_DATE_MSG = "Supplied date was invalid";
	private final String INVALID_PARAM_MSG = "Supplied param was invalid";
	private final String ILLEGAL_STATE_MSG = "Supplied params we not valid for this operation";
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

		Meeting m = getMeeting(id);
		if (m == null) {
			return null;
		}
		if (isFutureDate(m.getDate())) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		return cloneAsPastMeeting(m);
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {

		Meeting m = getMeeting(id);
		if (m == null) {
			return null;
		}
		if (isPastDate(m.getDate())) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
	throw new UnsupportedOperationException("Not implemented.");
//		return cloneAsFutureMeeting(m);
	}

	@Override
	public Meeting getMeeting(int id) {
		
		for (Meeting meeting: meetings) {
			if (meeting.getId() == id) {
				// TO DO: Return copy, not original
				return meeting;
			}
		}
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {

		if (contact == null ) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (!contacts.contains(contact)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		if (meetings.isEmpty()) {
			return Collections.emptyList();
		}
		List<Meeting> futureMeetings = new LinkedList<>();
		// Filter on contact
		meetings.stream()
			.filter((m) -> m.getContacts().contains(contact))
			.forEach((m) -> futureMeetings.add(m));
		// Sort meetings on meeting date
		Collections.sort(futureMeetings, (m1,m2) -> m1.getDate().compareTo(m2.getDate()));
		return futureMeetings;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {

		if (date == null ) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (meetings.isEmpty() || isPastDate(date)) {
			return Collections.emptyList();
		}
		final int dateEquality = 0;
		List<Meeting> futureMeetings = new LinkedList<>();
		// Filter on date
		meetings.stream()
			.filter((m) -> date.compareTo(m.getDate()) == dateEquality)
			.forEach((m) -> futureMeetings.add(m));
		return futureMeetings;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {

		if (contact == null ) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (!contacts.contains(contact)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		if (meetings.isEmpty()) {
			return Collections.emptyList();
		}
		List<PastMeeting> pastMeetings = new LinkedList<>();
		// Filter on contact and date
		meetings.stream()
			.filter((m) -> m.getContacts().contains(contact))
			.filter((m) -> isPastDate(m.getDate()))
			.forEach((m) -> pastMeetings.add(cloneAsPastMeeting(m)));

		// Sort meetings on meeting date
		Collections.sort(pastMeetings, (m1,m2) -> m1.getDate().compareTo(m2.getDate()));
		return pastMeetings;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {

		if (contacts == null || date == null || text == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (contacts.isEmpty() || !this.contacts.containsAll(contacts)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		if (isFutureDate(date)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		// TO DO: Replace with factory implementation
		PastMeetingImpl m = new PastMeetingImpl(getNextMeetingId());
		m.setDate(date);
		m.setContacts(contacts);
		m.setNotes(text);
		meetings.add(m);
	}

	@Override
	public void addMeetingNotes(int id, String text) {

		Meeting m = getMeeting(id);
		if (m == null)  {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		if (text == null ) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (isFutureDate(m.getDate())) {
			throw new IllegalStateException(ILLEGAL_STATE_MSG);
		}
		PastMeetingImpl pm =  cloneAsPastMeeting(m);
		pm.setNotes(text);
		meetings.remove(m);
		meetings.add(pm);
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
	private boolean isFutureDate(Calendar date) {
		return Calendar.getInstance().compareTo(date) < 0;
	}

	private boolean isPastDate(Calendar date) {
		return Calendar.getInstance().compareTo(date) > 0;
	}

	private PastMeetingImpl cloneAsPastMeeting(Meeting m) {
		// TO DO: Replace with factory implementation
		// TO DO: Make defensive copy	
		PastMeetingImpl pm = new PastMeetingImpl(m.getId());
		pm.setDate(m.getDate());
		pm.setContacts(m.getContacts());
		if (m instanceof PastMeeting) {
			pm.setNotes(((PastMeeting) m).getNotes());
		}
		return pm;
	}

	private FutureMeeting cloneAsFutureMeeting(Meeting m) {
		// TO DO: Replace with factory implementation
		// TO DO: Make defensive copy	
		FutureMeetingImpl fm = new FutureMeetingImpl(m.getId());
		fm.setDate(m.getDate());
		fm.setContacts(m.getContacts());
		return fm;
	}
}

