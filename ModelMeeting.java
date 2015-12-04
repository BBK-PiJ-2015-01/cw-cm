import java.util.*;

public interface ModelMeeting {

    /**
     * Returns the id of the meeting.
     *     
     * @return the id of the meeting.
     */
    int getId();

    /**
     * Return the date of the meeting.
     *     
     * @return the date of the meeting.
     */
    Calendar getDate();

    /**
     * Set the the meeting date.
     *     
     * @param date the meeting date
     */

    void setDate(Calendar date);
    /**
     * Return the details of people that attended the meeting.
     *     
     * @return the set of contacts
     */
    Set<ModelContact> getContacts();

    /**
     * Set the details of people that attended the meeting.
     *     
     * @param contacts the set of people attending the meeting
     */
    void setContacts(Set<ModelContact> contacts);

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
    void setNotes(String notes);
}

