import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestContactImpl {

	protected ContactImpl instance;
	protected int defaultId;
	protected Random r = new Random();
	
	@Before
	public void init() {
		
		instance = getInstance(generateDefaultId());
	}

	@Test
	public void getIdTest() {

		int expectedId = defaultId;
		int resultId = instance.getId();
		assertEquals(expectedId, resultId);
	}

	@Test
	public void getNameNotSetTest() {

		String expectedName = null;
		String resultName = instance.getName();
		assertEquals(expectedName, resultName);
	}

	@Test
	public void setNameTest() {

		String expectedName = null;
		instance.setName(expectedName);
		String resultName = instance.getName();
		assertEquals(expectedName, resultName);
		//
		expectedName = "Valid string";
		instance.setName(expectedName);
		resultName = instance.getName();
		assertEquals(expectedName, resultName);
	}

	@Test
	public void getNotesNotSetTest() {

		String expectedNotes = new String();
		String resultNotes = instance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}

	@Test
	public void addNotesTest() {

		String expectedNotes = new String();
		instance.addNotes(null);
		String resultNotes = instance.getNotes();
		assertEquals(expectedNotes, resultNotes);
		//
		expectedNotes = "Valid string";
		instance.addNotes(expectedNotes);
		resultNotes = instance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}

	@Test
	public void testContactSetContainsContact() {

		Set<Contact> contacts = new HashSet<>();
		contacts.add(instance);
		Contact instanceCopy = getInstance(instance.getId());
		assertTrue(contacts.contains(instanceCopy));
	}

	@Test
	public void testContactSetDoesNotContainsContact() {

		Set<Contact> contacts = new HashSet<>();
		contacts.add(instance);
		assertFalse(contacts.contains(getInstance(instance.getId()-1)));
	}

	protected ContactImpl getInstance(int id) {

		ModelContact model = new DefaultModelContact(id);
		return new ContactImpl(model);
	}

	protected int generateDefaultId() {

		defaultId = r.nextInt(Integer.MAX_VALUE);
		return defaultId;
	}



}
