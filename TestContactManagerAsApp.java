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
		System.out.println(resultMeeting.getClass().getSimpleName());
		assertTrue(resultMeeting instanceof FutureMeeting);
		Set<Contact> resultContacts = resultMeeting.getContacts();
		assertEquals(expectedContacts, resultContacts);		
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



