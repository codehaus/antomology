package antomology;

import java.util.Date;

import junit.framework.TestCase;

public class DefaultClockTest extends TestCase {

	public void testShouldCreateNewClockWithCurrentTime() {
		long expected = new Date().getTime();
		Clock clock = new DefaultClock();
		assertTrue(expected <= clock.getCurrentTime());
	}

}
