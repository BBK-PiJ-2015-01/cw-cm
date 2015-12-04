import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

//
//	Don't forget org.junit.runner.JUnitCore !
//
public class TestSerializableContactManagerModel {

	protected SerializableContactManagerModel instance;
	
	@Before
	public void init() {
		
		instance = getInstance();
	}
	
	// *****************************************************************************************************************	
	// Add Contact and Get Contact tests
	// *****************************************************************************************************************	

	@Test(expected=NullPointerException.class)
	public void addContact_Null() {

		instance.addContact(null);		
	}

	@Test
	public void addContact_NotNull() {

		int resultId = instance.addContact(getContactInstance());
		assertTrue(resultId > 0);		
	}

	@Test
	public void getContact_DoesNotExist() {

		ModelContact contact = instance.getContact(1);
		assertNull(contact);				
	}

	@Test
	public void getContact_Exists() {

		int resultId = instance.addContact(getContactInstance());		
		ModelContact contact = instance.getContact(resultId);
		assertNotNull(contact);				
	}

	@Test
	public void getContact_CheckValues() {

		ModelContact expectedContact = getContactInstance();
		expectedContact.setName("name");
		expectedContact.addNotes("notes");
		int resultId = instance.addContact(expectedContact);		
		ModelContact resultContact = instance.getContact(resultId);
		assertNotSame(expectedContact,resultContact);
		assertEquals(expectedContact.getName(), resultContact.getName());	
		assertEquals(expectedContact.getNotes(), resultContact.getNotes());					
	}

	@Test
	public void getContact_CheckImmutableUpdatesOnExpected() {

		ModelContact expectedContact = getContactInstance();
		expectedContact.setName("name");
		expectedContact.addNotes("notes");
		int resultId = instance.addContact(expectedContact);		
		
		expectedContact.setName("Changed name");
		expectedContact.addNotes("Changed notes");

		ModelContact resultContact = instance.getContact(resultId);
		assertNotEquals(expectedContact.getName(), resultContact.getName());	
		assertNotEquals(expectedContact.getNotes(), resultContact.getNotes());								
	}

	@Test
	public void getContact_CheckImmutableUpdatesOnResult() {

		ModelContact expectedContact = getContactInstance();
		expectedContact.setName("name");
		expectedContact.addNotes("notes");
		int resultId = instance.addContact(expectedContact);		
		ModelContact resultContact = instance.getContact(resultId);

		resultContact.setName("Changed name");
		resultContact.addNotes("Changed notes");

		assertNotEquals(expectedContact.getName(), resultContact.getName());	
		assertNotEquals(expectedContact.getNotes(), resultContact.getNotes());								
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

		instance.addContact(getContactInstance());
		Set<ModelContact> resultContacts = instance.getContacts();

		Set<ModelContact> contacts = instance.getContacts();	
		int expectedContactsSize = 1;
		int resultContactsSize	= resultContacts.size();
		assertEquals(expectedContactsSize, resultContactsSize);			
	}

	@Test
	public void getContacts_ImmutabilityTest() {

		ModelContact expectedContact = getContactInstance();
		expectedContact.setName("name");
		expectedContact.addNotes("notes");
		int resultId = instance.addContact(expectedContact);
		Set<ModelContact> resultContacts = instance.getContacts();

		Set<ModelContact> contacts = instance.getContacts();	
		ModelContact setContact = (ModelContact) contacts.stream().findFirst().get();
		ModelContact getContact = instance.getContact(resultId);
		assertNotSame(setContact , getContact);		
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

		ModelContact intialContact = getContactInstance();		
		int initialId = instance.addContact(intialContact);
	
		intialContact = instance.getContact(initialId);
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

		ModelContact intialContact = getContactInstance();		
		int initialId = instance.addContact(intialContact);	
		intialContact = instance.getContact(initialId);

		instance.removeContact(intialContact);	
		ModelContact removedContact = instance.getContact(initialId);
		assertNull(removedContact);
				
	}
	
	// *****************************************************************************************************************	
	// Add Meeting and Get Meeting tests
	// *****************************************************************************************************************	

	@Test(expected=NullPointerException.class)
	public void addMeeting_Null() {

		instance.addMeeting(null);		
	}

	@Test
	public void addMeeting_NotNull() {

		int resultId = instance.addMeeting(getMeetingInstance());
		assertTrue(resultId > 0);		
	}

