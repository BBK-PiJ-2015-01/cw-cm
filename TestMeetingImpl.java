import org.junit.*;
import static org.junit.Assert.*;


public class TestMeetingImpl {

	private Meeting instance;
	
	@Before
	public void init() {
		
		instance = new MeetingImpl();
	}
	
	@Test
	public void getIdWhenNotSetTest() {

		int expectedId = 0;
		int resultId = instance.getId();
		assertEquals(expectedId, resultId);
	}
}


