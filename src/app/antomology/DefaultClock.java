package antomology;

import java.util.Date;

public class DefaultClock implements Clock {

	public long getCurrentTime() {
		return new Date().getTime();
	}

}
