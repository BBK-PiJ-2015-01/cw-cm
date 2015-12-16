import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;

//
//	Don't forget org.junit.runner.JUnitCore !
//
public class TestSerializableContactManagerModel {

	protected SerializableContactManagerModel instance;
	
	@Before
	public void init() {
		
		instance = getInstance();
//		System.out.println(System.nanoTime());
	}
	
	@Test
	public void instanceIsSerializable() throws IOException {
		assertTrue(instance instanceof Serializable);
		ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
		oos.writeObject(instance);

	}
	// *****************************************************************************************************************	
	// Add Contact and Get Contact tests
	// *****************************************************************************************************************	

	@Test
	public void addContact_Null() {

		int resultId = instance.addContact(null, null);	
		assertTrue(resultId > 0);
	}

	@Test
	public void addContact_NotNull() {

		int resultId = instance.addContact("name", "notes");	
		assertTrue(resultId > 0);		
	}

	@Test
	public void getContact_DoesNotExist() {

		ModelContact contact = instance.getContact(1);
		assertNull(contact);				
	}

	@Test
	public void getContact_Exists() {

		int resultId = instance.addContact(null, null);			
		ModelContact contact = instance.getContact(resultId);
		assertNotNull(contact);				
	}

	@Test
	public void getContact_CheckValues() {

		String expectedName = "name";
		String expectedNotes = "notes";
		int resultId = instance.addContact(expectedName, expectedNotes);		
		ModelContact resultContact = instance.getContact(resultId);
		assertEquals(expectedName, resultContact.getName());	
		assertEquals(expectedNotes, resultContact.getNotes());					
	}

	@Test
	public void getContact_CheckImmutableUpdatesOnResult() {

		String expectedName = "name";
		String expectedNotes = "notes";
		int resultId = instance.addContact(expectedName, expectedNotes);	
		ModelContact resultContact = instance.getContact(resultId);

		resultContact.setName("Changed name");
		resultContact.addNotes("Changed notes");

		assertNotEquals(expectedName, resultContact.getName());	
		assertNotEquals(expectedNotes, resultContact.getNotes());								
	}

	// *****************************************************************************************************************	
	// Get Contacts tests
	// *****************************************************************************************************************	

	@Test
	public void getContacts_EmptySet() {

		Set<ModelContact> contacts = instance.getContacts();
		assertNotNull(contacts);							
	}

	@Test
	public void getContacts_PopulatedSet() {

		instance.addContact(null, null);
		Set<ModelContact> resultContacts = instance.getContacts();

		Set<ModelContact> contacts = instance.getContacts();	
		int expectedContactsSize = 1;
		int resultContactsSize	= resultContacts.size();
		assertEquals(expectedContactsSize, resultContactsSize);			
	}

	@Test
	public void getContacts_ImmutabilityTest() {

		String expectedName = "name";
		String expectedNotes = "notes";
		int resultId = instance.addContact(expectedName, expectedNotes);	
		Set<ModelContact> resultContacts = instance.getContacts();

		Set<ModelContact> contacts = instance.getContacts();	
		ModelContact setContact = (ModelContact) contacts.stream().findFirst().get();
		ModelContact getContact = instance.getContact(resultId);
		assertNotSame(setContact , getContact);	
		assertEquals(expectedName, setContact.getName());	
		assertEquals(expectedNotes, setContact.getNotes());	
		assertEquals(expectedName, getContact.getName());	
		assertEquals(expectedNotes, getContact.getNotes());		
	}
	// *****************************************************************************************************************	
	// Update Contacts tests
	// *****************************************************************************************************************	

	@Test(expected=NullPointerException.class)
	public void updateContact_NullContact() {

		instance.updateContact(null);				
	}

	@Test(expected=IllegalStateException.class)
	public void updateContact_NotFound() {

		instance.updateContact(getContactInstance());				
	}

	@Test
	public void updateContact_ImmutabilityTest() {

	
		int initialId = instance.addContact(null, null);
	
		ModelContact intialContact = instance.getContact(initialId);
		intialContact.setName("name");
		intialContact.addNotes("notes");
		instance.updateContact(intialContact);

		ModelContact resultContact = instance.getContact(initialId);
		assertNotSame(intialContact , resultContact);	
		assertEquals(intialContact.getName(), resultContact.getName());
		assertNotSame(intialContact.getName() , resultContact.getName());
		assertEquals(intialContact.getNotes(), resultContact.getNotes());
		assertNotSame(intialContact.getNotes() , resultContact.getNotes());			
	}
	// *****************************************************************************************************************	
	// Remove Contacts tests
	// *****************************************************************************************************************	

	@Test(expected=NullPointerException.class)
	public void removeContact_NullContact() {

		instance.removeContact(null);				
	}

