package com.alan.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Options {
    public static void sleep(int second) {
        try {
            Output.print("wait for(seconds): " + String.valueOf(second));
            TimeUnit.SECONDS.sleep(second);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String waitForInput() {
        String line = null;
        try {
            System.out.print("please input:");
            InputStream in = System.in;
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            line = br.readLine();
            Output.print(String.format("input:[%s]",line));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return line;
    }
}
