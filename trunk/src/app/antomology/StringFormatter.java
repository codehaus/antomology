package antomology;

public class StringFormatter {
    public String center(String value, int fixedLength) {
        int spacesBeforeValue = calculateSpaceBeforeValue(value, fixedLength);
        return toSpaces(spacesBeforeValue) + value;
    }

    public String left(String value, int fixedLength) {
        return value + toSpaces(fixedLength - value.length() + 4);
    }

    private int calculateSpaceBeforeValue(String value, int fixedLength) {
        return fixedLength / 2 - value.length() / 2;
    }

    public String toSpaces(int size) {
        return toChars(' ', size);
    }

    public String toChars(char ch, int size) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < size; i++) {
            sb.append(ch);
        }
        return sb.toString();

    }

}
