import java.util.*;

/**
 *
 * 
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	private String notes;

	public PastMeetingImpl(int id) {

		super(id);
	}

	@Override
	public String getNotes() {

		return notes == null ? new String() : notes;
	}

	public void setNotes(String notes) {
		throw new UnsupportedOperationException("Not implemented.");
	}
}
