import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestContactImpl {

	protected ContactImpl instance;
	protected int defaultId;
	protected Random r = new Random();
	
	@Before
	public void init() {
		
		instance = new ContactImpl(generateDefaultId());
	}

	@Test
	public void getIdTest() {

		int expectedId = defaultId;
		int resultId = instance.getId();
		assertEquals(expectedId, resultId);
	}

	@Test
	public void getNameNotSetTest() {

		String expectedName = null;
		String resultName = instance.getName();
		assertEquals(expectedName, resultName);
	}


	protected int generateDefaultId() {

		defaultId = r.nextInt(Integer.MAX_VALUE);
		return defaultId;
	}



}
