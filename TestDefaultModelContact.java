import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestDefaultModelContact {

	protected ModelContact instance;
	protected int defaultId;
	protected Random r = new Random();
	
	@Before
	public void init() {
		
		instance = new DefaultModelContact(generateDefaultId());
	}

	@Test
	public void getIdTest() {

		int resultId = instance.getId();
		assertEquals(defaultId, resultId);
	}

	@Test
	public void getName_NotSet() {

		String resultName = instance.getName();
		assertNull(resultName);
	}

	protected int generateDefaultId() {

		defaultId = r.nextInt(Integer.MAX_VALUE);
		return defaultId;
	}
}

