/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @Description: 实现功能
 * @Author: Alan
 * @Date: 2021/3/6
 */
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

	@Override
	public void run() {
		outList = getOutput(input);
	}

	public ArrayList<String> getOutput(InputStream in) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in, Charset.defaultCharset()));
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
