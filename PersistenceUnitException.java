/**
 *
 * @author sbaird02
 */
public class PersistenceUnitException extends Exception {

    private static final long serialVersionUID = 91907461442661L;

    /**
     * Creates a new instance of <code>PersistenceUnitException</code> without
     * detail message.
     */
    public PersistenceUnitException() {
    }

    /**
     * Constructs an instance of <code>PersistenceUnitException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PersistenceUnitException(String msg) {
        super(msg);
    }
}
