package com.alan.util;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RunCmd implements RunCmdInterface {
	private String cmdline;
	private StreamReader streamOut;
	private StreamReader streamError;
	private Process process;
	private File envDir = null;

	boolean wait = true;
	Duration timeout = Duration.ofMinutes(1L);
	boolean isPrint = true;

	public RunCmd() {
	}

	public RunCmd(String command) {
		this.cmdline = command;
		run(command);
	}

	public RunCmd(String command, int timeout, boolean wait, boolean isPrint) {
		this.cmdline = command;
		this.wait = wait;
		this.timeout = Duration.ofSeconds(timeout);
		this.isPrint = isPrint;
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
			process = Runtime.getRuntime().exec(command, null, envDir);
			new KillCmd(process, timeout).start();
			streamOut = new StreamReader(process.getInputStream(), isPrint);
			streamError = new StreamReader(process.getErrorStream(), isPrint);
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

	public void setEnvDir(String envDir) {
		this.envDir = new File(envDir);
	}

	public void setTimeout(Duration timeout) {
		this.timeout = timeout;
	}
}
