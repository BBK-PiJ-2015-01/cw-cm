import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
public class TestPastMeetingImpl extends TestMeetingImpl {

	private PastMeetingImpl thisInstance;

	@Before
	public void init() {
		
		instance = getInstance(generateDefaultId());
		thisInstance = (PastMeetingImpl) instance;
	}

	@Test
	public void getNotesNotSetTest() {

		String expectedNotes = new String();
		String resultNotes = thisInstance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}

	@Test
	public void setNotesNullValueTest() {

		String expectedNotes = new String();
		thisInstance.setNotes(null);
		String resultNotes = thisInstance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}

	@Test
	public void setNotesEmptyValueTest() {

		String expectedNotes = new String();
		thisInstance.setNotes(expectedNotes);
		String resultNotes = thisInstance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}

	@Test
	public void setNotesValidValueTest() {

		String expectedNotes = "PastMeetingImpl";
		thisInstance.setNotes(expectedNotes);
		String resultNotes = thisInstance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}

	protected MeetingImpl getInstance(int id) {
		return new PastMeetingImpl(id);
	}

}
