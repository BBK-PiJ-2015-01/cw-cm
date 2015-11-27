import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class TestMeetingImpl {

	private Meeting instance;
	private int defaultId;
	private Random r = new Random();
	
	@Before
	public void init() {
		
		defaultId = r.nextInt(Integer.MAX_VALUE);
		instance = new MeetingImpl(defaultId);
	}
	
	@Test
	public void getIdWhenNotSetTest() {

		int expectedId = defaultId;
		int resultId = instance.getId();
		assertEquals(expectedId, resultId);
	}
}


