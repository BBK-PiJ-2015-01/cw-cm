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
	private final String INVALID_PUNIT_STATE = "An error occurred with the persistence unit";
	private final TimeZone tz = new SimpleTimeZone(0, "GMT");
	// TODO: Convert to factory implmentation or dependency injection
	private PersistenceUnit pUnit;
	
	public ContactManagerImpl(String fileName) {
		
		try {
			pUnit = new SerializableFilePersistenceUnit(fileName == null ? ContactManagerDomain.FILENAME : fileName);
		} catch(PersistenceUnitException e) {

			throw new IllegalStateException(INVALID_PUNIT_STATE);
		}
	}

	public ContactManagerImpl() {

		this(ContactManagerDomain.FILENAME);
	}

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
	
		if (contacts == null || date == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
//		if (contacts.isEmpty() || !getModel().getContacts().containsAll(contacts)) {
		if (contacts.isEmpty() || !getModel().contactsExist(contacts)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		if (!isFutureDate(date)) {
			throw new IllegalArgumentException(INVALID_DATE_MSG);
		}
		return getModel().addMeeting(date, contacts, null);
	}

	@Override
	public PastMeeting getPastMeeting(int id) {

		ModelMeeting m = getModel().getMeeting(id);
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

		ModelMeeting m = getModel().getMeeting(id);
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

		ModelMeeting m = getModel().getMeeting(id);		
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
//		if (!getModel().getContacts().contains(contact)) {
		if (!getModel().contactExists(contact)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}

		List<Meeting> futureMeetings = new LinkedList<>();
		// Filter on contact, sort on date
		Stream<Meeting> futureMeetingStream = getModel().getMeetings().stream()
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
		Stream<Meeting> futureMeetingStream = getModel().getMeetings().stream()
			.filter((m) -> date.compareTo(m.getDate()) == DATE_EQUALITY)
			.map((m) -> isFutureDate(m.getDate()) ? cloneAsFutureMeeting(m) : cloneAsPastMeeting(m));
		return  futureMeetingStream.collect(Collectors.toList());
	}

	@Override
	public List<PastMeeting> getPastMeetingListFor(Contact contact) {

		if (contact == null ) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
//		if (!getModel().getContacts().contains(contact)) {
		if (!getModel().contactExists(contact)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		// Filter on contact and date, sort on date
		Stream<PastMeeting> pastMeetingStream = getModel().getMeetings().stream()
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
//		if (contacts.isEmpty() || !getModel().getContacts().containsAll(contacts)) {
		if (contacts.isEmpty() || !getModel().contactsExist(contacts)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		if (isFutureDate(date)) {
			throw new IllegalArgumentException(INVALID_PARAM_MSG);
		}
		getModel().addMeeting(date, contacts, text);
	}

	@Override
	public PastMeeting addMeetingNotes(int id, String text) {

		ModelMeeting m = getModel().getMeeting(id);
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
		getModel().updateMeeting(m);
		return cloneAsPastMeeting(getModel().getMeeting(id));
	}

	@Override
	public int addNewContact(String name, String notes) {

		if (name == null || notes == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		return getModel().addContact(name, notes);
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		
		Set<ModelContact> contacts = getModel().getContacts();
		if (contacts.isEmpty()) {
			throw new IllegalArgumentException(INVALID_ID_MSG);
		}
		Set<Contact> returnContacts = new HashSet<>();
		for(int id : ids) {
			boolean found = false;
			for (ModelContact contact: contacts) {
				if (id == contact.getId()) {
					found = true;
					returnContacts.add(cloneAsContact(contact));
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
		Stream<Contact> contactStream = getModel().getContacts().stream()
			.filter((c) -> name.equals(c.getName()))
			.map((c) -> cloneAsContact(c));
		return  contactStream.collect(Collectors.toSet());

	}

	@Override
	public void flush() {

		try {
			pUnit.commit();
		} catch(PersistenceUnitException e) {
			throw new IllegalStateException(INVALID_PUNIT_STATE);
		}
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

	private ContactManagerModel getModel() {
		
		try {
			return pUnit.getModel();
		} catch(PersistenceUnitException e) {
			throw new IllegalStateException(INVALID_PUNIT_STATE);
		}		
	}
}

