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
	
	@Test
	public void addMeeting_Null() {

		
		
	}

	protected SerializableContactManagerModel getInstance() {
	
		return new SerializableContactManagerModel();
	}
}


