package antomology;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TimerMap {

	protected final Map map = new HashMap();

	public Timer get(String name) {
		return (Timer) map.get(name);
	}

	public Timer find(String name, Clock clock) {
		Timer timer = (Timer) map.get(name);
		if (timer == null) {
			timer = createTimer(name, clock);
			map.put(name, timer);
		}
		return timer;
	}

	protected Timer createTimer(String name, Clock clock) {
		return new Timer(name, clock);
	}

	public SeriesMap toSeriesMap() {
		SeriesMap seriesMap = new SeriesMap();
		Iterator keyIterator = map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			Timer timer = (Timer) map.get(key);
			seriesMap.put(key, timer.getSeries());
		}
		return seriesMap;
	}

}
