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

		int resultId = instance.addContact(getContactInstance(-1));
		assertTrue(resultId > 0);		
	}

	@Test
	public void getContact_DoesNotExist() {

		Contact contact = instance.getContact(1);
		assertNull(contact);				
	}

	@Test
	public void getContact_Exists() {

		int resultId = instance.addContact(getContactInstance(-1));		
		Contact contact = instance.getContact(resultId);
		assertNotNull(contact);				
	}

	protected SerializableContactManagerModel getInstance() {
	
		return new SerializableContactManagerModel();
	}

	protected Contact getContactInstance(int id) {

		ModelContact model = new DefaultModelContact(id);
		return new ContactImpl(model);
	}
}


