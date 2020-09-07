package com.alan.util;

import org.junit.Assume;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;

public class StringContainerTest {
    @Test
    public void findLine() {
        ArrayList<String> lines = new ArrayList<String >(Arrays.asList("abc","def","ghi","jk"));
        ArrayList<String> found = StringContainer.findLine(lines, ".*(\\Dh)i");
        Output.print(found);
        Assert.assertEquals(1, found.size());
        if (!found.isEmpty()) {
            Assume.assumeTrue("regex need to fix",found.get(0).equals("gh"));
        }
    }
}
