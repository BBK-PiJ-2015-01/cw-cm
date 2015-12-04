import java.util.*;

/**
 *
 * 
 */
public class ContactImpl implements Contact {

	private final ModelContact model;
	private final String NULL_MODEL_MSG = "Supplied model was null";

	public ContactImpl(ModelContact model) {

		if (model == null) {
			throw new IllegalArgumentException(NULL_MODEL_MSG);
		}
		this.model = model;
	}

	@Override
	public int getId() {

		return model.getId();
	}

	@Override
	public String getName() {

		return model.getName();
	}

	public void setName(String name) {
	
		model.setName(name);
	}

	@Override
	public String getNotes() {

		return model.getNotes() == null ? new String() : model.getNotes();
	}

	@Override
	public void addNotes(String notes) {

		model.addNotes(notes);
	}

	/**
	*	Allow equality on id
	*
	*/
	@Override
	public boolean equals(Object other) {

		return model.equals(other);
	}

	@Override
	public int hashCode() {

		return model.hashCode();
	}
}
