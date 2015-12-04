import org.junit.*;
import static org.junit.Assert.*;

//
//	Don't forget org.junit.runner.JUnitCore !
//
public class TestSerializableContactManagerModel {

	protected SerializableContactManagerModel instance;
	
	@Before
	public void init() {
		
		instance = getInstance();
	}
	
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

	protected SerializableContactManagerModel getInstance() {
	
		return new SerializableContactManagerModel();
	}

	protected ModelContact getContactInstance() {

		return new DefaultModelContact(-1);
	}
}


