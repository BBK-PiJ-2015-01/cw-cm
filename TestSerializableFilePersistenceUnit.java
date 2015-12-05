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
	@Test(expected=PersistenceUnitException.class)
	public void load_UnknownFile() throws PersistenceUnitException {
	
		instance = getInstance(expectedFileName) ;
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
	
		instance = getInstance(expectedFileName) ;
		ContactManagerModel model = instance.getModel();
		assertNull(model);
		
	}

	protected PersistenceUnit getInstance(String fileName) {

		return new SerializableFilePersistenceUnit(fileName);
	}
}


