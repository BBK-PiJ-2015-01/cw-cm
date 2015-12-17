import java.io.*;
/**
 * An implementation of <code>PersistenceUnit</code> that
 * stores <code>ContactManagerModel</code> in  a file in serialized format.
 * @author sbaird02
 */
public class SerializableFilePersistenceUnit implements PersistenceUnit {

	private final String fileName;
	private final String INVALID_FILENAME_MSG = "An invalid filename was supplied";	
	private final String FILE_NOTFOUND_MSG = "The file was not found";
	private final String NOT_LOADED_MSG = "The load() method was not successfully executed";
	private boolean loaded = false;
	private ContactManagerModel model;
	/**
	* Create with a resource file name.
	*
	* @param fileName the identifier of the file where the data is stored.
	* The file will be created if it does not exist.
	* @throws PersistenceUnitException if the file cannot be loaded.
	*/
	public SerializableFilePersistenceUnit(String fileName) throws PersistenceUnitException{
	
		if (fileName == null || fileName.isEmpty()) {
			throw new IllegalArgumentException(INVALID_FILENAME_MSG);
		}	
		this.fileName = fileName;	
		performLoad();
	}
	/**
	* Load the contents of the file into the <code>ContactManagerModel</code>.
	*
	* @throws PersistenceUnitException if the file cannot be loaded.
	*/
	@Override
	public void load() throws PersistenceUnitException {

		performLoad();
	}
	/**
	* Persist the <code>ContactManagerModel</code> in the file
	*
	* @throws PersistenceUnitException if the file cannot be written.
	*/
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
			System.out.println("Commit exception: " + ex.getClass().getSimpleName());
			ex.printStackTrace();
			throw new PersistenceUnitException(ex.getMessage()); 
		}
	}
	/**
	* Return the <code>ContactManagerModel</code>
	*
	* @throws PersistenceUnitException if the file has not been loaded.
	*/
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
		loaded = false;
		final long EMPTY_FILE_SIZE = 0L;
		if (fileName == null) {
			throw new PersistenceUnitException("File not found.");
		}
		File destinationFile = new File(fileName);
		if  (!destinationFile.exists() || destinationFile.length() == EMPTY_FILE_SIZE) {
			// Create empty model
			model = getContactManagerModelInstance();
			loaded = true;
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

