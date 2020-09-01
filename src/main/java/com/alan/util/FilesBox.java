package com.alan.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FilesBox {
    static boolean dirListWalk = false;
    static String[] dirListFilter = new String[20];
    static int fileGrowth = 0;

    public static String[] pathSplit(String path) {
        File file = new File(path);
        String name = file.getName();
        String parent = file.getParent();
        String[] split = name.split("\\.");
        String basename = split[0];
        String ext = "." + split[1];
        String[] paths = {parent, basename, ext, name};
        return paths;
    }

    public static String outFile(String inputPath) {
        String[] paths = pathSplit(inputPath);
        Path outPath = Paths.get(paths[0], paths[1] + "_out" + paths[2]);
        return outPath.toString();
    }

    public static String outFile(String inputPath, String addtion) {
        String[] paths = pathSplit(inputPath);
        Path outPath = Paths.get(paths[0], paths[1] + "_" + addtion + paths[2]);
        return outPath.toString();
    }

    public static String outExt(String inputPath, String ext) {
        String[] paths = pathSplit(inputPath);
        Path outPath = Paths.get(paths[0], paths[1] + "." + ext);
        return outPath.toString();
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
        mkdirOutPath(parent);
        return outPath.toString();
    }

    private static void mkdirOutPath(Path path) {
        if (!Files.exists(path)) {
            File file = new File(path.toString());
            file.mkdirs();
        }
    }


    public static ArrayList<String> dictoryList(String dir) {
        ArrayList<String> list = new ArrayList<String>();
        Path path = new File(dir).toPath();
        try {
            Stream<Path> pathStream;
            if (dirListWalk) {
                pathStream = Files.walk(path);
            } else {
                pathStream = Files.list(path);
            }
            Object[] objects = pathStream.toArray();
            for (Object object : objects) {
                String fileObject = object.toString();
                if (new File(fileObject).isFile())
                    for (String reg : dirListFilter) {
                        if (fileObject.matches(".*" + reg + ".*")) {
                            list.add(object.toString());
                            break;
                        }
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(list);
        return list;
    }

    public static ArrayList<String> dictoryListFilter(String dir, boolean walk, String... filter) {
        dirListWalk = walk;
        dirListFilter = filter;
        return dictoryList(dir);
    }


    public static void move(String source, String dir) {
        String[] paths = pathSplit(source);
        File file = new File(dir, paths[1] + paths[2]);
        new File(source).renameTo(file);
    }

    public static void deleteFiles(List<String> deleteFiles) {
        for (String file : deleteFiles) {
            try {
                Files.deleteIfExists(Paths.get(file));
            } catch (Exception e) {
                Output.print(e.getMessage());
            }
        }
    }

    public static String inputIfNotExists(String file) {
        if (Files.notExists(Paths.get(file))) {
            while (true){
                String input = StringContainer.input();
                if (Files.exists(Paths.get(input)))
                    return input;
            }
        }
        return file;
    }

}
