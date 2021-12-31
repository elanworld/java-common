package com.alan.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class FilesBoxTest {
    @Test
    public void print() {
        Output.print("a", "b");
    }

    @Test
    public void outDirFile() {
        String outDirFile = FilesBox.outDirFile("/a/b/c.d");
        Assert.assertEquals(new File("/a/b/c/c_1.d").getAbsolutePath(), new File(outDirFile).getAbsolutePath());
    }

    @Test
    public void pathSplit() {
        String[] strings = FilesBox.pathSplit("/a/b/c.d");
        Assert.assertEquals(new File("/a/b").getAbsolutePath(),new File(strings[0]).getAbsolutePath());
        Assert.assertEquals("c",strings[1]);
        Assert.assertEquals(".d",strings[2]);
    }

}
