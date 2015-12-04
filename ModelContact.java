public interface ModelContact {

    /**
     * Returns the ID of the contact.
     *
     * @return the ID of the contact.
     */
    int getId();

    /**
     * Returns the name of the contact.
     *
     * @return the name of the contact.
     */
    String getName();

    /**
     * Set the name of the contact.
     *
     * @param name Contact name
     */
    void setName(String name);

    /**
     * Returns the contact notes.
     *
     *
     * @return a string with notes about the contact
     */
    String getNotes();

    /**
     * Add notes about the contact.
     *
     * @param note the notes to be added
     */
    void addNotes(String note);
}
