/*
 * Copyright (c) 2021.
 * author:Alan
 * All rights reserved.
 */

package com.alan.common.text;

import java.util.List;

public interface SubtitleBoxInterface {
	List<SubtitleBody> getClip(double start, double end);
	List<SubtitleBody> getAll();
	List<SubtitleBody> getEnglish();
	List<SubtitleBody> getChinese();
}
