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
	//	****************************************************************
	//	load tests
	//	****************************************************************
	@Test(expected=PersistenceUnitException.class)
	public void load_UnknownFile() {
	
		try {
			instance.load();	
		} catch(PersistenceUnitException ex) {
			ex.printStackTrace();
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

		return new SerializableFilePersistenceUnit();
	}
}


