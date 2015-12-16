import java.util.*;
import java.util.stream.*;
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

	ContactManagerModel model = new SerializableContactManagerModel();

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
	
		if (contacts == null || date == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (contacts.isEmpty() || !model.getContacts().containsAll(contacts)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		if (!date.after(Calendar.getInstance())) {
			throw new IllegalArgumentException(INVALID_DATE_MSG);
		}
		return model.addMeeting(date, contacts, null);
	}

	@Override
	public PastMeeting getPastMeeting(int id) {

		ModelMeeting m = model.getMeeting(id);
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

		ModelMeeting m = model.getMeeting(id);
		if (m == null) {
			return null;
		}
		if (isPastDate(m.getDate())) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		return cloneAsFutureMeeting(m);
	}

	@Override
	public Meeting getMeeting(int id) {

		ModelMeeting m = model.getMeeting(id);		
		if (m == null) {
			return null;
		}
		return isFutureDate(m.getDate()) ? cloneAsFutureMeeting(m) : cloneAsPastMeeting(m);
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {

		if (contact == null ) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (!model.getContacts().contains(contact)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}

		List<Meeting> futureMeetings = new LinkedList<>();
		// Filter on contact, sort on date
		Stream<Meeting> futureMeetingStream = model.getMeetings().stream()
			.filter((m) -> m.getContacts().contains(contact))
			.sorted((m1,m2) -> m1.getDate().compareTo(m2.getDate()))
			.map((m) -> m);
		return  futureMeetingStream.collect(Collectors.toList());
	}

	@Override
	public List<Meeting> getMeetingListOn(Calendar date) {

		if (date == null ) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (isPastDate(date)) {
			return Collections.emptyList();
		}
		final int DATE_EQUALITY = 0;
		List<Meeting> futureMeetings = new LinkedList<>();
		// Filter on date
		Stream<Meeting> futureMeetingStream = model.getMeetings().stream()
			.filter((m) -> date.compareTo(m.getDate()) == DATE_EQUALITY)
			.map((m) -> isFutureDate(m.getDate()) ? cloneAsFutureMeeting(m) : cloneAsPastMeeting(m));
		return  futureMeetingStream.collect(Collectors.toList());
	}

	@Override
	public List<PastMeeting> getPastMeetingListFor(Contact contact) {

		if (contact == null ) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (!model.getContacts().contains(contact)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		// Filter on contact and date, sort on date
		Stream<PastMeeting> pastMeetingStream = model.getMeetings().stream()
			.filter((m) -> m.getContacts().contains(contact))
			.filter((m) -> isPastDate(m.getDate()))
			.sorted((m1,m2) -> m1.getDate().compareTo(m2.getDate()))
			.map((m) -> cloneAsPastMeeting(m));
		return  pastMeetingStream.collect(Collectors.toList());

	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {

		if (contacts == null || date == null || text == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (contacts.isEmpty() || !model.getContacts().containsAll(contacts)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		if (isFutureDate(date)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		model.addMeeting(date, contacts, text);
	}

	@Override
	public PastMeeting addMeetingNotes(int id, String text) {

		ModelMeeting m = model.getMeeting(id);
		if (m == null)  {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		if (text == null ) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		if (isFutureDate(m.getDate())) {
			throw new IllegalStateException(ILLEGAL_STATE_MSG);
		}
		m.addNotes(text);
		model.updateMeeting(m);
		return cloneAsPastMeeting(model.getMeeting(id));
	}

	@Override
	public int addNewContact(String name, String notes) {

		if (name == null || notes == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		return model.addContact(name, notes);
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		
		Set<ModelContact> contacts = model.getContacts();
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
		Stream<Contact> contactStream = model.getContacts().stream()
			.filter((c) -> name.equals(c.getName()))
			.map((c) -> cloneAsContact(c));
		return  contactStream.collect(Collectors.toSet());

	}

	@Override
	public void flush() {
	throw new UnsupportedOperationException("Not implemented.");
	}
	//
	//	Convenience methods
	//
	private boolean isFutureDate(Calendar date) {
		return Calendar.getInstance().compareTo(date) < 0;
	}

	private boolean isPastDate(Calendar date) {
		return Calendar.getInstance().compareTo(date) > 0;
	}

	private PastMeetingImpl cloneAsPastMeeting(ModelMeeting m) {

		return getPastMeetingInstance(m.getId(), m.getDate(), m.getContacts(), m.getNotes());
	}

	private FutureMeeting cloneAsFutureMeeting(ModelMeeting m) {

		return getFutureMeetingInstance(m.getId(), m.getDate(), m.getContacts());
	}

	private Contact cloneAsContact(ModelContact c) {

		return getContactInstance(c.getId(), c.getName(), c.getNotes());
	}

	private ContactImpl getContactInstance(int id, String name, String notes) {

		return notes == null ? new ContactImpl(id, name) : new ContactImpl(id, name, notes);
	}


	private FutureMeetingImpl getFutureMeetingInstance(int id, Calendar date , Set<Contact> contacts) {

		return new FutureMeetingImpl(id, date, contacts);
	}

	private PastMeetingImpl getPastMeetingInstance(int id, Calendar date, Set<Contact> contacts, String notes) {

		return new PastMeetingImpl(id, date, contacts, notes);
	}
}

