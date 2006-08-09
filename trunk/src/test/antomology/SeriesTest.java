package antomology;

import junit.framework.TestCase;

public class SeriesTest extends TestCase {

	public void testShouldReturnZeroAverageAndTotalTimeForZeroDurations() {
		Series series = new Series();
		assertEquals(0, series.getAverageTime());
		assertEquals(0, series.getTotalTime());
	}

	public void testShouldReturnSameAverageAndTotalTimeForOneDuration() {
		Series series = new Series();
		Duration duration = new Duration();
		series.add(duration);
		series.setFinishTime(10);
		assertEquals(1, series.size());
		assertEquals(1, series.getTimes().length);
		assertEquals(10, series.getTimes()[0]);
		assertEquals(10, series.getAverageTime());
		assertEquals(10, series.getTotalTime());
	}
	
	public void testShouldReturnSameAverageAndTotalTimeForThreeDurations() {
		Series series = new Series();
		series.add(new Duration());
		series.setFinishTime(10);
		series.add(new Duration());
		series.setFinishTime(20);
		series.add(new Duration());
		series.setFinishTime(30);
		assertEquals(3, series.size());
		assertEquals(3, series.getTimes().length);
		assertEquals(10, series.getTimes()[0]);
		assertEquals(20, series.getTimes()[1]);
		assertEquals(30, series.getTimes()[2]);
		assertEquals(20, series.getAverageTime());
		assertEquals(60, series.getTotalTime());
	}


}
