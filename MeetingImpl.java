import java.util.Calendar;
import java.util.Set;

/**
 *
 * 
 */
public class MeetingImpl implements Meeting {

    @Override
    public int getId() {
	return -1;
    }

    @Override
    public Calendar getDate() {
        throw new UnsupportedOperationException("Not implemented"); 
    }

    @Override
    public Set<Contact> getContacts() {
        throw new UnsupportedOperationException("Not implemented"); 
    }
    
}
