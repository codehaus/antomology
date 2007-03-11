package antomology;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;

public class BuildEventFactory {

	public BuildEvent createProjectBuildEvent() {
		return new BuildEvent(createProject());
	}

	public BuildEvent createTargetBuildEvent(String name) {
		Target target = new Target();
		target.setProject(createProject());
		target.setName(name);
		return new BuildEvent(target);
	}

	public BuildEvent createTaskBuildEvent(String name) {
		Task task = createProject().createTask(name);
		task.setProject(createProject());
		task.setOwningTarget(new Target());
		return new BuildEvent(task);
	}

	private Project createProject() {
		Project project = new Project();
		project.init();
		project.setName("project");
		return project;
	}

}
