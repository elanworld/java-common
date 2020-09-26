package com.alan.system;

import org.apache.log4j.Logger;

public class LogBox {
    private final static Logger logger = Logger.getLogger(LogBox.class.getName());

    public synchronized static Logger getLogger() {
        return logger;
    }
}