	@Test(expected=IllegalStateException.class)
	public void removeContact_NotFound() {

		instance.removeContact(getContactInstance());				
	}

	@Test
	public void removeContact() {
		
		int initialId = instance.addContact(null, null);	
		ModelContact intialContact = instance.getContact(initialId);

		instance.removeContact(intialContact);	
		ModelContact removedContact = instance.getContact(initialId);
		assertNull(removedContact);
				
	}
	//	*********************************************************************************************
	//	Test existence of Contact
	//	*********************************************************************************************
	@Test(expected=NullPointerException.class)
	public void contactExistsTest_NullContact() {

		instance.contactExists(null);		
	}

	@Test
	public void contactExistsTest_NotCreated() {

		Contact expectedContact = new ContactImpl(1, "Name");
		boolean expectedExists = false;
		boolean resultExists = instance.contactExists(expectedContact);		
		assertEquals(expectedExists, resultExists);
	}
	// *****************************************************************************************************************	
	// Add Meeting and Get Meeting tests
	// *****************************************************************************************************************	

	@Test
	public void addMeeting_Null() {

		int resultId = instance.addMeeting(null, null, null);	
		assertTrue(resultId > 0);	
	}

	@Test
	public void addMeeting_NotNull() {

		int resultId = instance.addMeeting(Calendar.getInstance(), Collections.emptySet(), "Notes");
		assertTrue(resultId > 0);		
	}

	@Test
	public void getMeeting_DoesNotExist() {

		ModelMeeting meeting = instance.getMeeting(1);
		assertNull(meeting);				
	}

	@Test
	public void getMeeting_Exists() {

		int resultId = instance.addMeeting(null, null, null);		
		ModelMeeting meeting = instance.getMeeting(resultId);
		assertNotNull(meeting);				
	}

	@Test
	public void getMeeting_CheckValues() {

		Calendar expectedDate = Calendar.getInstance();
		String expectedNotes = "notes";

		Set<Contact> expectedContacts = new HashSet<>();
		int contactId = instance.addContact("name", "notes");
		Contact meetingContact = instance.getContact(contactId);	
		expectedContacts.add(meetingContact);

		int resultId = instance.addMeeting(expectedDate, expectedContacts, expectedNotes);	
	
		ModelMeeting resultMeeting = instance.getMeeting(resultId);
		assertEquals(expectedDate, resultMeeting.getDate());	
		assertEquals(expectedNotes, resultMeeting.getNotes());	
		assertEquals(expectedContacts, resultMeeting.getContacts());				
	}

	@Test
	public void getMeeting_CheckImmutableUpdatesOnExpected() {

		Calendar expectedDate = Calendar.getInstance();
		String expectedNotes = "notes";

		Set<Contact> expectedContacts = new HashSet<>();
		int contactId = instance.addContact("name", "notes");
		Contact meetingContact = instance.getContact(contactId);	
		expectedContacts.add(meetingContact);

		int resultId = instance.addMeeting(expectedDate, expectedContacts, expectedNotes);
		ModelMeeting expectedMeeting = instance.getMeeting(resultId);

		Calendar newDate = Calendar.getInstance();
		newDate.add(Calendar.HOUR, 12);
		expectedMeeting.setDate(newDate);
		expectedMeeting.addNotes("Changed notes");
		expectedContacts = expectedMeeting.getContacts();

		int newContactId = instance.addContact("new name", "new notes");
		Contact newMeetingContact = instance.getContact(newContactId);
		expectedContacts.add(newMeetingContact);
		expectedMeeting.setContacts(expectedContacts);

		ModelMeeting resultMeeting = instance.getMeeting(resultId);
		assertNotEquals(expectedMeeting.getDate(), resultMeeting.getDate());	
		assertNotEquals(expectedMeeting.getNotes(), resultMeeting.getNotes());	
		int expectedContactsSize = expectedMeeting.getContacts().size();
		int resultContactsSize = resultMeeting.getContacts().size();
		assertNotEquals(expectedContactsSize, resultContactsSize);								
	}

