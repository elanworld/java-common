/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.util;

import java.time.Duration;
import java.util.Date;

/**
 * @Description: 实现功能
 * @Author: Alan
 * @Date: 2021/3/6
 */
class KillCmd extends Thread {
	Thread thread;
	private Process process;
	Duration time;

	public KillCmd(Process p, Duration timeout) {
		process = p;
		time = timeout;
		thread = new Thread(this);
		thread.start();

	}

	@Override
    public void run() {
		try {
			long start = new Date().getTime();
			while (true) {
				Thread.sleep(1000);
				long duration = new Date().getTime() - start;
				boolean timeout = duration > time.getSeconds() * 1000;
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
