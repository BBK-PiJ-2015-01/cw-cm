import java.util.*;
import java.util.stream.*;
/**
 *
 * 
 */
public abstract class MeetingImpl implements Meeting {
	
	private final int id;	
	private final Calendar date;
	private List<Contact> contactBackingList;
	private Set<Contact> contacts;	

	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {

		if (date == null || contacts == null) {
			throw new NullPointerException();
		}
		if (id < 1) {
			throw new IllegalArgumentException();
		}
		if (contacts.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.id = id;
		// clone the date
		this.date = (Calendar) date.clone();
		//
		safelyStoreContacts(contacts);
	}

	private void safelyStoreContacts(Set<Contact> contacts) {

		// Copy the contacts into a backing list
		Stream<Contact> contactsStream = contacts.stream().map((c) -> new ContactImpl(c.getId(), c.getName(), c.getNotes()));
		contactBackingList = contactsStream.collect(Collectors.toList());
		this.contacts = contacts;	
	}

	@Override
	public int getId() {

		return id;
	}

	@Override
	public Calendar getDate() {
	
		// Make a defensive copy
		return (Calendar) date.clone();
	}

	/**
	* Get the contacts. This will return a reference to the Set used in the
	* constructor with the same content. If this pointer has been used elsewhere
	* then it will now also reflect the values at the time of the constructor.
	*/
	@Override
	public Set<Contact> getContacts() {

		contacts.clear();
		contacts.addAll(contactBackingList);
		return contacts;
	}  

	/**
	*	Allow equality on id
	*
	*/
	@Override
	public boolean equals(Object other) {

		if (other == null || !(other instanceof Meeting)) {
		    return false;
		}
		Meeting otherMeeting = (Meeting) other;
		return this.getId() == otherMeeting.getId();
	}

	@Override
	public int hashCode() {

		int hash = 16383;
		hash = 137 * hash + id;
		return hash;
	}
}
