package antomology;

public class StubClock implements Clock {

	private long currentTime;

	public long getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(long currentTime) {
		this.currentTime = currentTime;
	}

}
