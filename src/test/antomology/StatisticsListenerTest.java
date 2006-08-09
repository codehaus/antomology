package antomology;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

import antomology.StatisticsListener;

import junit.framework.TestCase;

public class StatisticsListenerTest extends TestCase {

	private Duration buildDuration;

	private SeriesMap targetSeriesMap;

	private SeriesMap taskSeriesMap;

	private Clock clock;

	private StatisticsListener listener;

	public class Clock {
		private long currentTime;

		public long getCurrentTime() {
			return currentTime;
		}

		public void setCurrentTime(long currentTime) {
			this.currentTime = currentTime;
		}
	}

	public void setUp() throws Exception {
		super.setUp();
		clock = new Clock();
		buildDuration = new Duration();
		targetSeriesMap = new SeriesMap();
		taskSeriesMap = new SeriesMap();
		listener = createTimerListener(clock);
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

	private StatisticsListener createTimerListener(final Clock c) {
		return new StatisticsListener(buildDuration, targetSeriesMap,
				taskSeriesMap) {
			public long currentTime() {
				return c.getCurrentTime();
			}
		};
	}

	public void testShouldCalculateDurationOfTarget() {
		clock.setCurrentTime(10);
		listener.targetStarted(createTargetBuildEvent("target"));
		clock.setCurrentTime(100);
		listener.targetFinished(createTargetBuildEvent("target"));
		assertEquals(90, targetSeriesMap.getSeries("target").getTimes()[0]);
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
		assertEquals(90, taskSeriesMap.getSeries("antcall").getTimes()[0]);
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
		assertEquals(90, targetSeriesMap.getSeries("target2").getTimes()[0]);
		assertEquals(90, targetSeriesMap.getSeries("target1").getTimes()[0]);
	}

	public void testShouldCalculateDurationOfSameTwoTargets() {
		clock.setCurrentTime(10);
		listener.targetStarted(createTargetBuildEvent("target"));
		listener.targetStarted(createTargetBuildEvent("target"));
		clock.setCurrentTime(90);
		listener.targetFinished(createTargetBuildEvent("target"));
		clock.setCurrentTime(100);
		listener.targetFinished(createTargetBuildEvent("target"));
		long[] times = targetSeriesMap.getSeries("target").getTimes();
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
		long[] times = taskSeriesMap.getSeries("antcall").getTimes();
		assertEquals(2, times.length);
		assertEquals(90, times[0]);
		assertEquals(80, times[1]);
	}

}
