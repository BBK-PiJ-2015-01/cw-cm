import java.util.*;
/**
 * 
 *
 * @author sbaird02
 */
public class SerializableContactManagerModel implements ContactManagerModel {

	private static final String NULL_PARAM_MSG = "The supplied argument was null";	
	private static final String PARAM_NOT_FOUND_MSG = "The supplied argument does not exist in the model";

	private int nextContactId;
	private int nextMeetingId;
	private Set<ModelContact> contacts;
	private Set<ModelMeeting> meetings;

	@Override
	public Set<ModelContact> getContacts() {

		lazyInstantiateContacts();
		Set<ModelContact> returnContactsSet =  new HashSet<>();
		contacts.stream().forEach((c) -> returnContactsSet.add(c.clone()));
		return returnContactsSet;
	}

	@Override
	public Set<ModelMeeting> getMeetings() {

		lazyInstantiateMeetings();
		Set<ModelMeeting> returnMeetingsSet =  new HashSet<>();
		meetings.stream().forEach((m) -> returnMeetingsSet.add(m.clone()));
		return returnMeetingsSet;
	}

	@Override
	public int addContact(String name, String notes) {


		ModelContact newContact = getNewContactInstance();
		lazyInstantiateContacts();
		if (contacts.contains(newContact)) {
			throw new IllegalStateException("Contact Identity error encountered");
		}
		newContact.setName(name);
		newContact.addNotes(notes);
		contacts.add(newContact);
		return newContact.getId();
	}

	@Override
	public ModelContact getContact(int id) {

		lazyInstantiateContacts();
		Optional<ModelContact> result =  contacts.stream().filter((c) -> c.getId() == id).findFirst();
		return result.isPresent() ? result.get().clone() : null;
	}

	@Override
	public void removeContact(ModelContact contact) {

		if (contact == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		lazyInstantiateContacts();
		if (!contacts.contains(contact)) {
			throw new IllegalStateException(PARAM_NOT_FOUND_MSG);
		}
		contacts.remove(contact);
	}

	@Override
	public void updateContact(ModelContact contact) {

		if (contact == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		lazyInstantiateContacts();
		if (!contacts.contains(contact)) {
			throw new IllegalStateException(PARAM_NOT_FOUND_MSG);
		}
		contacts.remove(contact);
		contacts.add(contact.clone());
	}

	@Override
	public int addMeeting(Calendar date, Set<Contact> contacts, String notes) {

		ModelMeeting newMeeting = getNewMeetingInstance();
		lazyInstantiateMeetings();
		if (meetings.contains(newMeeting)) {
			throw new IllegalStateException("Meeting Identity error encountered");
		}
		newMeeting.setDate(date);
		newMeeting.setContacts(contacts);
		newMeeting.addNotes(notes);
		meetings.add(newMeeting);
		return newMeeting.getId();
	}

	@Override
	public ModelMeeting getMeeting(int id) {
		lazyInstantiateMeetings();
		Optional<ModelMeeting> result =  meetings.stream().filter((m) -> m.getId() == id).findFirst();
		return result.isPresent() ? result.get().clone() : null;
	}

	@Override
	public void removeMeeting(ModelMeeting meeting) {
		if (meeting == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		lazyInstantiateMeetings();
		if (!meetings.contains(meeting)) {
			throw new IllegalStateException(PARAM_NOT_FOUND_MSG);
		}
		meetings.remove(meeting);
	}

	@Override
	public void updateMeeting(ModelMeeting meeting) {
		if (meeting == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		lazyInstantiateMeetings();	
		if (!meetings.contains(meeting)) {
			throw new IllegalStateException(PARAM_NOT_FOUND_MSG);
		}
		meetings.remove(meeting);
		meetings.add(meeting.clone());
	}

	//	*****************************************************************************************************************************		
	//	Utility methods
	//	*****************************************************************************************************************************		
	private ModelContact getNewContactInstance() {
		
		return new DefaultModelContact(++nextContactId);
	}

	private ModelMeeting getNewMeetingInstance() {
		
		return new DefaultModelMeeting(++nextMeetingId);
	}
	private void populateModelContactFromModelContact(ModelContact target, ModelContact source) {
	
		ModelContact clonedSource = source.clone();
		target.setName(clonedSource.getName());
		target.addNotes(clonedSource.getNotes());		
	}

	private void populateModelMeetingFromModelMeeting(ModelMeeting target, ModelMeeting source) {
	
		ModelMeeting clonedSource = source.clone();
		target.setDate(clonedSource.getDate());
		target.addNotes(clonedSource.getNotes());
		target.setContacts(clonedSource.getContacts());		
	}

	private void lazyInstantiateContacts()  {

		if (contacts == null) {
			contacts = new HashSet<>();
		}
	}

	private void lazyInstantiateMeetings()  {

		if (meetings == null) {
			meetings = new HashSet<>();
		}
	}
}

