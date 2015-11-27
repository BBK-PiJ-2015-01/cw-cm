import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
{	TestFutureMeetingImpl.class 
,	TestMeetingImpl.class  
,	TestPastMeetingImpl.class  
,	TestStub.class
})
public class AllTests {}
