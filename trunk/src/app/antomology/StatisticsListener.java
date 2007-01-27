package antomology;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.SubBuildListener;

public class StatisticsListener implements SubBuildListener {

	private final Build build;

	public StatisticsListener() {
		this(new Build("main", new DefaultClock()));
	}

	public StatisticsListener(Build build) {
		this.build = build;
	}

	public void buildStarted(BuildEvent buildEvent) {
		build.start();
	}

	public void buildFinished(BuildEvent buildEvent) {
		build.finish();
		new StatisticsReport().print("Target Statistics", build
				.toTargetSeriesMap());
		new StatisticsReport()
				.print("Task Statistics", build.toTaskSeriesMap());
	}

	public void targetStarted(BuildEvent buildEvent) {
		String name = buildEvent.getTarget().getName();
		build.getTarget(name).start();
	}

	public void targetFinished(BuildEvent buildEvent) {
		String name = buildEvent.getTarget().getName();
		build.getTarget(name).finish();
	}

	public void taskStarted(BuildEvent buildEvent) {
		String name = buildEvent.getTask().getTaskName();
		build.getTask(name).start();
	}

	public void taskFinished(BuildEvent buildEvent) {
		String name = buildEvent.getTask().getTaskName();
		build.getTask(name).finish();
	}

	public void messageLogged(BuildEvent buildEvent) {
	}

	public long getBuildTime() {
		return build.getTime();
	}

	public void subBuildFinished(BuildEvent buildEvent) {
	}

	public void subBuildStarted(BuildEvent buildEvent) {

	}

}
