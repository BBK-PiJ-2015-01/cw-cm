import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

//
//	Don't forget org.junit.runner.JUnitCore !
//
public class TestDefaultModelMeeting {

	private ModelMeeting instance;
	protected int defaultId;
	protected Random r = new Random(100);
	
	@Before
	public void init() {
		
		instance = getInstance(generateDefaultId());
	}
	
	@Test
	public void getId() {
		
		int resultId = instance.getId();
		assertEquals(defaultId, resultId);
	}

	@Test
	public void getDate_NotSet() {
		
		Calendar resultDate = instance.getDate();
		assertNull(resultDate);
	}

	protected ModelMeeting getInstance(int id) {

		return new DefaultModelMeeting(id);
	}

	protected int generateDefaultId() {

		defaultId = r.nextInt(Integer.MAX_VALUE);
		return defaultId;
	}
}


