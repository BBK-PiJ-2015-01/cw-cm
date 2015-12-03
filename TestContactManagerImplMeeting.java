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
		instance.addFutureMeeting(expectedContacts, expectedDate);	
		// Get the list
		List<Meeting> meetings = instance.getFutureMeetingList(expectedDate);	
		assertNotNull(meetings);
		int expectedListSize = 1;
		int resultListSize = meetings.size();
		assertEquals(expectedListSize, resultListSize);
		// Get the only item
		Meeting resultMeeting = meetings.get(0);	
		Calendar resultDate = resultMeeting.getDate();
		Set<Contact> resultContacts = resultMeeting.getContacts();
		assertEquals(expectedDate, resultDate);
		assertArrayEquals(expectedContacts.toArray(), resultContacts.toArray());
	}

	@Test
	public void getFutureMeetingListByDate_MultipleMeetings() {

		// Use a valid contact
		Set<Contact> expectedContacts = new HashSet<>();
		expectedContacts.add(getValidContact());
		// Use a future date
		Calendar expectedDate = getFutureCalendar();
		int expectedListSize = 5;
		int[] allValidIds = new int[expectedListSize];
		for (int i = 0; i < expectedListSize; i++) {
			allValidIds[i] = instance.addFutureMeeting(expectedContacts, expectedDate);
		}
		// Add some meetings for another future date
		Calendar anotherFutureDate = getFutureCalendar();
		anotherFutureDate.add(Calendar.DAY_OF_YEAR, 3);
		for (int i = 0; i < expectedListSize; i++) {
			instance.addFutureMeeting(expectedContacts, anotherFutureDate);
		}
		// Get the list. 2x expectedListSize meetings have been added
		// Only 1x expectedListSize meetings should be returned for expectedDate
		List<Meeting> meetings = instance.getFutureMeetingList(expectedDate);	
		assertNotNull(meetings);
		int resultListSize = meetings.size();
		assertEquals(expectedListSize, resultListSize);
		// Ensure all added meeting ids are found
		for (int j = 0; j < expectedListSize; j++) {
			boolean found = false;
			for (Meeting m : meetings) {
				if (m.getId() == allValidIds[j]) {
					found = true;
					break;
				}
			}
			if (!found) {
				fail("Id not found: " + j);
			}
		}
	}

	@Test
	public void getFutureMeetingListByDate_NoMatchingMeetings() {

		// Use a valid contact
		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		// Use some future date
		Calendar someFutureDate = getFutureCalendar();
		someFutureDate.add(Calendar.DAY_OF_YEAR, 3);
		int listSize = 5;
		for (int i = 0; i < listSize; i++) {
			instance.addFutureMeeting(contacts, someFutureDate);
		}
		// Use a different future date. No meetings should be returned
		Calendar expectedDate = getFutureCalendar();
		int expectedListSize = 0;
		List<Meeting> meetings = instance.getFutureMeetingList(expectedDate);	
		assertNotNull(meetings);
		int resultListSize = meetings.size();
		assertEquals(expectedListSize, resultListSize);
	}

	//	*********************************************************************************************
	//	Test getFutureMeetingList by Contact
	//	*********************************************************************************************



	@Test(expected=NullPointerException.class)
	public void getFutureMeetingListByContact_NullContact() {

		Contact nullContact = null;
		List<Meeting> meetings = instance.getFutureMeetingList(nullContact);	
	}

	@Test(expected=IllegalArgumentException.class)
	public void getFutureMeetingListByContact_UnknownContact() {

		Contact unknownContact = new ContactImpl(-1);
		List<Meeting> meetings = instance.getFutureMeetingList(unknownContact);	
	}

	@Test
	public void getFutureMeetingListByContact_ValidContactNoMeetings() {

		// Use a valid contact
		Set<Contact> contacts = new HashSet<>();
		Contact validContact = getValidContact();
		contacts.add(validContact);
		List<Meeting> meetings = instance.getFutureMeetingList(validContact);	
		assertNotNull(meetings);
		assertTrue(meetings.isEmpty());		
	}

	@Test
	public void getFutureMeetingListByDate_ValidContactSingleMeeting() {

		// Use a valid contact
		Set<Contact> expectedContacts = new HashSet<>();
		Contact validContact = getValidContact();
		expectedContacts.add(validContact);
		// Use a future date
		Calendar expectedDate = getFutureCalendar();
		// Add the meeting
		instance.addFutureMeeting(expectedContacts, expectedDate);	
		// Get the list
		List<Meeting> meetings = instance.getFutureMeetingList(validContact);	
		assertNotNull(meetings);
		int expectedListSize = 1;
		int resultListSize = meetings.size();
		assertEquals(expectedListSize, resultListSize);
		// Get the only item
		Meeting resultMeeting = meetings.get(0);	
		Calendar resultDate = resultMeeting.getDate();
		Set<Contact> resultContacts = resultMeeting.getContacts();
		assertEquals(expectedDate, resultDate);
		assertArrayEquals(expectedContacts.toArray(), resultContacts.toArray());
	}

	@Test
	public void getFutureMeetingListByDate_ValidContactMultipleMeetingDates() {

		// Use a valid contact
		Set<Contact> expectedContacts = new HashSet<>();
		Contact validContact = getValidContact();
		expectedContacts.add(validContact);
		// Date - 1 day ahead
		Calendar date1DayAhead = Calendar.getInstance();
		date1DayAhead.add(Calendar.DAY_OF_YEAR, 1);
		// Date - 2 days ahead
		Calendar date2DaysAhead = Calendar.getInstance();
		date2DaysAhead.add(Calendar.DAY_OF_YEAR, 2);
		// Date - 3 days ahead
		Calendar date3DaysAhead = Calendar.getInstance();
		date3DaysAhead.add(Calendar.DAY_OF_YEAR, 3);
		// Add the meetings
		instance.addFutureMeeting(expectedContacts, date3DaysAhead);	
		instance.addFutureMeeting(expectedContacts, date1DayAhead);	
		instance.addFutureMeeting(expectedContacts, date2DaysAhead);	
		// Get the list
		List<Meeting> meetings = instance.getFutureMeetingList(validContact);	
		assertNotNull(meetings);
		int expectedListSize = 3;
		int resultListSize = meetings.size();
		assertEquals(expectedListSize, resultListSize);
		// 1st item date should be newest i.e. 1 day ahead
		Calendar resultDate = meetings.get(0).getDate();
		assertEquals(date1DayAhead, resultDate);
		// 2nd item date should be middle i.e. 2 days ahead
		resultDate = meetings.get(1).getDate();
		assertEquals(date2DaysAhead, resultDate);
		// 3rd item date should be latest i.e. 3 days ahead
		resultDate = meetings.get(2).getDate();
		assertEquals(date3DaysAhead, resultDate);
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
		// 1st item date should be newest i.e. 2 days ago
		Calendar resultDate = pastMeetings.get(0).getDate();
		assertEquals(date2DaysAgo, resultDate);
		// 2nd item date should be oldest i.e. 1 day ago 
		resultDate = pastMeetings.get(1).getDate();
		assertEquals(date1DayAgo, resultDate);
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
		instance.addNewPastMeeting( contacts, date3DaysAgo, "");	
		instance.addNewPastMeeting( contacts, date1DayAgo, "");	
		instance.addNewPastMeeting( contacts, date2DaysAgo, "");	
		// Get the list for random contact
		List<PastMeeting> pastMeetings = instance.getPastMeetingList(validContact2);	
		assertNotNull(pastMeetings);	
		int expectedListSize = 3;
		int resultListSize = pastMeetings.size();
		assertEquals(expectedListSize, resultListSize);
		// 1st item date should be oldest i.e. 3 days ago
		Calendar resultDate = pastMeetings.get(0).getDate();
		assertEquals(date3DaysAgo, resultDate);
		// 2nd item date should be middle i.e. 2 days ago
		resultDate = pastMeetings.get(1).getDate();
		assertEquals(date2DaysAgo, resultDate);
		// 3rd item date should be newest i.e. 1 day ago
		resultDate = pastMeetings.get(2).getDate();
		assertEquals(date1DayAgo, resultDate);
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
	//	Add meeting notes
	//	*********************************************************************************************

	@Test(expected=IllegalArgumentException.class)
	public void addMeetingNotes_NonexistentMeeting() {

		instance.addMeetingNotes(-1, null);	
	}

	@Test(expected=NullPointerException.class)
	public void addMeetingNotes_NullNotes() {

		// Use a valid contact
		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		// Use a future date
		Calendar date = getFutureCalendar();
		//
		int id = instance.addFutureMeeting( contacts, date);
		// use the created meeting id
		instance.addMeetingNotes(id, null);	
	}

	@Test(expected=IllegalStateException.class)
	public void addMeetingNotes_FutureMeeting() {

		// Use a valid contact
		Set<Contact> contacts = new HashSet<>();
		contacts.add(getValidContact());
		// Use a future date
		Calendar date = getFutureCalendar();
		//
		int id = instance.addFutureMeeting( contacts, date);
		// use the created meeting id
		instance.addMeetingNotes(id, "");	
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



