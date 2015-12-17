import java.util.*;

/**
 *
 * @author sbaird02
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	private String notes;
	/**
	* Create a past meeting. Constructor is the same as MeetingImpl with
	* an additional param representing the meeting notes
	*
	* @param id the unique Meeting identifier. 
	* @param date the date on which the meeting is to take place. 
	* @param contacts a Set containing at least one valid Contact.. 
	* @param notes text relevant to the meeting. 
	* @throws NullPointerException if date,contacts or notes are null
	* @throws IllegalArgumentException if id is invalid or contacts is empty
	*/
	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {

		super(id, date, contacts);
		if (notes == null ) {
			throw new NullPointerException();
		}
		this.notes = notes;
	}

	@Override
	public String getNotes() {
		
		return notes;
	}
}
