/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.web.tans;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5编码相关的类
 *
 * @author wangjingtao
 *
 */
public class MD5 {
	private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f'};

	/**
	 * 获得一个字符串的MD5值
	 *
	 * @param input
	 *            输入的字符串
	 * @return 输入字符串的MD5值
	 *
	 */
	public static String md5(String input) {
		if (input == null) {
			return null;
		}

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] inputByteArray = input.getBytes("utf-8");
			messageDigest.update(inputByteArray);
			byte[] resultByteArray = messageDigest.digest();
			return byteArrayToHex(resultByteArray);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 获取文件的MD5值
	 *
	 * @param file
	 * @return
	 */
	public static String md5(File file) {
		try {
			if (!file.isFile()) {
				System.err.println("文件" + file.getAbsolutePath() + "不存在或者不是文件");
				return null;
			}

			FileInputStream in = new FileInputStream(file);

			String result = md5(in);

			in.close();

			return result;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String md5(InputStream in) {

		try {
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");

			byte[] buffer = new byte[1024];
			int read = 0;
			while ((read = in.read(buffer)) != -1) {
				messagedigest.update(buffer, 0, read);
			}

			in.close();

			String result = byteArrayToHex(messagedigest.digest());

			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static String byteArrayToHex(byte[] byteArray) {
		char[] resultCharArray = new char[byteArray.length * 2];
		int index = 0;
		for (byte b : byteArray) {
			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
			resultCharArray[index++] = hexDigits[b & 0xf];
		}
		return new String(resultCharArray);

	}

}
