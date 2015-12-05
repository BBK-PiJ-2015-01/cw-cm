
import java.util.*;

/**
 * A model representing the Contacts and Meetings for the ContactManager
 * application. 
 *
 * @author sbaird02
 */
public interface ContactManagerModel {

    /**
     * Get the set of all contacts within the model
     *
     * @return all contacts in the model.  An empty set will be returned 
     * if not contacts are present 
     */
    Set<? extends Contact> getContacts();

    /**
     * Get the set of all meetings within the model
     *
     * @return all meetings in the model
     */
    Set<? extends Meeting> getMeetings();

    /**
     * Add a contact to the model. A unique id will be created and 
     * returned to enable the Contact to be retrieved
     *
     * @param name optional name of Contact
     * @param notes optional notes to be added to the  Contact
     * @return id of newly created Contact
     */
    int addContact(String name, String notes);

    /**
     * Get an instance of a contact by it's unique identifier
     *
     * @param id unique Contact identifier
     * @return Contact or null if not found
     */
    ModelContact getContact(int id);

    /**
     * Remove on instance of a Contact from the model
     *
     * @param contact Contact to remove
     * @throws java.lang.IllegalStateException - if the contact does not exists in the model
     */
    void removeContact(ModelContact contact);

    /**
     * Replace the instance of the Contact in the model
     *
     * @param contact amended Contact to store in the model
     * @throws java.lang.NullPointerException - if the contact is null
     * @throws java.lang.IllegalStateException - if the contact does not exists in the model
     */
    void updateContact(ModelContact contact);

    /**
     * Add a meeting to the model
     *
     * @param meeting to add
     * @return id of newly created Meeting
     * @throws java.lang.NullPointerException - if meeting is null
     */
    int addMeeting(ModelMeeting meeting);

    /**
     * Get an instance of a Meeting by it's unique identifier
     *
     * @param id unique Meeting identifier
     * @return Meeting or null if not found
     */
    ModelMeeting getMeeting(int id);

    /**
     * Replace the instance of the Meeting in the model
     *
     * @param meeting amended Meeting to store in the model
     * @throws java.lang.NullPointerException - if the meeting is null
     * @throws java.lang.IllegalStateException - if the meeting does not exists in the model
     */
    void removeMeeting(ModelMeeting meeting);

    /**
     * Replace the instance of the Meeting in the model
     *
     * @param meeting amended Meeting to store in the model
     * @throws java.lang.NullPointerException - if the meeting is null
     * @throws java.lang.IllegalStateException - if the meeting does not exists in the model
     */
    void updateMeeting(ModelMeeting meeting);
}

