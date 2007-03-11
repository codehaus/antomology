package antomology;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SeriesMap {
	protected Map map = new HashMap();

	public String[] getNames() {
		Set keySet = map.keySet();
		return (String[]) keySet.toArray(new String[keySet.size()]);
	}

	public Series get(String name) {
		return (Series) map.get(name);
	}

	public void put(String key, Series series) {
		map.put(key, series);
	}

}
