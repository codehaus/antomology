package antomology;

import junit.framework.TestCase;

import org.apache.tools.ant.BuildEvent;

public class StatisticsListenerTest extends TestCase {

	private StubClock clock;

	private StatisticsListener listener;

	private BuildEventFactory factory = new BuildEventFactory();

	public void setUp() throws Exception {
		super.setUp();
		clock = new StubClock();
		listener = new StatisticsListener(clock);
	}

	public void testShouldCreateInstanceWithoutErrorAndInvokeMessageLogged() {
		new StatisticsListener().messageLogged(factory
				.createProjectBuildEvent());
	}

	public void testShouldCalculateDurationOfBuild() {
		clock.setCurrentTime(10);
		final BuildEvent buildEvent = factory.createProjectBuildEvent();
		listener.buildStarted(buildEvent);
		clock.setCurrentTime(100);
		listener.buildFinished(buildEvent);
		assertEquals(90, listener.findInitialProjectTimer().getTime());
	}

	public void testShouldCalculateDurationOfSubBuild() {
		clock.setCurrentTime(10);
		final BuildEvent buildEvent = factory.createProjectBuildEvent();
		listener.subBuildStarted(buildEvent);
		clock.setCurrentTime(100);
		listener.subBuildFinished(buildEvent);
		final ProjectTimer projectTimer = listener.projectTimerMap
				.get(buildEvent.getProject());
		assertEquals(90, projectTimer.getTime());
	}

	public void testShouldCalculateDurationOfTarget() {
		final BuildEvent projectBuildEvent = factory.createProjectBuildEvent();
		listener.buildStarted(projectBuildEvent);
		clock.setCurrentTime(10);
		final BuildEvent targetBuildEvent = factory
				.createTargetBuildEvent("target");
		listener.targetStarted(targetBuildEvent);
		clock.setCurrentTime(100);
		listener.targetFinished(targetBuildEvent);
		final ProjectTimer projectTimer = listener.projectTimerMap
				.get(projectBuildEvent.getProject());
		assertEquals(90, projectTimer.getTargetTimer("target").getTime());
		listener.buildFinished(projectBuildEvent);
	}

	public void testShouldCalculateDurationOfTask() {
		final BuildEvent projectBuildEvent = factory.createProjectBuildEvent();
		listener.buildStarted(projectBuildEvent);
		clock.setCurrentTime(10);
		final BuildEvent taskBuildEvent = factory
				.createTaskBuildEvent("antcall");
		listener.taskStarted(taskBuildEvent);
		clock.setCurrentTime(100);
		listener.taskFinished(taskBuildEvent);
		final ProjectTimer projectTimer = listener.projectTimerMap
				.get(projectBuildEvent.getProject());
		assertEquals(90, projectTimer.getTaskTimer("antcall").getTime());
		listener.buildFinished(projectBuildEvent);
	}

	public void testShouldCalculateDurationOfTwoTargets() {
		final BuildEvent projectBuildEvent = factory.createProjectBuildEvent();
		listener.buildStarted(projectBuildEvent);
		clock.setCurrentTime(10);
		final BuildEvent targetBuildEvent1 = factory
				.createTargetBuildEvent("target1");
		listener.targetStarted(targetBuildEvent1);
		final BuildEvent targetBuildEvent2 = factory
				.createTargetBuildEvent("target2");
		listener.targetStarted(targetBuildEvent2);
		clock.setCurrentTime(100);
		listener.targetFinished(targetBuildEvent1);
		listener.targetFinished(targetBuildEvent2);
		final ProjectTimer projectTimer = listener.projectTimerMap
				.get(projectBuildEvent.getProject());
		assertEquals(90, projectTimer.getTargetTimer("target2").getTime());
		assertEquals(90, projectTimer.getTargetTimer("target1").getTime());
		listener.buildFinished(projectBuildEvent);
	}

	public void testShouldCalculateDurationOfSameTwoTargets() {
		final BuildEvent projectBuildEvent = factory.createProjectBuildEvent();
		listener.buildStarted(projectBuildEvent);
		clock.setCurrentTime(10);
		final BuildEvent targetBuildEvent = factory
				.createTargetBuildEvent("target");
		listener.targetStarted(targetBuildEvent);
		listener.targetStarted(targetBuildEvent);
		clock.setCurrentTime(90);
		listener.targetFinished(targetBuildEvent);
		clock.setCurrentTime(100);
		listener.targetFinished(targetBuildEvent);
		final ProjectTimer projectTimer = listener.projectTimerMap
				.get(projectBuildEvent.getProject());
		long[] times = projectTimer.getTargetTimer("target").getSeries()
				.getTimes();
		assertEquals(2, times.length);
		assertEquals(90, times[0]);
		assertEquals(80, times[1]);
		listener.buildFinished(projectBuildEvent);
	}

	public void testShouldCalculateDurationOfSameTwoTasks() {
		final BuildEvent projectBuildEvent = factory.createProjectBuildEvent();
		listener.buildStarted(projectBuildEvent);
		clock.setCurrentTime(10);
		final BuildEvent taskBuildEvent = factory
				.createTaskBuildEvent("antcall");
		listener.taskStarted(taskBuildEvent);
		listener.taskStarted(taskBuildEvent);
		clock.setCurrentTime(90);
		listener.taskFinished(taskBuildEvent);
		clock.setCurrentTime(100);
		listener.taskFinished(taskBuildEvent);
		final ProjectTimer projectTimer = listener.projectTimerMap
				.get(projectBuildEvent.getProject());
		long[] times = projectTimer.getTaskTimer("antcall").getSeries()
				.getTimes();
		assertEquals(2, times.length);
		assertEquals(90, times[0]);
		assertEquals(80, times[1]);
		listener.buildFinished(projectBuildEvent);
	}

}
