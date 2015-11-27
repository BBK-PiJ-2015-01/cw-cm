import java.util.*;

/**
 *
 * 
 */
public class MeetingImpl implements Meeting {

	private final int id;
	private Calendar date;
	private Set<Contact> contacts;
	private List<Contact> backingList = new ArrayList<>();
	
	private final String INVALID_CONTACTS = "The Set of contacts must not be null or empty";

	public MeetingImpl(int id) {

		this.id = id;
	}

	@Override
	public int getId() {

		return id;
	}

	@Override
	public Calendar getDate() {
	
		// Make a defensive copy
		return date == null ? null : (Calendar) date.clone();
	}

	/**
	* Set the date of the meeting.
	*     
	* @param the date of the meeting. Null values are permitted
	*/
	public void setDate(Calendar date) {

		// Make a defensive copy
		this.date = date == null ? null : (Calendar) date.clone();
	}

	/**
	* Set the contacts. The Set must not be null or empty
	*     
	* @param the Set of contacts
	*/
	public void setContacts(Set<Contact> contacts) {

		if (contacts == null || contacts.isEmpty()) {
			throw new IllegalArgumentException(INVALID_CONTACTS); 
		}
		//
		//	Save the Set contacts in a List. This ensures that the 
		//	contact Set will be the same class as supplied but
		//	will be immutable from the perpective of this class
		//
		backingList = new ArrayList<>(contacts);
		this.contacts = contacts;
	}
	/**
	* Get the contacts. This will return a reference to the Set used in the
	* setter with the same content. If this pointer has been used elsewhere
	* then it will also reflect the values at the time of the setter.
	*/
	@Override
	public Set<Contact> getContacts() {

		if (contacts == null) {
			return null;
		}
		//	Return a defensive copy
		contacts.clear();
		contacts.addAll(backingList);
		return contacts;
	}  

	/**
	*	Allow equality on id
	*
	*/
	@Override
	public boolean equals(Object other) {

		if (other == null || this.getClass() != other.getClass()) {
		    return false;
		}
		MeetingImpl otherMeetingImpl = (MeetingImpl) other;
		return this.id == otherMeetingImpl.id;
	}

	@Override
	public int hashCode() {

		int hash = 16381;
		hash = 97 * hash + id;
		hash = 97 * hash + Objects.hashCode(date);
		hash = 97 * hash + Objects.hashCode(backingList);
		return hash;
	}
}
