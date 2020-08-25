package com.alan.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.text.Format;
import java.util.Date;

public class DateBox {

    public static String format(Date date, String fomat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fomat);
        String format = simpleDateFormat.format(date);
        return format;
    }

    public static Date getDate(long stamp) {
        Date date = new Date(stamp);
        return date;
    }
}
