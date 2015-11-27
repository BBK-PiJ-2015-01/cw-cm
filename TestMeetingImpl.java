import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class TestMeetingImpl {

	protected MeetingImpl instance;
	private int defaultId;
	private Random r = new Random();
	
	@Before
	public void init() {
		
		defaultId = r.nextInt(Integer.MAX_VALUE);
		instance = new MeetingImpl(defaultId);
	}
	
	@Test
	public void getIdTest() {

		int expectedId = defaultId;
		int resultId = instance.getId();
		assertEquals(expectedId, resultId);
	}

	
	@Test
	public void getDateNotSetTest() {
	
		Calendar resultDate = instance.getDate();
		assertNull(resultDate);
	}

	@Test
	public void setDateWithNullTest() {
	
		Calendar expectedDate = null;
		instance.setDate(expectedDate);
		Calendar resultDate = instance.getDate();
		assertEquals(expectedDate, resultDate);
	}

	@Test
	public void setDateWithNonNullTest() {
	
		Calendar expectedDate = Calendar.getInstance();
		instance.setDate(expectedDate);
		Calendar resultDate = instance.getDate();
		assertEquals(expectedDate, resultDate);
	}

	@Test
	public void getDateImmutabilityTest() {
	
		//
		//	Create 2 equal dates
		//
		Calendar expectedDate = Calendar.getInstance();
		Calendar copyDate = (Calendar) expectedDate.clone();
		assertEquals(expectedDate, copyDate);
		//
		//	Set the date using the copy
		//
		instance.setDate(copyDate);
		Calendar resultDate = instance.getDate();
		assertEquals(expectedDate, resultDate);
		//
		//	Clear the returned date
		//
		resultDate.clear();
		//
		resultDate = instance.getDate();
		assertEquals(expectedDate, resultDate);
	}

	@Test
	public void setDateImmutabilityTest() {
	
		//
		//	Create 2 equal dates
		//
		Calendar expectedDate = Calendar.getInstance();
		Calendar copyDate = (Calendar) expectedDate.clone();
		assertEquals(expectedDate, copyDate);
		//
		//	Set the date using the copy
		//
		instance.setDate(copyDate);
		Calendar resultDate = instance.getDate();
		assertEquals(expectedDate, resultDate);
		//
		//	Clear the date used in the setter 
		//
		copyDate.clear();
		//
		resultDate = instance.getDate();
		assertEquals(expectedDate, resultDate);
	}

	@Test(expected=IllegalArgumentException.class)
	public void setContactsWithNullValueTest() {
		
		instance.setContacts(null);
	}


	@Test(expected=IllegalArgumentException.class)
	public void setContactsWithEmptyValueTest() {
		
		Set<Contact> contacts = new TreeSet<>();
		instance.setContacts(contacts);
	}

	@Test
	public void setContactsTest() {
		
		Set<Contact> expectedContacts = new TreeSet<>();
		expectedContacts.add(new ContactImpl());
		instance.setContacts(expectedContacts);
		Set<Contact> resultContacts = instance.getContacts();
		assertArrayEquals(expectedContacts.toArray(), resultContacts.toArray());	
	}

	@Test
	public void getContactsImmutabilityTest() {
	
		//
		//	Create 2 equal contacts Sets
		//
		Set<Contact> expectedContacts = new TreeSet<>();
		Set<Contact> copyContacts = new TreeSet<>();
		Contact singleContact = new ContactImpl();
		expectedContacts.add(singleContact);
		copyContacts.add(singleContact);
		assertArrayEquals(expectedContacts.toArray(), copyContacts.toArray());
		//
		//	Set the contacts using the copy
		//
		instance.setContacts(copyContacts);
		Set<Contact> resultContacts = instance.getContacts();
		assertArrayEquals(expectedContacts.toArray(), resultContacts.toArray());	
		//
		//	Clear the returned contacts
		//
		resultContacts.clear();
		//
		resultContacts = instance.getContacts();
		assertArrayEquals(expectedContacts.toArray(), resultContacts.toArray());	
	}
	
	@Test
	public void setContactsImmutabilityTest() {
	
		//
		//	Create 2 equal contacts Sets
		//
		Set<Contact> expectedContacts = new TreeSet<>();
		Set<Contact> copyContacts = new TreeSet<>();
		Contact singleContact = new ContactImpl();
		expectedContacts.add(singleContact);
		copyContacts.add(singleContact);
		assertArrayEquals(expectedContacts.toArray(), copyContacts.toArray());
		//
		//	Set the contacts using the copy
		//
		instance.setContacts(copyContacts);
		Set<Contact> resultContacts = instance.getContacts();
		assertArrayEquals(expectedContacts.toArray(), resultContacts.toArray());
		//
		//	Clear the contacts used in the setter 
		//
		copyContacts.clear();
		//
		resultContacts = instance.getContacts();
		assertArrayEquals(expectedContacts.toArray(), resultContacts.toArray());
	}





	private class ContactImpl implements Contact, Comparable<Contact> {

		@Override
		public int getId() {
		throw new UnsupportedOperationException("Not implemented.");
		}

		@Override
		public String getName() {
		throw new UnsupportedOperationException("Not implemented.");
		}

		@Override
		public String getNotes() {
		throw new UnsupportedOperationException("Not implemented.");
		}

		@Override
		public void addNotes(String note) {
		throw new UnsupportedOperationException("Not implemented.");
		}

		@Override
		public int compareTo(Contact o) {
			
			return o == null ? -1 : hashCode() - o.hashCode();
		}
	}
}


