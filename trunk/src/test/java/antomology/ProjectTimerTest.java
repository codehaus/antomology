package antomology;

import org.apache.tools.ant.Project;

import junit.framework.TestCase;

public class ProjectTimerTest extends TestCase {

	private StubClock clock;

	private ProjectTimer projectTimer;

	private Project project;

	public void setUp() {
		project = createProject();
		project.setName("project");
		clock = new StubClock();
		projectTimer = new ProjectTimer(project.getName(), clock);
	}

	public void testShouldRetrieveTarget() {
		Timer target = projectTimer.getTargetTimer("target");
		assertNotNull(target);
		Timer target2 = projectTimer.getTargetTimer("target");
		assertSame(target, target2);
	}

	public void testShouldRetrieveTask() {
		Timer task = projectTimer.getTaskTimer("task");
		assertNotNull(task);
		Timer task2 = projectTimer.getTaskTimer("task");
		assertSame(task, task2);
	}

	public void testShouldReturnTargetSeriesMap() {
		projectTimer.getTargetTimer("target1").start();
		projectTimer.getTargetTimer("target1").finish();
		projectTimer.getTargetTimer("target2").start();
		projectTimer.getTargetTimer("target2").finish();
		SeriesMap seriesMap = projectTimer.toTargetSeriesMap();
		assertEquals(2, seriesMap.getNames().length);
	}

	public void testShouldReturnTaskSeriesMap() {
		projectTimer.getTaskTimer("task1").start();
		projectTimer.getTaskTimer("task1").finish();
		projectTimer.getTaskTimer("task2").start();
		projectTimer.getTaskTimer("task2").finish();
		projectTimer.getTaskTimer("task3").start();
		projectTimer.getTaskTimer("task3").finish();
		SeriesMap seriesMap = projectTimer.toTaskSeriesMap();
		assertEquals(3, seriesMap.getNames().length);
	}

	private Project createProject() {
		Project project = new Project();
		project.init();
		return project;
	}

}
