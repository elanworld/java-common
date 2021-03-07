package com.alan.util;

import static com.alan.util.StringBox.findGroup;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesBox {
	static int fileGrowth = 0;

	public static String[] pathSplit(String path) {
		File file = new File(path);
		String name = file.getName();
		String parent = file.getParent();
		String[] split = name.split("\\.");
		String ext = "";
		String basename = "";
		if (split.length >= 2) {
			ext = "." + split[1];
		}
		if (split.length >= 1) {
			basename = split[0];
		}
		String[] paths = {parent, basename, ext, name};
		return paths;
	}

	public static String outFile(String inputPath, String add) {
		String[] paths = pathSplit(inputPath);
		Path outPath = Paths.get(paths[0], paths[1] + "_" + add + paths[2]);
		return outPath.toString();
	}

	public static String outExt(String inputPath, String ext) {
		String[] paths = pathSplit(inputPath);
		Path outPath = Paths.get(paths[0], paths[1] + "." + ext);
		return outPath.toString();
	}

	public static String outBasename(String inputPath) {
		String[] strings = pathSplit(inputPath);
		return strings[1];
	}

	public static boolean regexFiles(String dir, String regex) {
		boolean got = false;
		List<String> strings = directoryList(dir);
		List<String> found = findGroup(strings, regex);
		if (found.size() > 0) {
			got = true;
		}
		return got;
	}

	/**
	 * generate new filenames time by time
	 *
	 * @param inputPath
	 * @return
	 */
	public static String outDirFile(String inputPath) {
		fileGrowth += 1;
		String[] paths = pathSplit(inputPath);
		Path outPath = Paths.get(paths[0], "out", paths[1], paths[1] + "_" + String.valueOf(fileGrowth) + paths[2]);
		Path parent = outPath.getParent();
		if (!Files.exists(parent)) {
			File file = new File(parent.toString());
			file.mkdirs();
		}
		return outPath.toString();
	}

	public static List<String> directoryList(String dir) {
		return directoryListFilter(dir, false);
	}

	public static List<String> directoryListFilter(String dir, boolean walk, String... filters) {

		ArrayList<String> list = new ArrayList<>();
		File fileDir = new File(dir);
		if (!fileDir.isDirectory()) {
			return list;
		}
		Path path = fileDir.toPath();
		try {
			Stream<Path> pathStream;
			if (walk) {
				pathStream = Files.walk(path);
			} else {
				pathStream = Files.list(path);
			}
			List<String> paths = pathStream.map(Path::toString).collect(Collectors.toList());
			for (Object p : paths) {
				String fileObject = p.toString();
				if (new File(fileObject).isFile()) {
					if (filters.length == 0) {
						list.add(p.toString());
					} else {
						for (String reg : filters) {
							if (fileObject.matches(".*" + reg + ".*")) {
								list.add(p.toString());
								break;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void move(String source, String dir) {
		String[] paths = pathSplit(source);
		File file = new File(dir, paths[1] + paths[2]);
		new File(source).renameTo(file);
	}

	public static void deleteFiles(String deleteFile) {
		try {
			Files.deleteIfExists(Paths.get(deleteFile));
		} catch (Exception e) {
			Output.print(e.getMessage());
		}
	}

	public static String inputIfNotExists(String file) {
		if (Files.notExists(Paths.get(file))) {
			while (true) {
				String input = StringBox.input();
				if (Files.exists(Paths.get(input))) {
					return input;
				}
			}
		}
		return file;
	}

	public static boolean writer(String text, String fileName) {
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.write(text);
			fileWriter.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static boolean writer(List<String> text, String fileName) {
		String join = String.join("\n", text);
		return writer(join, fileName);
	}

	public static List<String> reader(String fileName) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

	public static Object readObject(String file) {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
			Object object = objectInputStream.readObject();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeObject(Object object, String file) {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
			objectOutputStream.writeObject(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
