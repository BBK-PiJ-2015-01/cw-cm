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
		assertFalse(expectedContact == resultContact);
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
		assertFalse(setContact == getContact);		
	}

	protected SerializableContactManagerModel getInstance() {
	
		return new SerializableContactManagerModel();
	}

	protected ModelContact getContactInstance() {

		return new DefaultModelContact(-1);
	}
}


