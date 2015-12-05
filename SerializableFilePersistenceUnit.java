/**
 *
 * @author sbaird02
 */
public class SerializableFilePersistenceUnit implements PersistenceUnit {

	

	@Override
	public void load() throws PersistenceUnitException {
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public void commit() throws PersistenceUnitException {
	throw new UnsupportedOperationException("Unsupported operation.");
	}

	@Override
	public ContactManagerModel getModel() {
	throw new UnsupportedOperationException("Unsupported operation.");
	}

}

