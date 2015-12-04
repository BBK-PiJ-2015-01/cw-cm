import java.util.*;

public interface ModelMeeting extends Meeting {


    /**
     * Set the the meeting date.
     *     
     * @param date the meeting date
     */

    void setDate(Calendar date);

    /**
     * Set the details of people that attended the meeting.
     *     
     * @param contacts the set of people attending the meeting
     */
    void setContacts(Set<Contact> contacts);

    /**
     * Returns the notes of the meeting.
     *    
     *     
     * @return the notes from the meeting or an empty string.
     */
    String getNotes();

    /**
     * Set the meeting notes.
     *    
     * @param notes the notes from the meeting
     */
    void addNotes(String notes);
}

