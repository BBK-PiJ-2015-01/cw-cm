import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class TestMeetingImpl {

	private MeetingImpl instance;
	private int defaultId;
	private Random r = new Random();
	
	@Before
	public void init() {
		
		defaultId = r.nextInt(Integer.MAX_VALUE);
		instance = new MeetingImpl(defaultId);
	}
	
	@Test
	public void getIdTest() {

		int expectedId = defaultId;
		int resultId = instance.getId();
		assertEquals(expectedId, resultId);
	}

	
	@Test
	public void getDateNotSetTest() {
	
		Calendar resultDate = instance.getDate();
		assertNull(resultDate);
	}

	@Test
	public void setDateWithNullTest() {
	
		Calendar expectedDate = null;
		instance.setDate(expectedDate);
		Calendar resultDate = instance.getDate();
		assertEquals(expectedDate, resultDate);
	}
}


