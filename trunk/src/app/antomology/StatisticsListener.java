package antomology;

import java.util.Date;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.SubBuildListener;

public class StatisticsListener implements SubBuildListener {

	private final Duration buildDuration;

	private final SeriesMap targetSeriesMap;

	private final SeriesMap taskSeriesMap;

	public StatisticsListener() {
		this(new Duration(), new SeriesMap(), new SeriesMap());
	}

	public StatisticsListener(Duration buildDuration,
			SeriesMap targetSeriesMap, SeriesMap taskSeriesMap) {
		this.buildDuration = buildDuration;
		this.targetSeriesMap = targetSeriesMap;
		this.taskSeriesMap = taskSeriesMap;
	}

	public void buildStarted(BuildEvent buildEvent) {
		buildDuration.setStartTime(currentTime());
	}

	public void buildFinished(BuildEvent buildEvent) {
		buildDuration.setFinishTime(currentTime());
		new StatisticsReport().print("Target Statistics", targetSeriesMap);
		new StatisticsReport().print("Task Statistics", taskSeriesMap);
	}

	public void targetStarted(BuildEvent buildEvent) {
		String name = buildEvent.getTarget().getName();
		setStartTimeToCurrentTime(targetSeriesMap, name);
	}

	public void targetFinished(BuildEvent buildEvent) {
		String name = buildEvent.getTarget().getName();
		setFinishTimeToCurrentTime(targetSeriesMap, name);
	}

	public void taskStarted(BuildEvent buildEvent) {
		String name = buildEvent.getTask().getTaskName();
		setStartTimeToCurrentTime(taskSeriesMap, name);
	}

	public void taskFinished(BuildEvent buildEvent) {
		String name = buildEvent.getTask().getTaskName();
		setFinishTimeToCurrentTime(taskSeriesMap, name);
	}

	public void messageLogged(BuildEvent buildEvent) {
	}

	protected long currentTime() {
		return new Date().getTime();
	}

	public long getBuildTime() {
		return buildDuration.getTime();
	}

	private void setStartTimeToCurrentTime(SeriesMap map, String name) {
		Duration duration = new Duration();
		duration.setStartTime(currentTime());
		map.findSeries(name).add(duration);
	}

	private void setFinishTimeToCurrentTime(SeriesMap map, String name) {
		Series series = map.findSeries(name);
		series.setFinishTime(currentTime());
	}

	public void subBuildFinished(BuildEvent arg0) {
		
	}

	public void subBuildStarted(BuildEvent arg0) {
		
	}

}
