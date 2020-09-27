package com.alan.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RunCmd implements RunCmdInterface {
    private String cmdline;
    private StreamReader streamOut;
    private StreamReader streamError;
    Process process;

    boolean wait = true;
    int timeout = 60;
    boolean print = true;

    public RunCmd(String command) {
        this.cmdline = command;
        run(command);
    }

    public RunCmd(String command, int timeout, boolean wait, boolean print) {
        this.cmdline = command;
        this.wait = wait;
        this.timeout = timeout;
        this.print = print;
        run(command);
    }

    public ArrayList<String> getOutput() {
        return streamOut.getResult();
    }

    public ArrayList<String> getError() {
        return streamError.getResult();
    }

    public Process getProcess() {
        return process;
    }

    @Override
    public List<String> getResult() {
        ArrayList<String> output = getOutput();
        output.addAll(getError());
        return output;
    }

    @Override
    public void run(String command) {
        try {
            Output.print("running cmd: " + command);
            process = Runtime.getRuntime().exec(command);
            new KillCmd(process, timeout).start();
            streamOut = new StreamReader(process.getInputStream(), print);
            streamError = new StreamReader(process.getErrorStream(), print);
            if (wait) {
                streamOut.getResult();
                streamError.getResult();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void input(String input) {
        OutputStream outputStream = process.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        try {
            outputStreamWriter.write(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class StreamReader extends Thread {
    public Thread t;
    InputStream input;
    public ArrayList<String> outList = new ArrayList<String>();
    boolean print;

    public StreamReader(InputStream in, boolean print) {
        this.print = print;
        input = in;
        t = new Thread(this, getClass().getName());
        t.start();
    }

    public ArrayList<String> getResult() {
        try {
            while (true) {
                Thread.sleep(1000);
                if (!t.isAlive()) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outList;
    }


    public void run() {
        outList = getOutput(input);
    }

    public ArrayList<String> getOutput(InputStream in) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                if (print) {
                    Output.print(line);
                }
                list.add(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

class KillCmd extends Thread {
    Thread thread;
    private Process process;
    int time;

    public KillCmd(Process p, int timeout) {
        process = p;
        time = timeout;
        thread = new Thread(this);
        thread.start();

    }

    public void run() {
        try {
            long start = new Date().getTime();
            while (true) {
                Thread.sleep(1000);
                long duration = new Date().getTime() - start;
                boolean timeout = duration > 1000 * time;
                if (process.isAlive() && timeout) {
                    Output.print("destroy the process: timeout " + time);
                    process.destroy();
                    break;
                } else if (!process.isAlive()) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
