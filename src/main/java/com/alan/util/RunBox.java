package com.alan.util;

import java.util.List;

public abstract class RunBox {
    private String cmdLine;

    public void setCmdLine(String cmdLine) {
        this.cmdLine = cmdLine;
    }

    public void run() {
        new RunCmd(cmdLine);
    }

    public abstract List<String> getResult();
}
