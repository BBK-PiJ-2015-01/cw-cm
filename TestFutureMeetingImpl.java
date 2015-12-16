import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestFutureMeetingImpl {

	@Test(expected=NullPointerException.class)
	public void constructor_nullDate() {

		new FutureMeetingImpl(1, null, Collections.emptySet());
	}

	@Test(expected=NullPointerException.class)
	public void constructor_nullContacts() {

		new FutureMeetingImpl(1, Calendar.getInstance(), null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_zeroId() {

		new FutureMeetingImpl(0, Calendar.getInstance(), Collections.emptySet());
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_negativeId() {

		new FutureMeetingImpl(-1, Calendar.getInstance(), Collections.emptySet());
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_emptyContacts() {

		new FutureMeetingImpl(1, Calendar.getInstance(), Collections.emptySet());
	}	
}
