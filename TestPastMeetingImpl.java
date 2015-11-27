import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
public class TestPastMeetingImpl extends TestMeetingImpl {

	private PastMeetingImpl thisInstance;

	@Before
	public void init() {
		
		instance = new PastMeetingImpl(generateDefaultId());
		thisInstance = (PastMeetingImpl) instance;
	}

	@Test
	public void getNotesNotSetTest() {

		String expectedNotes = new String();
		String resultNotes = thisInstance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}
}
