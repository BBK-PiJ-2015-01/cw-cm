import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestContactManagerImpl {

	protected ContactManagerImpl instance;
	
	@Before
	public void init() {
		
		instance = new ContactManagerImpl();
	}

	@Test(expected=NullPointerException.class)
	public void getContactsByNameDataNullNameTest() {

		String nullName = null;
		Set<Contact> resultSet = instance.getContacts(nullName);		
	}	

	@Test
	public void getContactsByNameNotFoundTest() {

		String emptyName = new String();
		Set<Contact> expectedSet = Collections.emptySet();
		Set<Contact> resultSet = instance.getContacts(emptyName);
		assertArrayEquals(expectedSet.toArray(), resultSet.toArray());	
	}


	@Test(expected=NullPointerException.class)
	public void addNewContactNameNullTest() {

		String nullString = null;
		instance.addNewContact(nullString, nullString);
	}

	@Test(expected=NullPointerException.class)
	public void addNewContactNotesNullTest() {

		String nullString = null;
		String notNullString = "notNullString";
		instance.addNewContact(notNullString, nullString);
	}

	@Test
	public void addNewContactValidParamsTest() {

		int expectedSetSize = 1;
		String name = "name";
		String notes = "notes";
		instance.addNewContact(name, notes);
		Set<Contact> resultSet = instance.getContacts(name);
		assertNotNull(resultSet);
		int resultSetSize = resultSet.size();
		assertEquals(expectedSetSize, resultSetSize);
		// Check the returned contact
		Contact resultContact = getContactSetItem(0, resultSet);
		assertNotNull(resultContact);
		assertEquals(name, resultContact.getName() );
		assertEquals(notes, resultContact.getNotes() );
	}

	@Test
	public void addNewContactDuplicateAddTest() {

		int expectedSetSize = 2;
		String name = "name";
		String notes = "notes";
		instance.addNewContact(name, notes);
		instance.addNewContact(name, notes);
		Set<Contact> resultSet = instance.getContacts(name);
		assertNotNull(resultSet);
		int resultSetSize = resultSet.size();
		assertEquals(expectedSetSize, resultSetSize);

	}
	//
	//	Utility methods
	//
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



