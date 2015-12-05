import java.io.*;
/**
 *
 * @author sbaird02
 */
public class SerializableFilePersistenceUnit implements PersistenceUnit {

	private final String fileName;
	private final String INVALID_FILENAME_MSG = "An invalid filename was supplied";	
	private final String FILE_NOTFOUND_MSG = "The file was not found";
	private final String NOT_LOADED_MSG = "The load() method was not successfully executed";
	private boolean loaded = false;
	private ContactManagerModel model;

	public SerializableFilePersistenceUnit(String fileName) throws PersistenceUnitException{
	
		if (fileName == null || fileName.isEmpty()) {
			throw new IllegalArgumentException(INVALID_FILENAME_MSG);
		}	
		this.fileName = fileName;	
		performLoad();
	}

	@Override
	public void load() throws PersistenceUnitException {

		performLoad();
	}

	@Override
	public void commit() throws PersistenceUnitException {

		if  (!loaded) {
			throw new PersistenceUnitException (NOT_LOADED_MSG);
		}
		if (fileName == null) {
			throw new PersistenceUnitException("File not found.");
		}
		try (FileOutputStream fos = new FileOutputStream(fileName); ObjectOutputStream oos = new ObjectOutputStream(fos) ) {
			oos.writeObject(model);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new PersistenceUnitException(ex.getMessage()); 
		}
	}

	@Override
	public ContactManagerModel getModel() throws PersistenceUnitException {

		if  (!loaded) {
			throw new PersistenceUnitException (NOT_LOADED_MSG);
		}
		return model;
	}

	// ********************************************************************
	//	Convenience methods
	// ********************************************************************
	private ContactManagerModel getContactManagerModelInstance() {

		return new SerializableContactManagerModel();
	}

	private void performLoad() throws PersistenceUnitException{

		model = null;
		loaded = true;
		final long EMPTY_FILE_SIZE = 0L;
		if (fileName == null) {
			throw new PersistenceUnitException("File not found.");
		}
		File destinationFile = new File(fileName);
		if  (!destinationFile.exists() || destinationFile.length() == EMPTY_FILE_SIZE) {
			// Create empty model
			model = getContactManagerModelInstance();
		} else {
			try (FileInputStream fis = new FileInputStream(destinationFile); ObjectInputStream ois = new ObjectInputStream(fis) ) {
				model = (SerializableContactManagerModel) ois.readObject();
				loaded = true;
			} catch (IOException | ClassNotFoundException ex) {
				throw new PersistenceUnitException(ex.getMessage()); 
			}
		}
	}
}

