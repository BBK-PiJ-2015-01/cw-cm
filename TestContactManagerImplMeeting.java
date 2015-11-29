import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestContactManagerImplMeeting {

	protected ContactManagerImpl instance;
	
	@Before
	public void init() {
		
		instance = new ContactManagerImpl();
	}

	//	*********************************************************************************************
	//	Tests for Meetings
	//	*********************************************************************************************

	@Test(expected=NullPointerException.class)
	public void addFutureMeetingNullDateTest() {

		instance.addFutureMeeting( Collections.emptySet(), null);		
	}	

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeetingInvalidDateTest() {

		Calendar now = Calendar.getInstance();
		// Make sure the contacts are valid
		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		// Use a non-future date
		instance.addFutureMeeting(contacts, now);		
	}


	@Test(expected=NullPointerException.class)
	public void addFutureMeetingNullContactSetTest() {

		instance.addFutureMeeting( null, Calendar.getInstance());		
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeetingEmptyContactSetTest() {

		instance.addFutureMeeting(Collections.emptySet(), Calendar.getInstance());		
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeetingUnknownContactSetTest() {
		
		Set<Contact> contacts = new HashSet<>();
		contacts.add(new ContactImpl(-1));
		instance.addFutureMeeting(contacts, Calendar.getInstance());		
	}

	
	@Test
	public void addFutureMeetingTest() {

		// Use a valid contact
		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		// Use a future date
		instance.addFutureMeeting(contacts, getFutureCalendar());		
	}

	//	*********************************************************************************************
	//	Test getFutureMeetingList by Date
	//	*********************************************************************************************

	@Test
	public void getFutureMeetingListEmptyTest() {

		List<Meeting> meetings = instance.getFutureMeetingList(Calendar.getInstance());
		assertNotNull(meetings);
		assertTrue(meetings.isEmpty());		
	}

	//
	//	Utility methods
	//
	private Contact getValidContact() {
	
		final String validName = "validName";
		instance.addNewContact(validName, "Notes");
		Set<Contact> contacts = instance.getContacts(validName);
		assertFalse(contacts.isEmpty());
		return contacts.iterator().next();
	}

	private Calendar getFutureCalendar() {
	
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return calendar;
	}

	private boolean assertContactSetContainsContactName(String contactName, Set<Contact> contacts) {

		for (Contact contact: contacts) {
			if (contact.getName().equals(contactName)) {
				return true;
			}
		}
		return false;
	}

	private Contact getContactSetItem(int index, Set<Contact> contacts) {

		int count = 0;
		for (Contact contact: contacts) {
			if (index == count) {
				return contact;
			}
			count++;
		}
		return null;
	}
}



