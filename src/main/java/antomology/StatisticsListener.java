package antomology;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.SubBuildListener;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

public class StatisticsListener implements SubBuildListener {

	private static final boolean BUILDEVENT_PROJECT_NAME_HAS_NULL_VALUE = true;

	protected ProjectTimerMap projectTimerMap = new ProjectTimerMap();

	private final Clock clock;

	private final StatisticsReport statisticsReport = new StatisticsReport();

	public StatisticsListener() {
		this(new DefaultClock());
	}

	public StatisticsListener(Clock clock) {
		this.clock = clock;
	}

	public void buildStarted(BuildEvent buildEvent) {
		if (BUILDEVENT_PROJECT_NAME_HAS_NULL_VALUE) {
			findInitialProjectTimer().start();
		}
	}

	public void buildFinished(BuildEvent buildEvent) {
		ProjectTimer projectTimer = findProjectTimer(buildEvent);
		updateDurationWithInitialProjectTimer(projectTimer);
		buildFinished(projectTimer);
		statisticsReport.print();
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
		ProjectTimer projectTimer = findProjectTimer(buildEvent);
		buildFinished(projectTimer);
	}

	private ProjectTimer findProjectTimer(BuildEvent buildEvent) {
		final Project project = buildEvent.getProject();
		return projectTimerMap.find(project, clock);
	}

	protected ProjectTimer findInitialProjectTimer() {
		return (ProjectTimer) projectTimerMap.find((String) null, clock);
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

	private void buildFinished(ProjectTimer projectTimer) {
		projectTimer.finish();
		statisticsReport.push(projectTimer);
	}

	private void updateDurationWithInitialProjectTimer(ProjectTimer projectTimer) {
		ProjectTimer rootProjectTimer = findInitialProjectTimer();
		final Duration duration = rootProjectTimer.getSeries().current();
		projectTimer.getSeries().add(duration);
	}
}
