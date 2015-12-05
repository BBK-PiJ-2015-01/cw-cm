/**
 *
 * @author sbaird02
 */
public interface PersistenceUnit {

    /**
     * Load the model from the persistence unit.
     *
     * @throws PersistenceUnitException
     */
    void load() throws PersistenceUnitException;

    /**
     * Commit updates made to the model to the persistence unit.
     *
     * @throws PersistenceUnitException
     */
    void commit() throws PersistenceUnitException;

    /**
     * Return the ContactManager model
     *
     * @return an instance of the ContactManagerModel
     * @throws PersistenceUnitException if called before load()
     */
    ContactManagerModel getModel() throws PersistenceUnitException;

}

