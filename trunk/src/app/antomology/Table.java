package antomology;

public class Table {
	private final String[] header;

	private final String[][] output;

	private final int[] maxLengths;

	public Table(String[] header, int rows) {
		this.header = (header == null) ? new String[0] : header;
		int columnSize = (rows >= 0) ? rows + 1 : 1;
		output = new String[columnSize][this.header.length];
		output[0] = this.header;
		maxLengths = getHeaderLengths();
	}

	private int[] getHeaderLengths() {
		int[] lengths = new int[header.length];
		for (int i = 0; i < header.length; i++) {
			lengths[i] = header[i].length();
		}
		return lengths;
	}

	public int[] getMaxLengths() {
		return maxLengths;
	}

	public void put(int x, int y, String value) {
		maxLengths[y] = max(y, value.length());
		output[x][y] = value;
	}

	private int max(int column, int length) {
		int max = length;
		for (int i = 0; i < output.length; i++) {
			int valueLength = (output[i][column] != null) ? output[i][column]
					.length() : 0;
			max = Math.max(max, valueLength);
		}
		return max;
	}

	public String get(int x, int y) {
		return output[x][y];
	}

	public int rows() {
		return output.length;
	}

	public int columns() {
		return header.length;
	}
}