import java.util.*;

/**
 *
 * 
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {


	public PastMeetingImpl(int id) {

		super(id);
	}

	@Override
	public String getNotes() {
		throw new UnsupportedOperationException("Not implemented.");
	}

	public void setNotes(String notes) {
		throw new UnsupportedOperationException("Not implemented.");
	}
}
