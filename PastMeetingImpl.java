import java.util.*;

/**
 *
 * 
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	private String notes;

	public PastMeetingImpl(int id, Calendar date, Set<Contact> contacts, String notes) {

		super(id, date, contacts);
		if (notes == null ) {
			throw new NullPointerException();
		}
		this.notes = notes;
	}



//	public PastMeetingImpl(ModelMeeting model) {

//		super(model);
//	}

	@Override
	public String getNotes() {
		
		return notes;
//		return model.getNotes() == null ? new String() : model.getNotes();
	}

//	public void setNotes(String notes) {

//		model.addNotes(notes);
//	}
}
