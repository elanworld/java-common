package com.alan.util;

import java.util.List;

public abstract class RunBox {
    private String cmdLine;

    public void setCmdLine(String cmdLine) {
        this.cmdLine = cmdLine;
    }

    public RunBox run() {
        new RunCmd(cmdLine);
        return this;
    }

    public abstract List<String> getResult();
}
