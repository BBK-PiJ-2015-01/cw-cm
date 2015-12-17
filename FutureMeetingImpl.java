import java.util.*;

/**
 * 
 * @author sbaird02
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

	public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {

		super(id, date, contacts);
	}
}
