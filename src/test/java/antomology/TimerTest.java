package antomology;

import junit.framework.TestCase;

public class TimerTest extends TestCase {
	private StubClock clock;

	private Timer timer;

	public void setUp() {
		clock = new StubClock();
		timer = new Timer("element", clock);
	}

	public void testShouldStartAndStopBuildElementTime() {

		clock.setCurrentTime(10);
		assertEquals("element", timer.getName());
		timer.start();
		clock.setCurrentTime(20);
		timer.finish();
		assertEquals(10, timer.getTime());
	}

}
