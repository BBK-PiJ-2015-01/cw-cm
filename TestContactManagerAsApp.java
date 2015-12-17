import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestContactManagerAsApp {

	protected ContactManager instance;
	
	@Before
	public void init() {
		
		instance = new ContactManagerImpl();
	}
	
	//	*********************************************************************************************
	//	Test adding contacts and restarting application
	//	*********************************************************************************************
	@Test
	public void addContactAndSave() {

		String expectedName = "Expected name";
		String expectedNotes = "Expected notes";
		int id = instance.addNewContact(expectedName, expectedNotes);
		instance.flush();
		// Restart the app
		init();
		Set<Contact> contacts = instance.getContacts(id);
		assertFalse(contacts.isEmpty());		
	}


	@Test
	public void addMeetingAndSave() {

		String expectedName = String.format("Name:%d", System.nanoTime());
		String expectedNotes = "Expected notes";
		int contactId = instance.addNewContact(expectedName, expectedNotes);
		Set<Contact> expectedContacts = instance.getContacts(contactId);
		// Add a new meeting
		Calendar expectedDate = getFutureCalendar();		
		int meetingId = instance.addFutureMeeting(expectedContacts, expectedDate);
		// Flush and restart the app
		instance.flush();
		init();
		Meeting resultMeeting = instance.getMeeting(meetingId);
		assertTrue(resultMeeting instanceof FutureMeeting);
		Set<Contact> resultContacts = resultMeeting.getContacts();
		assertEquals(expectedContacts, resultContacts);		
	}

	@Test
	public void addMeetingSaveAndThenAddNotes() {

		String expectedName = String.format("Name:%d", System.nanoTime());
		String expectedNotes = "Expected notes";
		final int WAIT_TIME_MS = 100;
		int contactId = instance.addNewContact(expectedName, expectedNotes);
		Set<Contact> expectedContacts = instance.getContacts(contactId);
		// Add a new meeting
		Calendar expectedDate = Calendar.getInstance();	
		expectedDate.add(Calendar.MILLISECOND, WAIT_TIME_MS);
		int meetingId = instance.addFutureMeeting(expectedContacts, expectedDate);		
		// Sleep, flush and restart the app
		try {
			Thread.sleep(WAIT_TIME_MS);
		} catch (InterruptedException ex) {
			System.out.println("Sleep was interrupted");
		}
		instance.flush();
		init();
		// Should new be a past meeting	
		String expectedMeetingNotes = "Expected meeting notes";
		PastMeeting resultMeeting = instance.addMeetingNotes(meetingId, expectedMeetingNotes);		
		String resultMeetingNotes = resultMeeting.getNotes();
		assertEquals(expectedMeetingNotes, resultMeetingNotes);
	}

	//	*********************************************************************************************
	//	Test lists by date, name, contact etc.
	//	*********************************************************************************************
	@Test
	public void getFutureMeetingsByContactName() {

		// Create a unique Contact name
		String expectedName = String.format("Name:%d", System.nanoTime());;
		int contactId = instance.addNewContact(expectedName, "");
		Set<Contact> expectedContacts = instance.getContacts(contactId);
		// Add some meetings using this Contact
		Calendar expectedDate = getFutureCalendar();	
		int expectedMeetingsSize = 5;
		Set<Meeting> addedMeetings = new HashSet<>();
		for(int i = 0; i < expectedMeetingsSize ; i++) {
			int id = instance.addFutureMeeting(expectedContacts, expectedDate );
			addedMeetings.add(instance.getMeeting(id));			
		}
		// Flush and restart the app
		instance.flush();
		init();
		// Search should return all the meetings added above
		Contact expectedContact = instance.getContacts(expectedName).stream().findFirst().get();
		List<Meeting> resultMeetings = instance.getFutureMeetingList(expectedContact);
		assertTrue(resultMeetings.containsAll(addedMeetings));
	}	

	@Test
	public void getPastMeetingsByContactName() {

		// Create a unique Contact name
		String expectedName = String.format("Name:%d", System.nanoTime());
		int contactId = instance.addNewContact(expectedName, "");
		Set<Contact> expectedContacts = instance.getContacts(contactId);
		// Add some meetings using this Contact
		Calendar expectedDate = getPastCalendar();	
		int expectedMeetingsSize = 5;
		for(int i = 0; i < expectedMeetingsSize ; i++) {
			instance.addNewPastMeeting(expectedContacts, expectedDate, "Not interested in the meeting notes" );			
		}
		// Flush and restart the app
		instance.flush();
		init();
		// Search should return all the meetings added above
		Contact expectedContact = instance.getContacts(expectedName).stream().findFirst().get();
		List<PastMeeting> resultMeetings = instance.getPastMeetingListFor(expectedContact);
		int resultListSize = resultMeetings.size();
		assertEquals(expectedMeetingsSize, resultListSize);
	}

	@Test
	public void getContactsById() {

		// Create a unique Contact name
		String expectedName = String.format("Name:%d", System.nanoTime());
		// Add some Contacts
		Set<Contact> expectedContacts = new HashSet<>();			
		int expectedContactsSize = 5;
		int[] contactIds = new int[expectedContactsSize];
		for(int i = 0; i < expectedContactsSize ; i++) {
			int contactId = instance.addNewContact(expectedName, "");
			expectedContacts.add(instance.getContacts(contactId).stream().findFirst().get());
			contactIds[i] = contactId;
		}
		// Flush and restart the app
		instance.flush();
		init();
		// Search should return all the contacts added above
		Set<Contact> resultContacts = instance.getContacts(contactIds);
		assertTrue(resultContacts.containsAll(expectedContacts));
	}

	@Test
	public void getContactsByName() {

		// Create a unique Contact name
		String expectedName = String.format("Name:%d", System.nanoTime());
		// Add some Contacts
		Set<Contact> expectedContacts = new HashSet<>();			
		int expectedContactsSize = 5;
		for(int i = 0; i < expectedContactsSize ; i++) {
			int contactId = instance.addNewContact(expectedName, "");
			expectedContacts.add(instance.getContacts(contactId).stream().findFirst().get());
		}
		// Flush and restart the app
		instance.flush();
		init();
		// Search should return all the contacts added above
		Set<Contact> resultContacts = instance.getContacts(expectedName);
		assertTrue(resultContacts.containsAll(expectedContacts));
	}
	//	*********************************************************************************************
	//	Test for immutability across commits
	//	*********************************************************************************************
	@Test
	public void addContactAndTryToChangeWithoutUsingContactManagerMethods() {

		String expectedName = String.format("Name:%d", System.nanoTime());
		String expectedNotes = "Expected notes";
		int contactId = instance.addNewContact(expectedName, expectedNotes);
		Contact addedContact = instance.getContacts(contactId).stream().findFirst().get();
		// Change the  notes
		addedContact.addNotes("Should not see these anywhere");
		// Flush the app
		instance.flush();
		addedContact = instance.getContacts(contactId).stream().findFirst().get();
		assertEquals(expectedNotes, addedContact.getNotes());		
	}
	//	*********************************************************************************************
	//	Test scalability
	//	*********************************************************************************************
//	@Test
	public void addLargeNumberOfContacts() {

		String expectedName = String.format("Name:%d", System.nanoTime());
		int expectedContactsSize = 100000;
		for(int i = 0; i < expectedContactsSize; i++) {
			instance.addNewContact(expectedName, "Not notable");			
		}
		// Flush and restart the app
		instance.flush();
		init();
		// Check that all Contacts found
		Set<Contact> resultContacts = instance.getContacts(expectedName);
		int resultContactsSize = resultContacts.size();
		assertEquals(expectedContactsSize, resultContactsSize);
	}

//	@Test
	public void addLargeNumberOfFutureMeetings() {

		String expectedName = String.format("Name:%d", System.nanoTime());
		int contactId = instance.addNewContact(expectedName, "Not notable");	
		Set<Contact> contacts = instance.getContacts(contactId);
		Calendar expectedDate = getFutureCalendar();
		int expectedMeetingsSize = 100000;
		for(int i = 0; i < expectedMeetingsSize; i++) {
			instance.addFutureMeeting(contacts, expectedDate);			
		}
		// Flush and restart the app
		instance.flush();
		init();
		// Check that all Contacts found
		List<Meeting> resultMeetings = instance.getMeetingListOn(expectedDate);
		int resultMeetingsSize = resultMeetings.size();
		assertEquals(expectedMeetingsSize, resultMeetingsSize);
	}

	//	*********************************************************************************************
	//	Convenience methods
	//	*********************************************************************************************
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
}



