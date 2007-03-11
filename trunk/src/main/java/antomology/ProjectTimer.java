package antomology;

public class ProjectTimer extends Timer {

	private final TimerMap targetMap;

	private final TimerMap taskMap;

	public ProjectTimer(String name, Clock clock) {
		super(name, clock);
		targetMap = new TimerMap();
		taskMap = new TimerMap();
	}

	public Timer getTargetTimer(String name) {
		return targetMap.find(name, clock);
	}

	public Timer getTaskTimer(String name) {
		return taskMap.find(name, clock);
	}

	public SeriesMap toTargetSeriesMap() {
		return targetMap.toSeriesMap();
	}

	public SeriesMap toTaskSeriesMap() {
		return taskMap.toSeriesMap();
	}

}
