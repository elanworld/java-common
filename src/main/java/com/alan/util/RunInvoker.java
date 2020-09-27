package com.alan.util;


public class RunInvoker {
    private RunCommand runCommand;

    public RunInvoker(RunCommand runCommand) {
        this.runCommand = runCommand;
    }

    public void setRunCommand(RunCommand runCommand) {
        this.runCommand = runCommand;
    }

    public void call() {
        this.runCommand.execute();
    }
}
