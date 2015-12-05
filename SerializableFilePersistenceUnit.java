import java.io.*;
/**
 *
 * @author sbaird02
 */
public class SerializableFilePersistenceUnit implements PersistenceUnit {

	private final String fileName;
	private final String INVALID_FILENAME_MSG = "An invalid filename was supplied";	
	private final String FILE_NOTFOUND_MSG = "The file was not found";
	private boolean loaded = false;

	public SerializableFilePersistenceUnit(String fileName) {
	
		if (fileName == null || fileName.isEmpty()) {
			throw new IllegalArgumentException(INVALID_FILENAME_MSG);
		}	
		this.fileName = fileName;	
	}

	@Override
	public void load() throws PersistenceUnitException {

		if (fileName == null) {
			throw new PersistenceUnitException("File not found.");
		}
		File destinationFile = new File(fileName);
		if  (destinationFile.exists()) {
			// load the model
		
		}
		loaded = true;
	}

	@Override
	public void commit() throws PersistenceUnitException {
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public ContactManagerModel getModel() {
		return null;
	}
	// ********************************************************************
	//	Convenience methods
	// ********************************************************************

}

