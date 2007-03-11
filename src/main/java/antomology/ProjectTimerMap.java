package antomology;

import org.apache.tools.ant.Project;

public class ProjectTimerMap extends TimerMap {

	public ProjectTimer get(Project project) {
		return (ProjectTimer) get(project.getName());
	}

	public ProjectTimer find(Project project, Clock clock) {
		return (ProjectTimer) find(project.getName(), clock);
	}

	protected Timer createTimer(String name, Clock clock) {
		return new ProjectTimer(name, clock);
	}

}
