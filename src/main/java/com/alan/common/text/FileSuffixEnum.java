/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.text;

import java.util.Arrays;

public enum FileSuffixEnum {
	MP_4("mp4"), AVI("avi"), MKV("mkv"), WEBM("webm"), MP_3("mp3"), WAV("wav"), SRT("srt"),;

	private String suffix;
	FileSuffixEnum(String suffix) {
		this.suffix = suffix;
	}

	public String getSuffix() {
		return suffix;
	}

	public static String[] video() {
		FileSuffixEnum[] video = {MP_4, MKV, WEBM};
		return Arrays.stream(video).map(FileSuffixEnum::getSuffix).toArray(String[]::new);
	}

	public static String[] audio() {
		FileSuffixEnum[] video = {MP_3, WAV};
		return Arrays.stream(video).map(FileSuffixEnum::getSuffix).toArray(String[]::new);
	}
}
