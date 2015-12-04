import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestDefaultModelContact {

	protected DefaultModelContact instance;
	protected int defaultId;
	protected Random r = new Random();
	
	@Before
	public void init() {
		
		instance = new DefaultModelContact(generateDefaultId());
	}

	@Test
	public void getIdTest() {

		int resultId = instance.getId();
		assertEquals(defaultId, resultId);
	}

	@Test
	public void getName_NotSet() {

		String resultName = instance.getName();
		assertNull(resultName);
	}

	@Test
	public void setName() {

		String expectedName = "expectedName";
		instance.setName(expectedName);
		String resultName = instance.getName();
		assertEquals(expectedName, resultName);
	}

	@Test
	public void getNotes_NotSet() {

		String resultNotes = instance.getNotes();
		assertNull(resultNotes);
	}

	@Test
	public void addNotes() {

		String expectedNotes = "expectedNotes";
		instance.addNotes(expectedNotes);
		String resultNotes = instance.getNotes();
		assertEquals(expectedNotes, expectedNotes);
	}

	@Test
	public void testClone() {

		DefaultModelContact instanceClone = instance.clone();
		assertNotNull(instanceClone);
		assertFalse(instance == instanceClone);
	}

	@Test
	public void testCloneAttributes() {

		DefaultModelContact instanceClone = instance.clone();
		assertNotNull(instanceClone);
		assertEquals(instance.getId(), instanceClone.getId());
		assertEquals(instance.getName(), instanceClone.getName());
		assertFalse(instance.getName() == instanceClone.getName());
		assertEquals(instance.getNotes(), instanceClone.getNotes());
		assertFalse(instance.getNotes() == instanceClone.getNotes());
	}

	protected int generateDefaultId() {

		defaultId = r.nextInt(Integer.MAX_VALUE);
		return defaultId;
	}
}

