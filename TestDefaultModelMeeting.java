import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;
//
//	Don't forget org.junit.runner.JUnitCore !
//
public class TestDefaultModelMeeting {

	private DefaultModelMeeting instance;
	protected int defaultId;
	protected Random r = new Random(100);
	
	@Before
	public void init() {
		
		instance = getInstance(generateDefaultId());
	}
	
	@Test
	public void instanceIsSerializable() throws IOException {
		assertTrue(instance instanceof Serializable);
		ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
		oos.writeObject(instance);
	}

	@Test
	public void getId() {
		
		int resultId = instance.getId();
		assertEquals(defaultId, resultId);
	}

	@Test
	public void getDate_NotSet() {
		
		Calendar resultDate = instance.getDate();
		assertNull(resultDate);
	}

	@Test
	public void setDate() {
		
		Calendar expectedDate = Calendar.getInstance();
		instance.setDate(expectedDate);
		Calendar resultDate = instance.getDate();
		assertEquals(expectedDate, resultDate);
	}

	@Test
	public void getNotes_NotSet() {
		
		String resultNotes = instance.getNotes();
		assertNull(resultNotes);

	}

	@Test
	public void setNotes() {
		
		String expectedNotes = "expectedNotes";
		instance.addNotes(expectedNotes);
		String resultNotes = instance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}

	@Test
	public void getContacts_NotSet() {
		
		Set<Contact> resultSet = instance.getContacts();
		assertNull(resultSet);
	}

	@Test
	public void setContacts() {
		
		Set<Contact> expectedContacts = Collections.emptySet();
		instance.setContacts(expectedContacts);
		Set<Contact> resultContacts = instance.getContacts();
		assertEquals(expectedContacts, resultContacts);
	}

	@Test
	public void testClone() {
		
		DefaultModelMeeting instanceClone = instance.clone();
		assertNotNull(instanceClone);
		assertFalse(instanceClone == instance);
	}

	@Test
	public void testCloneAttributes() {
		
		instance.setDate(Calendar.getInstance());
		instance.addNotes("notes");
		// generate a contact
		Set<Contact> contacts = new HashSet<>();
		ModelContact instanceContact = new DefaultModelContact(-1);
		instanceContact.setName("contactName");
		instanceContact.addNotes("contactNotes");
		contacts.add(instanceContact);		
		instance.setContacts(contacts);

		// clone
		DefaultModelMeeting instanceClone = instance.clone();

		assertEquals(instance.getId(),instanceClone.getId());
		assertEquals(instance.getDate(),instanceClone.getDate());
		assertFalse(instance.getDate() == instanceClone.getDate());
		assertEquals(instance.getNotes(),instanceClone.getNotes());
		assertFalse(instance.getNotes() == instanceClone.getNotes());

		assertFalse(instance.getContacts() == instanceClone.getContacts());

		// check the cloned contact
		Contact clonedContact = instanceClone.getContacts().stream().findFirst().get();
		assertFalse(instanceContact == clonedContact);
		assertEquals(instanceContact.getId(), clonedContact.getId());
		assertEquals(instanceContact.getName(), clonedContact.getName());
		assertFalse(instanceContact.getName() == clonedContact.getName());
		assertEquals(instanceContact.getNotes(), clonedContact.getNotes());
		assertFalse(instanceContact.getNotes() == clonedContact.getNotes());

	}

	protected DefaultModelMeeting getInstance(int id) {

		return new DefaultModelMeeting(id);
	}

	protected int generateDefaultId() {

		defaultId = r.nextInt(Integer.MAX_VALUE);
		return defaultId;
	}
}


