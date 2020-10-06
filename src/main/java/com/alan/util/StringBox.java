package com.alan.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @apiNote : just using package of jdk
 */
public class StringBox {
    public static List<String> findGroup(List<String> lines, String... regex) {
        ArrayList<String> found = new ArrayList<>();
        for (String reg : regex) {
            Pattern compile = Pattern.compile(reg);
            for (String line : lines) {
                Matcher matcher = compile.matcher(line);
                if (matcher.find()) {
                    for (int i = 1; i <= matcher.groupCount(); i++) {
                        found.add(matcher.group(i));
                    }
                }
            }
        }
        return found;
    }

    public static String input() {
        String s = null;
        try {
            System.out.print("please input: ");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            s = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static int inputInteger() {
        while (true) {
            String input = input();
            Output.print(input);
            if (input.matches("(\\d+)")) {
                return Integer.parseInt(input);
            }

        }
    }

    public static boolean checkChinese(String word) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(word);
        if (m.find()) {
            return true;
        }
        return false;
    }
}

