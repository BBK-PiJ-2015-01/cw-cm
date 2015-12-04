public interface ModelContact extends Contact {



	/**
	* Set the name of the contact.
	*
	* @param name Contact name
	*/
	void setName(String name);


	/**
	* Performs a deep clone of the contact.
	*
	* @return a new instance of DefaultModelContact
	*/
	DefaultModelContact clone();
}
