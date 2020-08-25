package com.alan.util;

import org.junit.Test;

import java.util.Date;

public class DateTest {
    @Test
    public void parse() {
        String yyyy = DateBox.format(new Date(), "yyyy");
        Output.print(yyyy);
    }

    @Test
    public void get() {
        long time = new Date().getTime();
        time += 600000;
        Date date = DateBox.getDate(time);
        Output.print(date, time);
    }
}
