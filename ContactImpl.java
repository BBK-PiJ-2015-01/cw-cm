import java.util.*;

/**
 *
 * 
 */
public class ContactImpl implements Contact {

	private final int id;
	private String name;
	private String notes;


	public ContactImpl(int id) {

		this.id = id;
	}



	@Override
	public int getId() {

		return id;
	}

	@Override
	public String getName() {

		return name;
	}

	public void setName(String name) {
	
		this.name = name;
	}

	@Override
	public String getNotes() {

		return notes == null ? new String() : notes;
	}

	@Override
	public void addNotes(String notes) {

		this.notes = notes;
	}
}
