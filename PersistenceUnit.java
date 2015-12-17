/**
 * A PersistenceUnit handles loading and commiting data stored in a 
 * ContactManager model. The storage format and location are not specified.
 *
 * @author sbaird02
 */
public interface PersistenceUnit {

    /**
     * Load the model from the persistence unit.
     *
     * @throws PersistenceUnitException if an error occurs locating or 
     * loading the persisted data 
     */
    void load() throws PersistenceUnitException;

    /**
     * Commit updates made to the model to the persistence unit.
     *
     * @throws PersistenceUnitException if an error occurs writing the 
     * model data
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

