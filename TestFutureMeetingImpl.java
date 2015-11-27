import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestFutureMeetingImpl extends TestMeetingImpl {

	private FutureMeetingImpl thisInstance;

	@Before
	public void init() {
		
		instance = new FutureMeetingImpl(generateDefaultId());
		thisInstance = (FutureMeetingImpl) instance;
	}
}
