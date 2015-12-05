import org.junit.*;
import static org.junit.Assert.*;

//
//	Don't forget org.junit.runner.JUnitCore !
//
public class TestSerializableFilePersistenceUnit {

	private PersistenceUnit instance;
	private final String expectedFileName = "contacts.txt";
	
	@Before
	public void init() {
		
		instance = getInstance() ;
	}
	//	****************************************************************
	//	load tests
	//	****************************************************************
	@Test(expected=PersistenceUnitException.class)
	public void load_UnknownFile() throws PersistenceUnitException {
	
		try {
			instance.load();	
		} catch(PersistenceUnitException ex) {
			throw ex;
		}
	}

	//	****************************************************************
	//	get Model tests
	//	****************************************************************
	@Test
	public void getModel_NotLoaded() {
	
		ContactManagerModel model = instance.getModel();
		assertNull(model);
		
	}

	protected PersistenceUnit getInstance() {

		return new SerializableFilePersistenceUnit(expectedFileName);
	}
}


