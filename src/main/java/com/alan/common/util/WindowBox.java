/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.util;

import java.awt.*;
import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.*;

import com.sun.javafx.application.PlatformImpl;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 * @Description: 实现功能
 * @Author: Alan
 * @Date: 2021/5/7
 */
public class WindowBox {

	public static JFrame commonFrame() {
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(size.width / 2, size.width * 3 / 8);
		return frame;
	}

	public static String chooseFile() {
		AtomicReference<File> file = new AtomicReference<>();
		PlatformImpl.startup(() -> {
			FileChooser fileChooser = new FileChooser();
			file.set(fileChooser.showOpenDialog(null));
		});
		while (file.get() == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return file.get() == null ? null : file.get().getAbsolutePath();
	}

	public static String chooseDirectory() {
		AtomicReference<File> file = new AtomicReference<>();
		PlatformImpl.startup(() -> {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			file.set(directoryChooser.showDialog(null));
		});
		while (file.get() == null) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return file.get() == null ? null : file.get().getAbsolutePath();
	}

}
