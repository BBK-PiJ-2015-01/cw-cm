import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

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
		instance.setNotes(expectedNotes);
		String resultNotes = instance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}

	@Test
	public void getContacts_NotSet() {
		
		Set<ModelContact> resultSet = instance.getContacts();
		assertNull(resultSet);
	}

	@Test
	public void setContacts() {
		
		Set<ModelContact> expectedContacts = Collections.emptySet();
		instance.setContacts(expectedContacts);
		Set<ModelContact> resultContacts = instance.getContacts();
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
		instance.setNotes("notes");

		Set<ModelContact> contacts = new HashSet<>();
		ModelContact instanceContact = new DefaultModelContact(-1);
		instance.setContacts(contacts);
		// clone
		DefaultModelMeeting instanceClone = instance.clone();

		assertEquals(instance.getId(),instanceClone.getId());
		assertEquals(instance.getDate(),instanceClone.getDate());
		assertFalse(instance.getDate() == instanceClone.getDate());
		assertEquals(instance.getNotes(),instanceClone.getNotes());
		assertFalse(instance.getNotes() == instanceClone.getNotes());
		assertArrayEquals(instance.getContacts().toArray(), instanceClone.getContacts().toArray());
		assertFalse(instance.getContacts() == instanceClone.getContacts());

	}

	protected DefaultModelMeeting getInstance(int id) {

		return new DefaultModelMeeting(id);
	}

	protected int generateDefaultId() {

		defaultId = r.nextInt(Integer.MAX_VALUE);
		return defaultId;
	}
}


