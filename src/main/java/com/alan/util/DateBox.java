package com.alan.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateBox {

	public static String format(Date date, String format) {
		if (format == null || format.equals("")) {
			format = "YYYY-MM-dd HH:mm:ss";
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String dateText = simpleDateFormat.format(date);
		return dateText;
	}

	public static Date getDate(long stamp) {
		Date date = new Date(stamp);
		return date;
	}

	public static Date getDate(String dateText, String dateFormat) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
			Date parse = simpleDateFormat.parse(dateText);
			return parse;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
