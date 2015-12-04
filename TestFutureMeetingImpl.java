import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;


public class TestFutureMeetingImpl extends TestMeetingImpl {

	private FutureMeetingImpl thisInstance;

	@Before
	public void init() {
		
		instance = getInstance(generateDefaultId());
		thisInstance = (FutureMeetingImpl) instance;
	}

	protected MeetingImpl getInstance(int id) {

		ModelMeeting model = new DefaultModelMeeting(id);
		return new FutureMeetingImpl(model);
	}
}
