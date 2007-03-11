package antomology;

import junit.framework.TestCase;

public class DurationTest extends TestCase {

	public void testShouldReturnDifferenceOfFinishAndStartTimes() {
		Duration duration = new Duration();
		assertEquals(0, duration.getTime());
		duration.setStartTime(10);
		assertEquals(-10, duration.getTime());
		duration.setFinishTime(20);
		assertEquals(10, duration.getTime());
	}

}
