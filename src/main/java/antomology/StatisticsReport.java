package antomology;

import java.util.Arrays;
import java.util.Stack;

public class StatisticsReport {

	private static final int IDX_NAME = 0;

	private static final int IDX_COUNT = 1;

	private static final int IDX_AVERAGE = 2;

	private static final int IDX_TOTAL = 3;

	private static final int IDX_PERCENTAGE = 4;

	private static final String[] HEADERS = new String[] { "name", "count",
			"average", "total", "%" };

	private static final StringFormatter FORMATTER = new StringFormatter();
	private static final TimeFormatter TIME_FORMATTER = new TimeFormatter();

	private final Stack stack = new Stack();

	public String create(String title, SeriesMap seriesMap) {
		String[] keys = seriesMap.getNames();
		Arrays.sort(keys);
		Table table = new Table(HEADERS, keys.length);

		long[] totalTimes = new long[table.rows() - 1];
		long runningTotalTime = 0;
		for (int i = 1; i < table.rows(); i++) {
			Series series = seriesMap.get(keys[i - 1]);
			table.put(i, IDX_NAME, keys[i - 1]);
			table.put(i, IDX_COUNT, String.valueOf(series.size()));
			table.put(i, IDX_AVERAGE, TIME_FORMATTER.format(series.getAverageTime()));
			table.put(i, IDX_TOTAL, TIME_FORMATTER.format(series.getTotalTime()));
			totalTimes[i - 1] = series.getTotalTime();
			runningTotalTime += series.getTotalTime();
		}

		updateTableWithPercentagesOfTotalTime(table, totalTimes,
				runningTotalTime);

		return toString(title, table);
	}
	
	private void updateTableWithPercentagesOfTotalTime(Table table,
			long[] totalTimes, long runningTotalTime) {
		for (int i = 0; i < totalTimes.length; i++) {
			long totalTime = totalTimes[i];
			long round = Math
					.round(100 * (double) totalTime / runningTotalTime);
			table.put(i + 1, IDX_PERCENTAGE, String.valueOf(round));
		}
	}

	private String toString(String title, Table table) {
		StringBuffer sb = new StringBuffer();
		int[] maxLengths = table.getMaxLengths();
		int titleBarLength = calculateFixedLength(maxLengths);
		sb.append(FORMATTER.center(title, titleBarLength));
		sb.append("\n\n");

		for (int i = 0; i < table.rows(); i++) {
			for (int j = 0; j < table.columns(); j++) {
				sb.append(FORMATTER.left(table.get(i, j), maxLengths[j]));
			}
			sb.append("\n");
			sb.append(createTitleBarIfFirstRow(titleBarLength, i));
		}

		sb.append("\n");
		return sb.toString();
	}

	private String createTitleBarIfFirstRow(int titleBarLength, int i) {
		if (i != 0) {
			return "";
		}
		return FORMATTER.toChars('-', titleBarLength) + "\n";
	}

	private int calculateFixedLength(int[] maxLengths) {
		int fixedLength = 0;
		for (int i = 0; i < maxLengths.length; i++) {
			fixedLength += maxLengths[i] + 4;
		}
		return fixedLength;
	}

	public void print(ProjectTimer projectTimer) {
		create("Target Statistics", projectTimer.toTargetSeriesMap());
		create("Task Statistics", projectTimer.toTaskSeriesMap());
	}

	public void push(ProjectTimer projectTimer) {
		stack.push(projectTimer);
	}

	public void print() {
		SeriesMap projectSeriesMap = new SeriesMap();
		StringBuffer sb = new StringBuffer();
		while (!stack.isEmpty()) {
			ProjectTimer projectTimer = (ProjectTimer) stack.pop();
			projectSeriesMap.put(projectTimer.getName(), projectTimer
					.getSeries());
			sb.append(createTargetStatistics(projectTimer));
			sb.append("\n");
			sb.append(createTaskStatistics(projectTimer));
			sb.append("\n");
		}
		System.out.println();
		System.out.println(create("Project Statistics", projectSeriesMap));
		System.out.println(sb.toString());
	}

	private String createTaskStatistics(ProjectTimer projectTimer) {
		return create("Task Statistics - " + projectTimer.getName(),
				projectTimer.toTaskSeriesMap());
	}

	private String createTargetStatistics(ProjectTimer projectTimer) {
		return create("Target Statistics - " + projectTimer.getName(),
				projectTimer.toTargetSeriesMap());
	}
}
