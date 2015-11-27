import java.util.Calendar;
import java.util.Set;

/**
 *
 * 
 */
public class MeetingImpl implements Meeting {

	private final int id;
	private Calendar date;


	public MeetingImpl(int id) {

		this.id = id;
	}

	@Override
	public int getId() {

		return id;
	}

	@Override
	public Calendar getDate() {

		return date;
	}
	/**
	* Set the date of the meeting.
	*     
	* @param the date of the meeting. Null values are permitted
	*/
	public void setDate(Calendar date) {

		this.date = date;
	}

	@Override
	public Set<Contact> getContacts() {
		throw new UnsupportedOperationException("Not implemented"); 
	}
    
}
