package antomology;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeFormatter {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");

	public String format(long time) {
		Calendar gmt = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		gmt.setTimeInMillis(time);
		DATE_FORMAT.setCalendar(gmt);
		return DATE_FORMAT.format(new Date(time));
	}
}