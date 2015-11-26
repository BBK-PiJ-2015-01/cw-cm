import java.util.Calendar;
import java.util.Set;

/**
 *
 * 
 */
public class MeetingImpl implements Meeting {

    private final int id = 0;

    @Override
    public int getId() {

	return id;
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
