
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 *
 * @author user0001
 */
public class ContactManagerImpl implements ContactManager {

    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public PastMeeting getPastMeeting(int id) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public FutureMeeting getFutureMeeting(int id) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Meeting getMeeting(int id) {
        throw new UnsupportedOperationException("Not implemented."); 
   }

    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {
        throw new UnsupportedOperationException("Not implemented."); 
   }

    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void addMeetingNotes(int id, String text) {
        throw new UnsupportedOperationException("Not implemented."); 
   }

    @Override
    public void addNewContact(String name, String notes) {
        throw new UnsupportedOperationException("Not implemented."); 
   }

    @Override
    public Set<Contact> getContacts(int... ids) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public Set<Contact> getContacts(String name) {
        throw new UnsupportedOperationException("Not implemented."); 
   }

    @Override
    public void flush() {
        throw new UnsupportedOperationException("Not implemented.");
    }
    
}

