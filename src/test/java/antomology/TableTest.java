package antomology;

import junit.framework.TestCase;

public class TableTest extends TestCase {

	public void testShouldCreateAnEmptyTableWithHeader() {
		String[] headers = new String[] { "1", "2" };
		Table table = new Table(headers, 0);
		assertEquals(1, table.rows());
		assertEquals(headers.length, table.columns());
	}

	public void testShouldCreateOneRowTableWithHeader() {
		String[] headers = new String[] { "1", "2" };
		Table table = new Table(headers, 1);
		assertEquals(2, table.rows());
		assertEquals(headers.length, table.columns());
	}

	public void testShouldCreateOneRowTableWithHeaderWithData() {
		String[] headers = new String[] { "1" };
		Table table = new Table(headers, 1);
		assertEquals(2, table.rows());
		assertEquals(headers.length, table.columns());
		String expected = "value";
		table.put(1, 0, expected);
		assertSame(expected, table.get(1, 0));
	}

	public void testShouldThrowArrayOutOfBoundsExceptionOnGet() {
		assertArrayIndexOutOfBoundsExceptionOnGet(new String[] { "1" }, -1, -1);
	}

	public void testShouldThrowArrayOutOfBoundsExceptionOnPut() {
		assertArrayIndexOutOfBoundsExceptionOnPut(new String[] { "1" }, -1, -1);
	}

	public void testShouldThrowArrayOutOfBoundsExceptionOnGetForNullHeader() {
		assertArrayIndexOutOfBoundsExceptionOnGet(null, 0, 0);
	}

	public void testShouldThrowArrayOutOfBoundsExceptionOnPutForNullHeader() {
		assertArrayIndexOutOfBoundsExceptionOnPut(null, 0, 0);
	}

	public void testShouldThrowArrayOutOfBoundsExceptionOnGetForEmptyHeader() {
		assertArrayIndexOutOfBoundsExceptionOnGet(new String[0], 0, 0);
	}

	public void testShouldThrowArrayOutOfBoundsExceptionOnPutForEmptyHeader() {
		assertArrayIndexOutOfBoundsExceptionOnPut(new String[0], 0, 0);
	}

	private void assertArrayIndexOutOfBoundsExceptionOnPut(String[] header,
			int x, int y) {
		try {
			Table table = new Table(header, 1);
			table.put(x, y, "value");
			fail("ArrayIndexOutOfBoundsException expected");
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}

	private void assertArrayIndexOutOfBoundsExceptionOnGet(String[] header,
			int x, int y) {
		try {
			Table table = new Table(header, 1);
			table.get(x, y);
			fail("ArrayIndexOutOfBoundsException expected");
		} catch (ArrayIndexOutOfBoundsException e) {

		}
	}

	public void testShouldReturnZeroMaxLengthsForZeroTableSize() {
		Table table = new Table(null, 0);
		int[] lengths = table.getMaxLengths();
		assertEquals(0, lengths.length);
	}

	public void testShouldReturnZeroMaxLengthsForLessThanZeroTableSize() {
		Table table = new Table(null, -1);
		int[] lengths = table.getMaxLengths();
		assertEquals(0, lengths.length);
	}

	public void testShouldReturnOneMaxLengthForZeroHeaderAndZeroBodyValue() {
		Table table = new Table(new String[] { "" }, 1);
		int[] lengths = table.getMaxLengths();
		assertEquals(1, lengths.length);
		assertEquals(0, lengths[0]);
	}

	public void testShouldReturnOneMaxLengthForZeroHeaderAndOneBodyValue() {
		Table table = new Table(new String[] { "" }, 1);
		table.put(1, 0, "1");
		int[] lengths = table.getMaxLengths();
		assertEquals(1, lengths.length);
		assertEquals(1, lengths[0]);
	}
	
	public void testShouldReturnOneMaxLengthForOneHeaderAndZeroBodyValue() {
		Table table = new Table(new String[] { "1" }, 1);
		int[] lengths = table.getMaxLengths();
		assertEquals(1, lengths.length);
		assertEquals(1, lengths[0]);
		table.put(1, 0, "");
		lengths = table.getMaxLengths();
		assertEquals(1, lengths.length);
		assertEquals(1, lengths[0]);
	}
	
	public void testShouldReturnTwoMaxLengthForTwoHeaderAndTwoBodyValue() {
		Table table = new Table(new String[] { "1", "22" }, 2);
		int[] lengths = table.getMaxLengths();
		table.put(1, 0, "22");
		table.put(1, 1, "1");
		table.put(2, 0, "333");
		table.put(2, 1, "");
		assertEquals(2, lengths.length);
		assertEquals(3, lengths[0]);
		assertEquals(2, lengths[1]);
	}

}
