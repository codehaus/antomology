package antomology;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.SubBuildListener;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

public class StatisticsListener implements SubBuildListener {

	protected ProjectTimerMap projectTimerMap = new ProjectTimerMap();

	private final Clock clock;

	public StatisticsListener() {
		this(new DefaultClock());
	}

	public StatisticsListener(Clock clock) {
		this.clock = clock;
	}

	public void buildStarted(BuildEvent buildEvent) {
		findProjectTimer(buildEvent).start();
	}

	public void buildFinished(BuildEvent buildEvent) {
		ProjectTimer projectTimer = findProjectTimer(buildEvent);
		projectTimer.finish();
		new StatisticsReport().print("Target Statistics", projectTimer
				.toTargetSeriesMap());
		new StatisticsReport().print("Task Statistics", projectTimer
				.toTaskSeriesMap());
	}

	public void targetStarted(BuildEvent buildEvent) {
		findTargetTimer(buildEvent).start();
	}

	public void targetFinished(BuildEvent buildEvent) {
		findTargetTimer(buildEvent).finish();
	}

	public void taskStarted(BuildEvent buildEvent) {
		findTaskTimer(buildEvent).start();
	}

	public void taskFinished(BuildEvent buildEvent) {
		findTaskTimer(buildEvent).finish();
	}

	public void messageLogged(BuildEvent buildEvent) {
	}

	public void subBuildStarted(BuildEvent buildEvent) {
		findProjectTimer(buildEvent).start();
	}

	public void subBuildFinished(BuildEvent buildEvent) {
		findProjectTimer(buildEvent).finish();
	}

	private ProjectTimer findProjectTimer(BuildEvent buildEvent) {
		return projectTimerMap.find(buildEvent.getProject(), clock);
	}

	private Timer findTargetTimer(BuildEvent buildEvent) {
		ProjectTimer projectTimer = findProjectTimer(buildEvent);
		final Target target = buildEvent.getTarget();
		String name = target.getName();
		return projectTimer.getTargetTimer(name);
	}

	private Timer findTaskTimer(BuildEvent buildEvent) {
		ProjectTimer projectTimer = findProjectTimer(buildEvent);
		final Task task = buildEvent.getTask();
		String name = task.getTaskName();
		return projectTimer.getTaskTimer(name);
	}

}