	@Test
	public void getMeeting_CheckImmutableUpdatesOnResult() {

		Calendar expectedDate = Calendar.getInstance();
		String expectedNotes = "notes";

		Set<Contact> expectedContacts = new HashSet<>();
		int contactId = instance.addContact("name", "notes");
		Contact meetingContact = instance.getContact(contactId);	
		expectedContacts.add(meetingContact);

		int resultId = instance.addMeeting(expectedDate, expectedContacts, expectedNotes);
		ModelMeeting expectedMeeting = instance.getMeeting(resultId);

		ModelMeeting resultMeeting = instance.getMeeting(resultId);

		Calendar newDate = Calendar.getInstance();
		newDate.add(Calendar.HOUR, 1);
		resultMeeting.setDate(newDate);
		resultMeeting.addNotes("Changed notes");
		Set<Contact> resultContacts  = resultMeeting.getContacts();
		int newContactId = instance.addContact("new name", "new notes");
		Contact newMeetingContact = instance.getContact(newContactId);
		resultContacts.add(newMeetingContact);
		resultMeeting.setContacts(resultContacts);

		assertNotEquals(expectedMeeting.getDate(), resultMeeting.getDate());	
		assertNotEquals(expectedMeeting.getNotes(), resultMeeting.getNotes());	
		int expectedContactsSize = expectedMeeting.getContacts().size();
		int resultContactsSize = resultMeeting.getContacts().size();
		assertNotEquals(expectedContactsSize, resultContactsSize);								
	}

	// *****************************************************************************************************************	
	// Get Meetings tests
	// *****************************************************************************************************************	

	@Test
	public void getMeetings_EmptySet() {

		Set<ModelMeeting> meetings = instance.getMeetings();
		assertNotNull(meetings);							
	}

	@Test
	public void getMeetings_PopulatedSet() {

		instance.addMeeting(null, null, null);
		Set<ModelMeeting> resultMeetings = instance.getMeetings();

		Set<ModelMeeting> meetings = instance.getMeetings();	
		int expectedMeetingsSize = 1;
		int resultMeetingsSize	= resultMeetings.size();
		assertEquals(expectedMeetingsSize, resultMeetingsSize);			
	}

	@Test
	public void getMeetings_ImmutabilityTest() {

		Calendar expectedDate = Calendar.getInstance();
		String expectedNotes = "notes";

		Set<Contact> expectedContacts = new HashSet<>();
		int contactId = instance.addContact("name", "notes");
		Contact meetingContact = instance.getContact(contactId);	
		expectedContacts.add(meetingContact);

		int resultId = instance.addMeeting(expectedDate, expectedContacts, expectedNotes);

		Set<ModelMeeting> meetings = instance.getMeetings();	
		ModelMeeting setMeeting = (ModelMeeting) meetings.stream().findFirst().get();
		ModelMeeting getMeeting = instance.getMeeting(resultId);
		assertNotSame(setMeeting , getMeeting);		
	}
	// *****************************************************************************************************************	
	// Update Meeting tests
	// *****************************************************************************************************************	

	@Test(expected=NullPointerException.class)
	public void updateMeeting_NullContact() {

		instance.updateMeeting(null);				
	}

	@Test(expected=IllegalStateException.class)
	public void updateMeeting_NotFound() {

		instance.updateMeeting(getMeetingInstance());				
	}

	@Test
	public void updateMeeting_ImmutabilityTest() {

	
		int initialId = instance.addMeeting(null, null, null);
		ModelMeeting intialMeeting = instance.getMeeting(initialId);

		intialMeeting.setDate(Calendar.getInstance());
		intialMeeting.addNotes("notes");
		instance.updateMeeting(intialMeeting);

		ModelMeeting resultMeeting = instance.getMeeting(initialId);
		assertNotSame(intialMeeting , resultMeeting);	
		assertEquals(intialMeeting.getDate(), resultMeeting.getDate());
		assertNotSame(intialMeeting.getDate() , resultMeeting.getDate());
		assertEquals(intialMeeting.getNotes(), resultMeeting.getNotes());
		assertNotSame(intialMeeting.getNotes() , resultMeeting.getNotes());			
	}

	// *****************************************************************************************************************	
	// Remove Meeting tests
	// *****************************************************************************************************************	

	@Test(expected=NullPointerException.class)
	public void removeMeeting_NullMeeting() {

		instance.removeMeeting(null);				
	}

	@Test(expected=IllegalStateException.class)
	public void removeMeeting_NotFound() {

		instance.removeMeeting(getMeetingInstance());				
	}

	@Test
	public void removeMeeting() {
	
		int initialId = instance.addMeeting(null, null, null);	
		ModelMeeting intialMeeting = instance.getMeeting(initialId);

		instance.removeMeeting(intialMeeting);	
		ModelMeeting removedMeeting = instance.getMeeting(initialId);
		assertNull(removedMeeting);
				
	}
	// *****************************************************************************************************************	
	// Utility methods
	// *****************************************************************************************************************	
	protected SerializableContactManagerModel getInstance() {
	
		return new SerializableContactManagerModel();
	}

	protected ModelContact getContactInstance() {

		return new DefaultModelContact(-1);
	}

	protected ModelMeeting getMeetingInstance() {

		return new DefaultModelMeeting(-1);
	}
}


