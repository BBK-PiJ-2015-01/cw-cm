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
		
//		instance = getInstance() ;
	}
	//	****************************************************************
	//	constructor tests
	//	****************************************************************
	@Test(expected=IllegalArgumentException.class)
	public void constructor_NullFile() {
	
		instance = getInstance(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_EmptyFilename() {
	
		instance = getInstance("");
	}

	//	****************************************************************
	//	load tests
	//	****************************************************************
	@Test
	public void load_UnknownFile() {
	
		instance = getInstance(expectedFileName) ;
		try {
			instance.load();
			ContactManagerModel model = instance.getModel();
			assertNotNull(model);
		} catch(PersistenceUnitException ex) {
			ex.printStackTrace();
		}
	}
	//	****************************************************************
	//	get Model tests
	//	****************************************************************
	@Test(expected=PersistenceUnitException.class)
	public void getModel_NotLoaded() throws PersistenceUnitException {
	
		instance = getInstance(expectedFileName) ;
		try {
			instance.getModel();
		} catch(PersistenceUnitException ex) {
			throw ex;
		}
	}

	//	****************************************************************
	//	commit tests
	//	****************************************************************
	@Test(expected=PersistenceUnitException.class)
	public void commit_NotLoaded() throws PersistenceUnitException {
	
		getInstance(expectedFileName).commit() ;
	}

	protected PersistenceUnit getInstance(String fileName) {

		return new SerializableFilePersistenceUnit(fileName);
	}
}


