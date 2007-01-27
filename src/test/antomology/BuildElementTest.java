package antomology;

import junit.framework.TestCase;

public class BuildElementTest extends TestCase {
	private StubClock clock;

	private BuildElement buildElement;

	public void setUp() {
		clock = new StubClock();
		buildElement = new BuildElement("element", clock);
	}

	public void testShouldStartAndStopBuildElementTime() {

		clock.setCurrentTime(10);
		assertEquals("element", buildElement.getName());
		buildElement.start();
		clock.setCurrentTime(20);
		buildElement.finish();
		assertEquals(10, buildElement.getTime());
	}

}
