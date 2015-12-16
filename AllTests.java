import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
{	TestFutureMeetingImpl.class 
,	TestMeetingImpl.class  
,	TestPastMeetingImpl.class  
,	TestStub.class
,	TestContactImpl.class
,	TestContactManagerImpl.class
,	TestContactManagerImplMeeting.class
,	TestDefaultModelMeeting.class
,	TestDefaultModelContact.class
,	TestSerializableContactManagerModel.class
,	TestSerializableFilePersistenceUnit.class
,	TestContactManagerAsApp.class})
public class AllTests {}
