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
}



