
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
     * @return all contacts in the model
     */
    Set<? extends Contact> getContacts();

    /**
     * Get the set of all meetings within the model
     *
     * @return all meetings in the model
     */
    Set<? extends Meeting> getMeetings();

    /**
     * Add a contact to the model
     *
     * @param contact to add
     * @return id of newly created Contact
     * @throws java.lang.NullPointerException - if the contact is null
     */
    int addContact(Contact contact);

    /**
     * Get an instance of a contact by it's unique identifier
     *
     * @param id unique Contact identifier
     * @return Contact or null if not found
     */
    Contact getContact(int id);

    /**
     * Remove on instance of a Contact from the model
     *
     * @param contact Contact to remove
     */
    void removeContact(Contact contact);

    /**
     * Replace the instance of the Contact in the model
     *
     * @param contact amended Contact to store in the model
     */
    void updateContact(Contact contact);

    /**
     * Add a meeting to the model
     *
     * @param meeting to add
     * @return id of newly created Meeting
     * @throws java.lang.NullPointerException - if meeting is null
     */
    int addMeeting(Meeting meeting);

    /**
     * Get an instance of a Meeting by it's unique identifier
     *
     * @param id unique Meeting identifier
     * @return Meeting or null if not found
     */
    Contact getMeeting(int id);

    /**
     * Replace the instance of the Meeting in the model
     *
     * @param meeting amended Meeting to store in the model
     */
    void removeMeeting(Meeting meeting);

    /**
     * Replace the instance of the Meeting in the model
     *
     * @param meeting amended Meeting to store in the model
     */
    void updateMeeting(Meeting meeting);
}

