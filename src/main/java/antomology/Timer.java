package antomology;

public class Timer {

	protected String name;

	protected final Series series;

	protected final Clock clock;

	public Timer(String name, Clock clock) {
		this.name = name;
		this.clock = clock;
		series = new Series();
	}

	public void start() {
		Duration duration = new Duration();
		duration.setStartTime(clock.getCurrentTime());
		series.add(duration);
	}

	public void finish() {
		series.setFinishTime(clock.getCurrentTime());
	}

	public String getName() {
		return name;
	}

	public long getTime() {
		return series.getTotalTime();
	}

	public Series getSeries() {
		return series;
	}

}
