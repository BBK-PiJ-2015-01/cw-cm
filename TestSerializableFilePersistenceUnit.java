import org.junit.*;
import static org.junit.Assert.*;

//
//	Don't forget org.junit.runner.JUnitCore !
//
public class TestSerializableFilePersistenceUnit {

	private PersistenceUnit instance;
	
	@Before
	public void init() {
		
		instance = getInstance() ;
	}
	
	@Test
	public void standardMethodTest() {
		
	}

	protected PersistenceUnit getInstance() {

		return new SerializableFilePersistenceUnit();
	}
}


