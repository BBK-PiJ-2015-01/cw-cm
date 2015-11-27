import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
public class TestPastMeetingImpl extends TestMeetingImpl {

	private PastMeetingImpl thisInstance;

	@Before
	public void init() {
		
		instance = new PastMeetingImpl(generateDefaultId());
		thisInstance = (PastMeetingImpl) instance;
	}
}
