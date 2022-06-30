/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.alan.common.util.FilesBox;
import com.alan.common.util.StringBox;
import com.alan.common.web.tans.BaiduTranslator;

/**
 * @Description: srt字幕工具
 * @Author: Alan
 * @Date: 2021/3/6
 */
public class SubtitleBox implements SubtitleBoxInterface {
	List<SubtitleBody> subtitleBodies = new ArrayList<>();

	/**
	 * 初始化
	 *
	 * @param file
	 * @return
	 */
	public SubtitleBox init(String file) {
		subtitleBodies = read(file);
		return this;
	}

	public void addSrt(String file) {
		List<SubtitleBody> list = read(file);
		list.forEach(n -> {
			if (subtitleBodies.contains(n)) {
				n.getText().forEach(subtitleBodies.get(subtitleBodies.indexOf(n)).getText()::add);
			} else {
				subtitleBodies.add(n);
			}
		});
	}

	@Override
	public List<SubtitleBody> getAll() {
		ArrayList<String> subtitles = new ArrayList<>();
		return filters(subtitleBodies, true, true, false, 0, 0, 0);
	}

	@Override
	public List<SubtitleBody> getClip(double start, double end) {
		ArrayList<String> subtitles = new ArrayList<>();
		return filters(subtitleBodies, true, true, true, start, end, 0);
	}

	@Override
	public List<SubtitleBody> getEnglish() {
		ArrayList<String> subtitles = new ArrayList<>();
		return filters(subtitleBodies, false, true, false, 0, 0, 0);
	}

	@Override
	public List<SubtitleBody> getChinese() {
		ArrayList<String> subtitles = new ArrayList<>();
		return filters(subtitleBodies, true, false, false, 0, 0, 0);
	}

	public void forEach(Consumer<SubtitleBody> consumer) {
		subtitleBodies.forEach(consumer);
	}

	public List<SubtitleBody> read(String file) {
		List<String> reader = FilesBox.reader(file);
		List<SubtitleBody> subtitleBodies = new ArrayList<>();
		String timeFormat = "(\\d+):(\\d+):(\\d+),(\\d+)";
		Pattern num = Pattern.compile("^(\\d{1,4})$");
		Pattern time = Pattern.compile(String.format("%s.*-->.*%s", timeFormat, timeFormat));
		SubtitleBody subtitleBody = new SubtitleBody();
		for (String line : reader) {
			Matcher numMat = num.matcher(line);
			Matcher timeMat = time.matcher(line);
			boolean isNum = numMat.find();
			boolean isTime = timeMat.find();
			if (isNum) {
				subtitleBody = subtitleBody.clone();
				subtitleBodies.add(subtitleBody);
				subtitleBody.init();
				subtitleBody.setNum(Integer.parseInt(numMat.group(1)));
			}
			if (isTime) {
				int h = Integer.parseInt(timeMat.group(1));
				int m = Integer.parseInt(timeMat.group(2));
				int s = Integer.parseInt(timeMat.group(3));
				int ms = Integer.parseInt(timeMat.group(4));
				int h1 = Integer.parseInt(timeMat.group(5));
				int m1 = Integer.parseInt(timeMat.group(6));
				int s1 = Integer.parseInt(timeMat.group(7));
				int ms1 = Integer.parseInt(timeMat.group(8));
				double start = h * 60 * 60 + m * 60 + s + ms / 1000.0;
				double end = h1 * 60 * 60 + m1 * 60 + s1 + ms1 / 1000.0;
				subtitleBody.setStart(start);
				subtitleBody.setEnd(end);
			}
			if ((!isNum & !isTime)) {
				if (!line.equals("")) {
					subtitleBody.addText(line);
				}
			}
		}
		return subtitleBodies;
	}

