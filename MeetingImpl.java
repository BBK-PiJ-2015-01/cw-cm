import java.util.*;

/**
 *
 * 
 */
public class MeetingImpl implements Meeting {

	protected final ModelMeeting model;		
	private final String NULL_MODEL_MSG = "Supplied model was null";	
	private final String INVALID_CONTACTS = "The Set of contacts must not be null or empty";

	public MeetingImpl(ModelMeeting model) {

		if (model == null) {
			throw new IllegalArgumentException(NULL_MODEL_MSG);
		}
		this.model = model;
	}

	@Override
	public int getId() {

		return model.getId();
	}

	@Override
	public Calendar getDate() {
	
		// Make a defensive copy
		return model.getDate() == null ? null : (Calendar) model.getDate().clone();
	}

	/**
	* Set the date of the meeting.
	*     
	* @param the date of the meeting. Null values are permitted
	*/
	public void setDate(Calendar date) {

		// Make a defensive copy
		model.setDate(date == null ? null : (Calendar) date.clone());
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
//		backingList = new ArrayList<>(contacts);
		model.setContacts(contacts);
	}
	/**
	* Get the contacts. This will return a reference to the Set used in the
	* setter with the same content. If this pointer has been used elsewhere
	* then it will also reflect the values at the time of the setter.
	*/
	@Override
	public Set<Contact> getContacts() {

//		if (model.getContacts() == null) {
//			return null;
//		}
//		//	Return a defensive copy
//		contacts.clear();
//		contacts.addAll(backingList);
		return model.getContacts();
	}  

	/**
	*	Allow equality on id
	*
	*/
	@Override
	public boolean equals(Object other) {

//		if (other == null || this.getClass() != other.getClass()) {
//		    return false;
//		}
//		MeetingImpl otherMeetingImpl = (MeetingImpl) other;
//		return this.getId() == otherMeetingImpl.getId();
		return model.equals(other);
	}

	@Override
	public int hashCode() {

//		int hash = 16381;
//		hash = 107 * hash + Objects.hashCode(model);
//		return hash;
		return model.hashCode();
	}
}
