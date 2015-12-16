import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestContactImpl {

	protected ContactImpl instance;
	protected int defaultId;
	protected Random r = new Random();
	
	@Before
	public void init() {

	}

	@Test(expected=NullPointerException.class)
	public void contructor_3_nullName(){

		new ContactImpl(1, null, "");
	}

	@Test(expected=NullPointerException.class)
	public void contructor_3_nullNotes(){

		new ContactImpl(1, "", null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_3_zeroId() {

		new ContactImpl(0, "", "");
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_3_negativeId() {

		new ContactImpl(-1, "", "");
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_2_zeroId() {

		new ContactImpl(0, "");
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_2_negativeId() {

		new ContactImpl(-1, "");
	}

	@Test(expected=NullPointerException.class)
	public void contructor_2_nullName(){

		new ContactImpl(1, null);
	}

	@Test
	public void getIdTest() {

		int expectedId = generateDefaultId();
		instance = new ContactImpl(expectedId, "");
		int resultId = instance.getId();
		assertEquals(expectedId, resultId);
		//
		expectedId = generateDefaultId();
		instance = new ContactImpl(expectedId, "", "");
		resultId = instance.getId();
		assertEquals(expectedId, resultId);
	}

	@Test
	public void getNameTest() {

		String expectedName = "Expected name";
		instance = new ContactImpl(generateDefaultId(), expectedName);
		String resultName = instance.getName();
		assertEquals(expectedName, resultName);
		//
		instance = new ContactImpl(generateDefaultId(), expectedName, "");
		resultName = instance.getName();
		assertEquals(expectedName, resultName);
	}


	@Test
	public void getNotesTest() {

		instance = new ContactImpl(generateDefaultId(), "");
		String expectedNotes = "";
		String resultNotes = instance.getNotes();
		assertEquals(expectedNotes, resultNotes);
		//
		expectedNotes = "Expected notes";
		instance = new ContactImpl(generateDefaultId(), "", expectedNotes);
		resultNotes = instance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}

	@Test
	public void addNotesTest() {

		instance = new ContactImpl(generateDefaultId(), "");
		//
		String expectedNotes = "Expected notes";
		instance.addNotes(expectedNotes);
		String resultNotes = instance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}



	@Test
	public void testContactSetContainsContact() {

		instance = new ContactImpl(generateDefaultId(), "");
		Set<Contact> contacts = new HashSet<>();
		contacts.add(instance);
		Contact instanceCopy = new ContactImpl(defaultId, "");
		assertTrue(contacts.contains(instanceCopy));
	}

	@Test
	public void testContactSetDoesNotContainsContact() {

		instance = new ContactImpl(generateDefaultId(), "");
		Set<Contact> contacts = new HashSet<>();
		contacts.add(instance);
		Contact notInstance = new ContactImpl(defaultId-1, "");
		assertFalse(contacts.contains(notInstance));
	}

	protected int generateDefaultId() {

		defaultId = r.nextInt(Integer.MAX_VALUE);
		return defaultId;
	}
}
