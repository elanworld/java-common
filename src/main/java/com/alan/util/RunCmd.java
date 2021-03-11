package com.alan.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RunCmd implements RunCmdInterface {
	private StreamReader streamOut;
	private StreamReader streamError;
	private Process process;
	private File envDir = null;

	protected String command;
	protected boolean wait = true;
	protected Duration timeout = Duration.ofMinutes(1L);
	protected boolean isPrint = true;

	public RunCmd() {
	}

	public RunCmd(String command) {
		this.command = command;
		run(command);
	}

	public RunCmd(String command, int timeout, boolean wait, boolean isPrint) {
		this.command = command;
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
	public void run() {
		Output.print("running cmd: " + this.command);
		try {
			process = Runtime.getRuntime().exec(this.command, null, envDir);
			new KillCmd(process, timeout).start();
			streamOut = new StreamReader(process.getInputStream(), isPrint);
			streamError = new StreamReader(process.getErrorStream(), isPrint);
			if (wait) {
				streamOut.getResult();
				streamError.getResult();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getResult() {
		ArrayList<String> output = getOutput();
		output.addAll(getError());
		return output;
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

	public void run(String command) {
		this.command = command;
	}

	public void setEnvDir(String envDir) {
		this.envDir = new File(envDir);
	}

	public void setTimeout(Duration timeout) {
		this.timeout = timeout;
	}

	public void setWait(boolean wait) {
		this.wait = wait;
	}

	public void setPrint(boolean print) {
		isPrint = print;
	}
}
