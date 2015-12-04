import java.util.*;

/**
 *
 * 
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	private String notes;

	public PastMeetingImpl(ModelMeeting model) {

		super(model);
	}

	@Override
	public String getNotes() {

		return model.getNotes() == null ? new String() : model.getNotes();
	}

	public void setNotes(String notes) {

		model.addNotes(notes);
	}
}
