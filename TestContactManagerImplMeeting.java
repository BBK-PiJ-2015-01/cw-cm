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
		Calendar expectedDate = getFutureCalendar();
		int meetingId = instance.addFutureMeeting(contacts, expectedDate);	
		Meeting meeting = instance.getMeeting(meetingId);	
		assertNotNull(meeting);
		Calendar resultDate = meeting.getDate();
		assertEquals(expectedDate, resultDate);
	}

	//	*********************************************************************************************
	//	Test getFutureMeeting by id
	//	*********************************************************************************************
	@Test
	public void getFutureMeetingListByIdEmptyTest() {

		Meeting meeting = instance.getMeeting(-1);
		assertNull(meeting);	
	}

	@Test
	public void getFutureMeetingListInvalidIdPopulatedTest() {

		// Use a valid contact
		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		// Use a future date
		Calendar expectedDate = getFutureCalendar();
		int meetingId = instance.addFutureMeeting(contacts, expectedDate);
		Meeting meeting = instance.getMeeting(-1);
		assertNull(meeting);	
	}

	//	*********************************************************************************************
	//	Test getFutureMeetingList by Date
	//	*********************************************************************************************

	@Test
	public void getFutureMeetingListByDateEmptyTest() {

		List<Meeting> meetings = instance.getFutureMeetingList(Calendar.getInstance());
		assertNotNull(meetings);
		assertTrue(meetings.isEmpty());		
	}

	//	*********************************************************************************************
	//	Test addPastMeeting
	//	*********************************************************************************************

	@Test(expected=NullPointerException.class)
	public void addNewPastMeetingNullDateTest() {

		instance.addNewPastMeeting( Collections.emptySet(), null, "");		
	}	

	@Test(expected=IllegalArgumentException.class)
	public void addNewPastMeetingInvalidDateTest() {

		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		instance.addNewPastMeeting( contacts, getFutureCalendar(), "");	
	}

	@Test(expected=NullPointerException.class)
	public void addNewPastMeetingNullTextTest() {

		instance.addNewPastMeeting( Collections.emptySet(), Calendar.getInstance(), null);		
	}	

	@Test(expected=NullPointerException.class)
	public void addNewPastMeetingNullContactsTest() {

		instance.addNewPastMeeting( null, Calendar.getInstance(), "");		
	}	

	@Test(expected=IllegalArgumentException.class)
	public void addNewPastMeetingEmptyContactsTest() {

		instance.addNewPastMeeting( Collections.emptySet(), Calendar.getInstance(), "");		
	}

	@Test(expected=IllegalArgumentException.class)
	public void addNewPastMeetingInvalidContactsTest() {

		Set<Contact> contacts = new HashSet<>();
		contacts.add(new ContactImpl(-1));
		instance.addNewPastMeeting( contacts, Calendar.getInstance(), "");		
	}

	@Test(expected=IllegalArgumentException.class)
	public void addNewPastMeetingValidContactsTest() {

		Set<Contact> contacts = new HashSet<>();
		contacts.add(new ContactImpl(-1));
		instance.addNewPastMeeting( contacts, Calendar.getInstance(), "");		
	}

	@Test
	public void addNewPastMeetingValidTest() {

		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		instance.addNewPastMeeting( contacts, getPastCalendar(), "");	
	}

	//	*********************************************************************************************
	//	Test getPastMeetingList by Contact
	//	*********************************************************************************************
	@Test(expected=NullPointerException.class)
	public void getPastMeetingListByNullContactTest() {

		List<PastMeeting> pastMeetings = instance.getPastMeetingList(null);		
	}

	@Test(expected=IllegalArgumentException.class)
	public void getPastMeetingListByInvalidContactTest() {

		List<PastMeeting> pastMeetings = instance.getPastMeetingList(new ContactImpl(-1));		
	}

	@Test
	public void getPastMeetingListByValidContactEmptyListTest() {

		Contact validContact = getValidContact();
		List<PastMeeting> pastMeetings = instance.getPastMeetingList(validContact);	
		assertNotNull(pastMeetings);	
		assertTrue(pastMeetings.isEmpty());
	}

	//	*********************************************************************************************
	//	Test getPastMeeting by id
	//	*********************************************************************************************
	@Test
	public void getPastMeetingByIdEmptyTest() {

		Meeting meeting = instance.getPastMeeting(-1);
		assertNull(meeting);	
	}

//	@Test
	public void getPastMeetingByIdValidTest() {

		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		instance.addNewPastMeeting( contacts, getPastCalendar(), "");
		Meeting meeting = instance.getPastMeeting(-1);
		assertNull(meeting);	
	}

	//	*********************************************************************************************
	//	Utility methods
	//	*********************************************************************************************
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
	private Calendar getPastCalendar() {
	
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);
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



