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
	public Set<? extends ModelMeeting> getMeetings() {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public int addContact(ModelContact contact) {

		if (contact == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		lazyInstantiateContacts();

		ModelContact newContact = getNewContactInstance();
		if (contacts.contains(newContact)) {
			throw new IllegalStateException("Contact Identity error encountered");
		}
		populateModelContactFromModelContact(newContact, contact);
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
	public int addMeeting(ModelMeeting meeting) {
		if (meeting == null) {
			throw new NullPointerException(NULL_PARAM_MSG);
		}
		return 1;
	}

	@Override
	public ModelMeeting getMeeting(int id) {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public void removeMeeting(ModelMeeting meeting) {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	@Override
	public void updateMeeting(ModelMeeting meeting) {
	throw new UnsupportedOperationException("Unsupported operation."); 
	}

	private ModelContact getNewContactInstance() {
		
		return new DefaultModelContact(++nextContactId);
	}

	private void populateModelContactFromModelContact(ModelContact target, ModelContact source) {
	
		ModelContact clonedSource = source.clone();
		target.setName(clonedSource.getName());
		target.addNotes(clonedSource.getNotes());		
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

