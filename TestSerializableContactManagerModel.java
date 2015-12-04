import org.junit.*;
import static org.junit.Assert.*;

//
//	Don't forget org.junit.runner.JUnitCore !
//
public class TestSerializableContactManagerModel {

	protected SerializableContactManagerModel instance;
	
	@Before
	public void init() {
		
		instance = getInstance();
	}
	
	@Test(expected=NullPointerException.class)
	public void addContact_Null() {

		instance.addContact(null);
		
	}

	protected SerializableContactManagerModel getInstance() {
	
		return new SerializableContactManagerModel();
	}
}


