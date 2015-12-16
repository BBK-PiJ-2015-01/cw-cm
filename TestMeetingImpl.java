import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class TestMeetingImpl {

	protected MeetingImpl instance;
	protected int defaultId;
	protected Contact defaultContact;
	protected Random r = new Random();
	
	@Before
	public void init() {
		
		instance = getInstance(generateDefaultId());
	}

	@Test(expected=NullPointerException.class)
	public void constructor_nullDate() {

		new DefaultMeetingImpl(1, null, Collections.emptySet());
	}

	@Test(expected=NullPointerException.class)
	public void constructor_nullContacts() {

		new DefaultMeetingImpl(1, Calendar.getInstance(), null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_zeroId() {

		new DefaultMeetingImpl(0, Calendar.getInstance(), Collections.emptySet());
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_negativeId() {

		new DefaultMeetingImpl(-1, Calendar.getInstance(), Collections.emptySet());
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_emptyContacts() {

		new DefaultMeetingImpl(1, Calendar.getInstance(), Collections.emptySet());
	}
	
	@Test
	public void getIdTest() {

		int expectedId = defaultId;
		int resultId = instance.getId();
		assertEquals(expectedId, resultId);
	}

	@Test
	public void detDateTest() {
	
		Calendar expectedDate = Calendar.getInstance();
		instance = new DefaultMeetingImpl(generateDefaultId(), expectedDate, getContactSet(defaultId));
		Calendar resultDate = instance.getDate();
		assertEquals(expectedDate, resultDate);
	}

	@Test
	public void dateImmutabilityTest() {
	
		Calendar expectedDate = Calendar.getInstance();
		instance = new DefaultMeetingImpl(generateDefaultId(), expectedDate, getContactSet(defaultId));
		long expectedTimeInMillis = expectedDate.getTimeInMillis();
		//
		//	Clear the date
		//
		expectedDate.clear();
		//
		Calendar resultDate = instance.getDate();
		long resultTimeInMillis = resultDate.getTimeInMillis();
		assertEquals(expectedTimeInMillis, resultTimeInMillis);
	}

	@Test
	public void getDateImmutabilityTest() {
	
		Calendar expectedDate = Calendar.getInstance();
		instance = new DefaultMeetingImpl(generateDefaultId(), expectedDate, getContactSet(defaultId));
		long expectedTimeInMillis = expectedDate.getTimeInMillis();
		//
		//	Get and clear the date
		//
		Calendar resultDate = instance.getDate();
		resultDate.clear();
		//
		long resultTimeInMillis = instance.getDate().getTimeInMillis();
		assertEquals(expectedTimeInMillis, resultTimeInMillis);
	}

	@Test
	public void getContactsTest() {
	
		Contact contact = getContactInstance(generateDefaultId());
		Set<Contact> expectedContacts = new HashSet<>();
		expectedContacts.add(contact);
		instance = new DefaultMeetingImpl(generateDefaultId(), Calendar.getInstance(), expectedContacts);		
		//
		Set<Contact> resultContacts = instance.getContacts();
		assertEquals(expectedContacts, resultContacts);
	}

	@Test
	public void getContactsImmutabilityAfterConstructorTest() {
	
		Contact contact = getContactInstance(generateDefaultId());
		Set<Contact> expectedContacts = new HashSet<>();
		expectedContacts.add(contact);
		instance = new DefaultMeetingImpl(generateDefaultId(), Calendar.getInstance(), expectedContacts);	
		//
		//	Clear the contacts set
		//
		expectedContacts.clear();
		//
		Set<Contact> resultContacts = instance.getContacts();
		assertFalse(resultContacts.isEmpty());	
	}

	@Test
	public void getContactsImmutabilityAfterGetterTest() {
	
		//
		Contact contact = getContactInstance(generateDefaultId());
		Set<Contact> expectedContacts = new HashSet<>();
		expectedContacts.add(contact);
		instance = new DefaultMeetingImpl(generateDefaultId(), Calendar.getInstance(), expectedContacts);
		//
		//	Get and clear the contacts set
		//
		Set<Contact> resultContacts = instance.getContacts();	
		resultContacts.clear();
		//
		//	Get the contacts again and ensure they have not been changed
		//
		resultContacts = instance.getContacts();
		assertArrayEquals(expectedContacts.toArray(), resultContacts.toArray());	
	}
	
	@Test
	public void testMeetingListContainsMeeting() {

		List<Meeting> meetings = new ArrayList<>();
		meetings.add(instance);
		Meeting instanceCopy = getInstance(instance.getId());
		assertTrue(meetings.contains(instanceCopy));
	}

	@Test
	public void testMeetingListDoesNotContainsMeeting() {

		List<Meeting> meetings = new ArrayList<>();
		meetings.add(instance);
		assertFalse(meetings.contains(getInstance(instance.getId()-1)));
	}

	protected MeetingImpl getInstance(int id) {

		return new DefaultMeetingImpl(id, Calendar.getInstance(), getContactSet(id));
	}

	protected int generateDefaultId() {

		defaultId = r.nextInt(Integer.MAX_VALUE);
		return defaultId;
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

	private class DefaultMeetingImpl extends MeetingImpl {

		public DefaultMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		
			super(id, date, contacts);				
		}
	}
}


