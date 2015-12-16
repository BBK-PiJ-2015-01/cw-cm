import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
public class TestPastMeetingImpl {

	private PastMeetingImpl instance;


	@Test(expected=NullPointerException.class)
	public void constructor_nullNotes() {

		new PastMeetingImpl(1, Calendar.getInstance(), getContactSet(1), null);
	}

	@Test
	public void getNotesNoValueTest() {

		String expectedNotes = new String();
		instance = new PastMeetingImpl(1, Calendar.getInstance(), getContactSet(1), expectedNotes);
		String resultNotes = instance.getNotes();
		assertEquals(expectedNotes, resultNotes);
	}

	protected ContactImpl getContactInstance(int id) {

		return new ContactImpl(id, "");
	}

	protected Set<Contact> getContactSet(int id) {

		Contact contact = getContactInstance(id);
		Set<Contact> contacts = new HashSet<>();
		contacts.add(contact);
		return contacts;
	}
}


