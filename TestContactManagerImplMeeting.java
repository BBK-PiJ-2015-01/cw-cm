import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestContactManagerImplMeeting {

	protected ContactManagerImpl instance;
	
	@Before
	public void init() {
		
		instance = new ContactManagerImpl();
	}

	//	*********************************************************************************************
	//	Tests for Meetings
	//	*********************************************************************************************

	@Test(expected=NullPointerException.class)
	public void addFutureMeetingNullDateTest() {

		instance.addFutureMeeting( Collections.emptySet(), null);		
	}	

	@Test(expected=NullPointerException.class)
	public void addFutureMeetingNullContactSetTest() {

		instance.addFutureMeeting( null, Calendar.getInstance());		
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeetingEmptyContactSetTest() {

		instance.addFutureMeeting(Collections.emptySet(), Calendar.getInstance());		
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeetingUnknownContactSetTest() {
		
		Set<Contact> contacts = new HashSet<>();
		contacts.add(new ContactImpl(-1));
		instance.addFutureMeeting(contacts, Calendar.getInstance());		
	}
	//
	//	Utility methods
	//
	private boolean assertContactSetContainsContactName(String contactName, Set<Contact> contacts) {

		for (Contact contact: contacts) {
			if (contact.getName().equals(contactName)) {
				return true;
			}
		}
		return false;
	}
	private Contact getContactSetItem(int index, Set<Contact> contacts) {

		int count = 0;
		for (Contact contact: contacts) {
			if (index == count) {
				return contact;
			}
			count++;
		}
		return null;
	}
}



