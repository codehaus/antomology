package antomology;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Series {

	private Stack stack = new Stack();

	private List list = new ArrayList();

	public void add(Duration duration) {
		list.add(duration);
		stack.push(duration);
	}

	public void setFinishTime(long time) {
		Duration duration = (Duration) stack.pop();
		duration.setFinishTime(time);
	}

	public long[] getTimes() {
		long[] times = new long[list.size()];
		for (int i = 0; i < times.length; i++) {
			times[i] = ((Duration) list.get(i)).getTime();
		}
		return times;
	}

	public long getTotalTime() {
		long time = 0;
		for (int i = 0; i < list.size(); i++) {
			time += ((Duration) list.get(i)).getTime();
		}
		return time;
	}

	public long getAverageTime() {
		if (list.size() == 0) {
			return 0;
		}
		return getTotalTime() / list.size();
	}

	public int size() {
		return list.size();
	}
}
