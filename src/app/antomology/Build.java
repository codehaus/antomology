package antomology;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Build extends BuildElement {

	private final Map targetMap;

	private final Map taskMap;

	public Build(String name, Clock clock) {
		super(name, clock);
		targetMap = new HashMap();
		taskMap = new HashMap();
	}

	public BuildElement getTarget(String name) {
		return findBuildElement(targetMap, name);
	}

	public BuildElement getTask(String name) {
		return findBuildElement(taskMap, name);
	}

	private BuildElement findBuildElement(Map buildElementMap, String name) {
		BuildElement buildElement = (BuildElement) buildElementMap.get(name);
		if (buildElement == null) {
			buildElement = new BuildElement(name, clock);
			buildElementMap.put(name, buildElement);
		}
		return buildElement;
	}

	public SeriesMap toTargetSeriesMap() {
		return toSeriesMap(targetMap);
	}

	public SeriesMap toTaskSeriesMap() {
		return toSeriesMap(taskMap);
	}

	private SeriesMap toSeriesMap(Map map) {
		SeriesMap seriesMap = new SeriesMap();
		Iterator keyIterator = map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			BuildElement buildElement = (BuildElement) map.get(key);
			seriesMap.map.put(key, buildElement.getSeries());
		}
		return seriesMap;
	}

}
