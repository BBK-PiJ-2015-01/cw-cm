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
	//	Test add Future Meeting
	//	*********************************************************************************************

	@Test(expected=NullPointerException.class)
	public void addFutureMeeting_NullDate() {

		instance.addFutureMeeting( Collections.emptySet(), null);		
	}	

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeeting_PastDate() {

		Calendar now = Calendar.getInstance();
		// Make sure the contacts are valid
		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		// Use a past date
		instance.addFutureMeeting(contacts, getPastCalendar());		
	}


	@Test(expected=NullPointerException.class)
	public void addFutureMeeting_NullContacts() {

		instance.addFutureMeeting( null, Calendar.getInstance());		
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeeting_EmptyContacts() {

		instance.addFutureMeeting(Collections.emptySet(), Calendar.getInstance());		
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeeting_UnknownContacts() {
		
		Set<Contact> contacts = new HashSet<>();
		contacts.add(new ContactImpl(-1));
		instance.addFutureMeeting(contacts, Calendar.getInstance());		
	}
	
	@Test
	public void addFutureMeeting_ValidParams() {

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
	public void getFutureMeetingListById_EmptyMeetings() {

		Meeting meeting = instance.getMeeting(-1);
		assertNull(meeting);	
	}

	@Test
	public void getFutureMeetingListById_UnknownMeetingId() {

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
	public void getFutureMeetingListByDate_NoMeetings() {

		List<Meeting> meetings = instance.getFutureMeetingList(Calendar.getInstance());
		assertNotNull(meetings);
		assertTrue(meetings.isEmpty());		
	}

	@Test(expected=NullPointerException.class)
	public void getFutureMeetingListByDate_NullDate() {

		Calendar nullDate = null;
		List<Meeting> meetings = instance.getFutureMeetingList(nullDate);	
	}

	@Test
	public void getFutureMeetingListByDate_SingleMeeting() {

		// Use a valid contact
		Set<Contact> expectedContacts = new HashSet<>();
		Contact validContact = getValidContact();
		expectedContacts.add(validContact);
		// Use a future date
		Calendar expectedDate = getFutureCalendar();
		// Add the meeting
		int meetingId = instance.addFutureMeeting(expectedContacts, expectedDate);	
		// Get the list
		List<Meeting> meetings = instance.getFutureMeetingList(expectedDate);	
		assertNotNull(meetings);
		int exectedListSize = 1;
		int resultListSize = meetings.size();
		assertEquals(exectedListSize, resultListSize);
		// Get the only item
		Meeting resultMeeting = meetings.get(0);	
		Calendar resultDate = resultMeeting.getDate();
		Set<Contact> resultContacts = resultMeeting.getContacts();
		assertEquals(expectedDate, resultDate);
		assertArrayEquals(expectedContacts.toArray(), resultContacts.toArray());
	}
	

	//	*********************************************************************************************
	//	Test addPastMeeting
	//	*********************************************************************************************

	@Test(expected=NullPointerException.class)
	public void addNewPastMeeting_NullDate() {

		instance.addNewPastMeeting( Collections.emptySet(), null, "");		
	}	

	@Test(expected=IllegalArgumentException.class)
	public void addNewPastMeeting_FutureDate() {

		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		instance.addNewPastMeeting( contacts, getFutureCalendar(), "");	
	}

	@Test(expected=NullPointerException.class)
	public void addNewPastMeeting_NullText() {

		instance.addNewPastMeeting( Collections.emptySet(), getPastCalendar(), null);		
	}	

	@Test(expected=NullPointerException.class)
	public void addNewPastMeeting_NullContacts() {

		instance.addNewPastMeeting( null, Calendar.getInstance(), "");		
	}	

	@Test(expected=IllegalArgumentException.class)
	public void addNewPastMeeting_EmptyContacts() {

		instance.addNewPastMeeting( Collections.emptySet(), Calendar.getInstance(), "");		
	}

	@Test(expected=IllegalArgumentException.class)
	public void addNewPastMeeting_ContactDoesNotExist() {

		Set<Contact> contacts = new HashSet<>();
		contacts.add(new ContactImpl(-1));
		instance.addNewPastMeeting( contacts, Calendar.getInstance(), "");		
	}

	@Test
	public void addNewPastMeeting_ValidParams() {

		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		instance.addNewPastMeeting( contacts, getPastCalendar(), "");	
	}

	//	*********************************************************************************************
	//	Test getPastMeetingList by Contact
	//	*********************************************************************************************
	@Test(expected=NullPointerException.class)
	public void getPastMeetingList_NullContact() {

		List<PastMeeting> pastMeetings = instance.getPastMeetingList(null);		
	}

	@Test(expected=IllegalArgumentException.class)
	public void getPastMeetingList_InvalidContact() {

		List<PastMeeting> pastMeetings = instance.getPastMeetingList(new ContactImpl(-1));		
	}

	@Test
	public void getPastMeetingList_ValidContactEmptyList() {

		Contact validContact = getValidContact();
		List<PastMeeting> pastMeetings = instance.getPastMeetingList(validContact);	
		assertNotNull(pastMeetings);	
		assertTrue(pastMeetings.isEmpty());
	}

	@Test
	public void getPastMeetingList_SingleContactMultipleDates() {

		// Valid params
		Set<Contact> contacts = new HashSet<>();
		Contact validContact = getValidContact();
		contacts.add(validContact);
		// Date - 1 day
		Calendar date1DayAgo = Calendar.getInstance();
		date1DayAgo.add(Calendar.DAY_OF_YEAR, -1);
		// Date - 2 days
		Calendar date2DaysAgo = Calendar.getInstance();
		date2DaysAgo.add(Calendar.DAY_OF_YEAR, -2);
		//	
		// Add in reverse order
		//
//		System.out.println("Adding Past Meetings");
		instance.addNewPastMeeting( contacts, date2DaysAgo, "");	
		instance.addNewPastMeeting( contacts, date1DayAgo, "");	
		// Get the list
		List<PastMeeting> pastMeetings = instance.getPastMeetingList(validContact);	
		assertNotNull(pastMeetings);	
		int expectedListSize = 2;
		int resultListSize = pastMeetings.size();
		assertEquals(expectedListSize, resultListSize);
		// 1st item date should be newest i.e. 1 day ago
		Calendar resultDate = pastMeetings.get(0).getDate();
		assertEquals(date1DayAgo, resultDate);
		// 2nd item date should be oldest i.e. 2 days ago
		resultDate = pastMeetings.get(1).getDate();
		assertEquals(date2DaysAgo, resultDate);
	}

	@Test
	public void getPastMeetingList_MultipleContactsMultipleDates() {

		// Valid params
		Set<Contact> contacts = new HashSet<>();
		Contact validContact1 = getValidContact();
		Contact validContact2 = getValidContact();
		Contact validContact3 = getValidContact();
		contacts.add(validContact1);
		contacts.add(validContact2);
		contacts.add(validContact3);
		// Date - 1 day ago
		Calendar date1DayAgo = Calendar.getInstance();
		date1DayAgo.add(Calendar.DAY_OF_YEAR, -1);
		// Date - 2 days ago
		Calendar date2DaysAgo = Calendar.getInstance();
		date2DaysAgo.add(Calendar.DAY_OF_YEAR, -2);
		// Date - 3 days ago
		Calendar date3DaysAgo = Calendar.getInstance();
		date3DaysAgo.add(Calendar.DAY_OF_YEAR, -3);
		//	
		// Add in random date order
		//
//		System.out.println("Adding Past Meetings");
		instance.addNewPastMeeting( contacts, date2DaysAgo, "");	
		instance.addNewPastMeeting( contacts, date1DayAgo, "");	
		instance.addNewPastMeeting( contacts, date3DaysAgo, "");	
		// Get the list for random contact
		List<PastMeeting> pastMeetings = instance.getPastMeetingList(validContact2);	
		assertNotNull(pastMeetings);	
		int expectedListSize = 3;
		int resultListSize = pastMeetings.size();
		assertEquals(expectedListSize, resultListSize);
		// 1st item date should be newest i.e. 1 day ago
		Calendar resultDate = pastMeetings.get(0).getDate();
		assertEquals(date1DayAgo, resultDate);
		// 2nd item date should be middle i.e. 2 days ago
		resultDate = pastMeetings.get(1).getDate();
		assertEquals(date2DaysAgo, resultDate);
		// 3rd item date should be oldest i.e. 3 days ago
		resultDate = pastMeetings.get(2).getDate();
		assertEquals(date3DaysAgo, resultDate);
	}

	//	*********************************************************************************************
	//	Test getPastMeeting by id
	//	*********************************************************************************************
	@Test
	public void getPastMeetingById_Empty() {

		Meeting meeting = instance.getPastMeeting(-1);
		assertNull(meeting);	
	}

	@Test(expected=IllegalArgumentException.class)
	public void getPastMeetingById_MeetingIsActuallyAFutureMeeting() {

		// Add a valid future meeting
		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		int meetingId = instance.addFutureMeeting(contacts, getFutureCalendar());	
		// Get the meeting by id
		PastMeeting resultMeeting = instance.getPastMeeting(meetingId);
	}

	@Test
	public void getPastMeetingById_ValidPastMeeting() {

		// Valid params
		Set<Contact> contacts = new HashSet<>();
		Contact validContact = getValidContact();
		contacts.add(validContact);
		instance.addNewPastMeeting( contacts, getPastCalendar(), "");	
		// Get the list
		List<PastMeeting> pastMeetings = instance.getPastMeetingList(validContact);	
		assertNotNull(pastMeetings);	
		int expectedListSize = 1;
		int resultListSize = pastMeetings.size();
		assertEquals(expectedListSize, resultListSize);
		// 
		PastMeeting expectedMeeting = pastMeetings.get(0);
		int validId = expectedMeeting.getId();
		// Get the meeting by id
		PastMeeting resultMeeting = instance.getPastMeeting(validId);
		assertEquals(expectedMeeting, resultMeeting);
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



