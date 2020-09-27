package com.alan.util;

import java.util.List;

public class RunReceiver {
    private String command;
    private RunCmd runCmd;

    public void setCommand(String command) {
        this.command = command;
    }

    public void run() {
        runCmd = new RunCmd(command);
    }

    public List<String> getResult() {
        return runCmd.getResult();
    }

}