	public SubtitleBody filter(SubtitleBody subtitleBody, boolean chinese, boolean english, boolean clip, double start,
							   double end, double delay) {
		if (clip) {
			if (subtitleBody.start < start || subtitleBody.end > end) {
				return null;
			}
		}
		if (delay != 0) {
			subtitleBody.setStart(subtitleBody.getStart() + delay);
			subtitleBody.setEnd(subtitleBody.getEnd() + delay);
		}
		List<String> text = subtitleBody.text;
		for (int i = text.size() - 1; i > -1; i--) {
			if (!chinese) {
				if (StringBox.checkChinese(text.get(i))) {
					text.remove(i);
				}
			}
			if (!english) {
				if (!StringBox.checkChinese(text.get(i))) {
					text.remove(i);
				}
			}
		}
		if (text.isEmpty()) {
			return null;
		}
		return subtitleBody;
	}

	public List<SubtitleBody> filters(List<SubtitleBody> bodies, boolean chinese, boolean english, boolean clip,
									  double start, double end, double delay) {
		ArrayList<SubtitleBody> newBodies = new ArrayList<>();
		for (SubtitleBody body : bodies) {
			SubtitleBody filter = filter(body, chinese, english, clip, start, end, delay);
			if (filter != null) {
				newBodies.add(filter);
			}
		}
		return newBodies;
	}

	public List<SubtitleBody> getByFilter(boolean chinese, boolean english, boolean clip, double start, double end,
										  double delay) {
		return filters(subtitleBodies, chinese, english, clip, start, end, delay);
	}

	public boolean write(List<SubtitleBody> subtitle, String file) {
		if (subtitle.isEmpty()) {
			return false;
		}
		List<String> collect = subtitle.stream().map(SubtitleBody::toBody).collect(Collectors.toList());
		FilesBox.writer(collect, file);
		return true;
	}

	public List<SubtitleBody> delay(double time) {
		for (SubtitleBody subtitleBody : subtitleBodies) {
			subtitleBody.setStart(subtitleBody.getStart() + time);
			subtitleBody.setEnd(subtitleBody.getEnd() + time);
		}
		return subtitleBodies;
	}

	public List<SubtitleBody> getSubtitleBodies() {
		return subtitleBodies;
	}

	public List<SubtitleBody> transSubtitles(List<SubtitleBody> subtitleBodies) {
		subtitleBodies.forEach(sub -> {
			if (sub.getText().size() == 1) {
				if (!StringBox.checkChinese(sub.getText().get(0))) {
					sub.getText().add(BaiduTranslator.translate(sub.getText().get(0), true));
				} else {
					sub.getText().add(BaiduTranslator.translate(sub.getText().get(0), false));
				}
			}
		});
		return subtitleBodies;
	}

	/**
	 * 翻译字幕
	 *
	 * @param subtitleBodies
	 * @param zh
	 * @return
	 */
	public List<SubtitleBody> transSubtitles(List<SubtitleBody> subtitleBodies, Boolean zh) {
		subtitleBodies.forEach(sub -> {
			AtomicReference<Integer> lan = new AtomicReference<>(); // all 0, zh 1, en 2
			// 检测中英文
			sub.getText().forEach(v -> {
				if (StringBox.checkChinese(v)) {
					lan.set(lan.get() == null ? 1 : lan.get() == 0 ? 0 : lan.get() == 1 ? 1 : 0);
				} else {
					lan.set(lan.get() == null ? 2 : lan.get() == 0 ? 0 : lan.get() == 1 ? 0 : 2);
				}
			});
			if (lan.get() == 1 && (zh == null || !zh)) {
				ArrayList<String> strings = new ArrayList<>();
				sub.getText().forEach(v -> {
					strings.add(BaiduTranslator.translate(v,false));
				});
				if (Boolean.FALSE.equals(zh)) {
					sub.setText(strings);
				} else {
					sub.getText().addAll(strings);
				}
			} else if (lan.get() == 2 && (zh ==null || zh)){
				ArrayList<String> strings = new ArrayList<>();
				sub.getText().forEach(v -> {
					strings.add(BaiduTranslator.translate(v,true));
				});
				if (Boolean.TRUE.equals(zh)) {
					sub.setText(strings);
				} else {
					sub.getText().addAll(strings);
				}
			}
		});
		return subtitleBodies;
	}

	public void setSubtitleBodies(List<SubtitleBody> subtitleBodies) {
		this.subtitleBodies = subtitleBodies;
	}
}