	@Test
	public void getMeeting_DoesNotExist() {

		ModelMeeting meeting = instance.getMeeting(1);
		assertNull(meeting);				
	}

	@Test
	public void getMeeting_Exists() {

		int resultId = instance.addMeeting(getMeetingInstance());		
		ModelMeeting meeting = instance.getMeeting(resultId);
		assertNotNull(meeting);				
	}

	@Test
	public void getMeeting_CheckValues() {

		ModelMeeting expectedMeeting = getMeetingInstance();
		expectedMeeting.setDate(Calendar.getInstance());
		expectedMeeting.addNotes("notes");

		Set<Contact> expectedContacts = new HashSet<>();
		int contactId = instance.addContact(getContactInstance());
		Contact meetingContact = instance.getContact(contactId);	
		expectedContacts.add(meetingContact);
		expectedMeeting.setContacts(expectedContacts);

		int resultId = instance.addMeeting(expectedMeeting);		
		ModelMeeting resultMeeting = instance.getMeeting(resultId);
		assertNotSame(expectedMeeting , resultMeeting);
		assertEquals(expectedMeeting.getDate(), resultMeeting.getDate());	
		assertEquals(expectedMeeting.getNotes(), resultMeeting.getNotes());	
		assertEquals(expectedMeeting.getContacts(), resultMeeting.getContacts());				
	}

	@Test
	public void getMeeting_CheckImmutableUpdatesOnExpected() {

		ModelMeeting expectedMeeting = getMeetingInstance();
		expectedMeeting.setDate(Calendar.getInstance());
		expectedMeeting.addNotes("notes");

		Set<Contact> expectedContacts = new HashSet<>();
		int contactId = instance.addContact(getContactInstance());
		Contact meetingContact = instance.getContact(contactId);	
		expectedContacts.add(meetingContact);
		expectedMeeting.setContacts(expectedContacts);

		int resultId = instance.addMeeting(expectedMeeting);	
		
		Calendar newDate = Calendar.getInstance();
		newDate.add(Calendar.HOUR, 12);
		expectedMeeting.setDate(newDate);
		expectedMeeting.addNotes("Changed notes");
		expectedContacts = expectedMeeting.getContacts();
		expectedContacts.add(getContactInstance());
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

		ModelMeeting expectedMeeting = getMeetingInstance();
		expectedMeeting.setDate(Calendar.getInstance());
		expectedMeeting.addNotes("notes");

		Set<Contact> expectedContacts = new HashSet<>();
		int contactId = instance.addContact(getContactInstance());
		Contact meetingContact = instance.getContact(contactId);	
		expectedContacts.add(meetingContact);
		expectedMeeting.setContacts(expectedContacts);

		int resultId = instance.addMeeting(expectedMeeting);
		ModelMeeting resultMeeting = instance.getMeeting(resultId);

		Calendar newDate = Calendar.getInstance();
		newDate.add(Calendar.HOUR, 1);
		resultMeeting.setDate(newDate);
		resultMeeting.addNotes("Changed notes");
		Set<Contact> resultContacts  = resultMeeting.getContacts();
		resultContacts.add(getContactInstance());
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

		instance.addMeeting(getMeetingInstance());
		Set<ModelMeeting> resultMeetings = instance.getMeetings();

		Set<ModelMeeting> meetings = instance.getMeetings();	
		int expectedMeetingsSize = 1;
		int resultMeetingsSize	= resultMeetings.size();
		assertEquals(expectedMeetingsSize, resultMeetingsSize);			
	}

	@Test
	public void getMeetings_ImmutabilityTest() {

		ModelMeeting expectedMeeting = getMeetingInstance();
		expectedMeeting.setDate(Calendar.getInstance());
		expectedMeeting.addNotes("notes");
		int resultId = instance.addMeeting(expectedMeeting);
		Set<ModelMeeting> resultMeetings = instance.getMeetings();

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

		ModelMeeting intialMeeting = getMeetingInstance();		
		int initialId = instance.addMeeting(intialMeeting);

		intialMeeting = instance.getMeeting(initialId);
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

		ModelMeeting intialMeeting = getMeetingInstance();		
		int initialId = instance.addMeeting(intialMeeting);	
		intialMeeting = instance.getMeeting(initialId);

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


