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

		String expectedName = "Expected name";
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

		String expectedName = "Expected name";
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
	public void getMeetingsByContact() {

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



