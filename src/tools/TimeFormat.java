package tools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeFormat {
	public static Timestamp date2TimeStamp(String date_str) {
		String format1 = "yy-MM-dd HH:mm:ss";
		String format2 = "yy-MM-dd";
		Timestamp timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format1);
			timestamp = new Timestamp(sdf.parse(date_str).getTime());
		} catch (Exception e) {
			SimpleDateFormat sdf = new SimpleDateFormat(format2);
			try {
				timestamp = new Timestamp(sdf.parse(date_str).getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return timestamp;
	}
}
