package antomology;

import org.apache.tools.ant.Project;

import junit.framework.TestCase;

public class BuildTest extends TestCase {

	private StubClock clock;

	private Build build;

	private Project project;

	public void setUp() {
		project = createProject();
		project.setName("build");
		clock = new StubClock();
		build = new Build(project.getName(), clock);
	}

	public void testShouldRetrieveTarget() {
		BuildElement target = build.getTarget("target");
		assertNotNull(target);
		BuildElement target2 = build.getTarget("target");
		assertSame(target, target2);
	}

	public void testShouldRetrieveTask() {
		BuildElement task = build.getTask("task");
		assertNotNull(task);
		BuildElement task2 = build.getTask("task");
		assertSame(task, task2);
	}

	public void testShouldReturnTargetSeriesMap() {
		build.getTarget("target1").start();
		build.getTarget("target1").finish();
		build.getTarget("target2").start();
		build.getTarget("target2").finish();
		SeriesMap seriesMap = build.toTargetSeriesMap();
		assertEquals(2, seriesMap.getNames().length);
	}

	public void testShouldReturnTaskSeriesMap() {
		build.getTask("task1").start();
		build.getTask("task1").finish();
		build.getTask("task2").start();
		build.getTask("task2").finish();
		build.getTask("task3").start();
		build.getTask("task3").finish();
		SeriesMap seriesMap = build.toTaskSeriesMap();
		assertEquals(3, seriesMap.getNames().length);
	}

	private Project createProject() {
		Project project = new Project();
		project.init();
		return project;
	}

}
