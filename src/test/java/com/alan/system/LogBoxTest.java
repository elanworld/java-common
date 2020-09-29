package com.alan.system;

import org.apache.log4j.Logger;
import org.junit.Test;


public class LogBoxTest {
    @Test
    public void test() {
        Logger logger = LogBox.getLog4j();
        Logger.getLogger("aabb").info("haha");
        logger.removeAllAppenders();
        logger.info("kk");
    }
}
