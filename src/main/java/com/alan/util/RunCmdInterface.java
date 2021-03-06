package com.alan.util;

import java.util.List;

public interface RunCmdInterface {
	void run(String command);
	List<String> getResult();
	void input(String input);

}
