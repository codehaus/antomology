package antomology;

import junit.framework.TestCase;

public class TimeFormatterTest extends TestCase {
	
	public void testShouldFormatTime() {
		TimeFormatter formatter = new TimeFormatter();
		assertEquals("00:00:00.100", formatter.format(100));
	}

}
