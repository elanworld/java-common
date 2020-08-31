package com.alan.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class RunCmd {
    ArrayList<String> output = new ArrayList<String>();
    ArrayList<String> outError = new ArrayList<String>();
    private StreamReader streamOut;
    private StreamReader streamError;
    Process p;
    boolean wait = true;
    int timeout = 60;
    boolean print = true;

    public RunCmd(String command) {
        run(command);
    }

    public RunCmd(String command, int timeout, boolean wait, boolean print) {
        this.wait = wait;
        this.timeout = timeout;
        this.print = print;
        run(command);
    }

    public void run(String command) {
        try {
            Output.print("runing cmd: " + command);
            p = Runtime.getRuntime().exec(command);
            new KillCmd(p, timeout).start();
            streamOut = new StreamReader(p.getInputStream(),print);
            streamError = new StreamReader(p.getErrorStream(),print);
            if (wait) {
                streamOut.getResul();
                streamError.getResul();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getOutput() {
        return streamOut.getResul();
    }

    public ArrayList<String> getError() {
        return streamError.getResul();
    }
}

class StreamReader extends Thread {
    public Thread t;
    InputStream input;
    public ArrayList<String> outList = new ArrayList<>();
    boolean print;

    public StreamReader(InputStream in, boolean print) {
        this.print = print;
        input = in;
        t = new Thread(this, getClass().getName());
        t.start();
    }

    public ArrayList<String> getResul() {
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
