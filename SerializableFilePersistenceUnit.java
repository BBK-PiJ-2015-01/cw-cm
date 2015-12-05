/**
 *
 * @author sbaird02
 */
public class SerializableFilePersistenceUnit implements PersistenceUnit {

	private String fileName;
	private final String INVALID_FILENAME_MSG = "An invalid filename was supplied";

	public SerializableFilePersistenceUnit(String fileName) {
	
		if (fileName == null || fileName.isEmpty()) {
			throw new IllegalArgumentException(INVALID_FILENAME_MSG);
		}		
	}

	@Override
	public void load() throws PersistenceUnitException {
		throw new PersistenceUnitException("File not found.");
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
	private void loadModelFromFile(String fileName) {
	}
}

