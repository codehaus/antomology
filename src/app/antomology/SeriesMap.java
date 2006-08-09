package antomology;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SeriesMap {
    private Map map = new HashMap();

    public String[] getNames() {
        Set keySet = map.keySet();
        return (String[]) keySet.toArray(new String[keySet.size()]);
    }

    public Series getSeries(String name) {
        return (Series) map.get(name);
    }

    public Series findSeries(String name) {
        if (!map.containsKey(name)) {
            map.put(name, new Series());
        }
        return (Series) map.get(name);
    }

}
