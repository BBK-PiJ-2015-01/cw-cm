import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

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
	public void constructor_NullFile()  throws PersistenceUnitException {
	
		instance = getInstance(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_EmptyFilename()  throws PersistenceUnitException {
	
		instance = getInstance("");
	}

	//	****************************************************************
	//	load tests
	//	****************************************************************
	@Test
	public void load_UnknownFile()  throws PersistenceUnitException{
	
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


	//	****************************************************************
	//	commit tests
	//	****************************************************************


	//	****************************************************************
	//	sequence tests
	//	****************************************************************	
	@Test
	public void crudSequence_NoCommit() throws PersistenceUnitException {
	
		final String testFileName = String.format("%d.txt", System.nanoTime());
		instance = getInstance(testFileName);
		ContactManagerModel model = instance.getModel();
		assertNotNull(model);
		int contactId = model.addContact("name", "notes");
		ModelContact contact = model.getContact(contactId);
		assertNotNull(contact);
		File expectedFile = new File(testFileName);
		assertFalse(expectedFile.exists());
	}
	@Test
	public void crudSequence_WithCommit() throws PersistenceUnitException {
	
		final String testFileName = String.format("%d.txt", System.nanoTime());
		instance = getInstance(testFileName);
		ContactManagerModel model = instance.getModel();
		assertNotNull(model);
		int contactId = model.addContact("name", "notes");
		ModelContact contact = model.getContact(contactId);
		assertNotNull(contact);
		instance.commit();
		File expectedFile = new File(testFileName);
		assertTrue(expectedFile.exists());
		expectedFile.delete();
		assertFalse(expectedFile.exists());
	}

	@Test
	public void crudSequence_NonNullsContactWithCommitAndReload() throws PersistenceUnitException {
	
		final String testFileName = String.format("%d.txt", System.nanoTime());
		instance = getInstance(testFileName);
		ContactManagerModel model = instance.getModel();
		assertNotNull(model);
		String expectedContactName = "name";
		String expectedContactNotes = "notes";
		int contactId = model.addContact(expectedContactName, expectedContactNotes);
		instance.commit();
		// new instance
		instance = getInstance(testFileName);
		model = instance.getModel();
		assertNotNull(model);
		ModelContact resultContact = model.getContact(contactId);
		assertNotNull(resultContact);
		assertEquals(expectedContactName, resultContact.getName());
		assertEquals(expectedContactNotes, resultContact.getNotes());
		// cleanup
		File expectedFile = new File(testFileName);
		assertTrue(expectedFile.exists());
		expectedFile.delete();
		assertFalse(expectedFile.exists());
	}
	@Test
	public void crudSequence_NullsMeetingWithCommitAndReload() throws PersistenceUnitException {
	
		final String testFileName = String.format("%d.txt", System.nanoTime());
		instance = getInstance(testFileName);
		ContactManagerModel model = instance.getModel();
		assertNotNull(model);
		int meetingId = model.addMeeting(null, null, null);
		instance.commit();
		// new instance
		instance = getInstance(testFileName);
		model = instance.getModel();
		assertNotNull(model);
		ModelMeeting resultMeeting = model.getMeeting(meetingId);
		assertNotNull(resultMeeting);
		assertNull(resultMeeting.getDate());
		assertNull(resultMeeting.getContacts());
		assertNull(resultMeeting.getNotes());
		// cleanup
		File expectedFile = new File(testFileName);
		assertTrue(expectedFile.exists());
		expectedFile.delete();
		assertFalse(expectedFile.exists());
	}


	@Test
	public void commit_AfterLoad() throws PersistenceUnitException {

		instance = getInstance(expectedFileName) ;
		instance.load();
		instance.commit();
	}

	protected PersistenceUnit getInstance(String fileName) throws PersistenceUnitException {

		return new SerializableFilePersistenceUnit(fileName);
	}
}


