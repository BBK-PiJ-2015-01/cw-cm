import java.util.*;

/**
 *
 * 
 */
public class ContactImpl implements Contact {

	private final int id;
	private String name;


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

	@Override
	public String getNotes() {
	throw new UnsupportedOperationException("Not implemented.");
	}

	@Override
	public void addNotes(String note) {
	throw new UnsupportedOperationException("Not implemented.");
	}
}
