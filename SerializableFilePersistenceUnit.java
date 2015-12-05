/**
 *
 * @author sbaird02
 */
public class SerializableFilePersistenceUnit implements PersistenceUnit {

	private String fileName;

	public SerializableFilePersistenceUnit(String fileName) {

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

