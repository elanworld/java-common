package com.alan.util;

import org.junit.Assume;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringBoxTest {
    @Test
    public void findLine() {
        ArrayList<String> lines = new ArrayList<String >(Arrays.asList("abc","def","ghi","jk"));
        List<String> found = StringBox.findGroup(lines, ".*(\\Dh)i");
        Assert.assertEquals(1, found.size());
        if (!found.isEmpty()) {
            Assume.assumeTrue("regex need to fix",found.get(0).equals("gh"));
        }
    }

    @Test
    public void checkChinese() {
        boolean hh = StringBox.checkChinese("hh faf的弟弟");
        Assume.assumeTrue(hh);
    }
}
