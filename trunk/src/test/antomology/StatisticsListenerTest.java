package antomology;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

import antomology.StatisticsListener;

import junit.framework.TestCase;

public class StatisticsListenerTest extends TestCase {

	private Build build;

	private StubClock clock;

	private StatisticsListener listener;

	public void setUp() throws Exception {
		super.setUp();
		clock = new StubClock();
		build = new Build("main", clock);
		listener = createTimerListener(build, clock);
	}

	public void testShouldCreateInstanceWithoutErrorAndInvokeMessageLogged() {
		new StatisticsListener().messageLogged(new BuildEvent(createProject()));
	}

	public void testShouldCalculateDurationOfBuild() {
		clock.setCurrentTime(10);
		listener.buildStarted(createProjectBuildEvent());
		clock.setCurrentTime(100);
		listener.buildFinished(createProjectBuildEvent());
		assertEquals(90, listener.getBuildTime());
	}

	private BuildEvent createProjectBuildEvent() {
		return new BuildEvent(createProject());
	}

	private StatisticsListener createTimerListener(final Build build,
			final Clock c) {
		return new StatisticsListener(build);
	}

	public void testShouldCalculateDurationOfTarget() {
		clock.setCurrentTime(10);
		listener.targetStarted(createTargetBuildEvent("target"));
		clock.setCurrentTime(100);
		listener.targetFinished(createTargetBuildEvent("target"));
		assertEquals(90, build.getTarget("target").getTime());
	}

	private BuildEvent createTargetBuildEvent(String name) {
		Target target = new Target();
		target.setName(name);
		return new BuildEvent(target);
	}

	public void testShouldCalculateDurationOfTask() {
		clock.setCurrentTime(10);
		listener.taskStarted(createTaskBuildEvent("antcall"));
		clock.setCurrentTime(100);
		listener.taskFinished(createTaskBuildEvent("antcall"));
		assertEquals(90, build.getTask("antcall").getTime());
	}

	private Project createProject() {
		Project project = new Project();
		project.init();
		return project;
	}

	private BuildEvent createTaskBuildEvent(String name) {
		Task task = createProject().createTask(name);
		task.setOwningTarget(new Target());
		return new BuildEvent(task);
	}

	public void testShouldCalculateDurationOfTwoTargets() {
		clock.setCurrentTime(10);
		listener.targetStarted(createTargetBuildEvent("target1"));
		listener.targetStarted(createTargetBuildEvent("target2"));
		clock.setCurrentTime(100);
		listener.targetFinished(createTargetBuildEvent("target1"));
		listener.targetFinished(createTargetBuildEvent("target2"));
		assertEquals(90, build.getTarget("target2").getTime());
		assertEquals(90, build.getTarget("target1").getTime());
	}

	public void testShouldCalculateDurationOfSameTwoTargets() {
		clock.setCurrentTime(10);
		listener.targetStarted(createTargetBuildEvent("target"));
		listener.targetStarted(createTargetBuildEvent("target"));
		clock.setCurrentTime(90);
		listener.targetFinished(createTargetBuildEvent("target"));
		clock.setCurrentTime(100);
		listener.targetFinished(createTargetBuildEvent("target"));
		long[] times = build.getTarget("target").getSeries().getTimes();
		assertEquals(2, times.length);
		assertEquals(90, times[0]);
		assertEquals(80, times[1]);
	}

	public void testShouldCalculateDurationOfSameTwoTasks() {
		clock.setCurrentTime(10);
		listener.taskStarted(createTaskBuildEvent("antcall"));
		listener.taskStarted(createTaskBuildEvent("antcall"));
		clock.setCurrentTime(90);
		listener.taskFinished(createTaskBuildEvent("antcall"));
		clock.setCurrentTime(100);
		listener.taskFinished(createTaskBuildEvent("antcall"));
		long[] times = build.getTask("antcall").getSeries().getTimes();
		assertEquals(2, times.length);
		assertEquals(90, times[0]);
		assertEquals(80, times[1]);
	}

}
