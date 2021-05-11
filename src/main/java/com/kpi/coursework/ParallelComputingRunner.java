package com.kpi.coursework;

import java.io.File;

public class ParallelComputingRunner {
    public static final String SOURCE_FOLDER = "text_files";

    public static void main(String[] args) {
        File sourceFolder = new File(SOURCE_FOLDER);

        if (!sourceFolder.isDirectory() || sourceFolder.listFiles().length == 0) {
            System.out.println("There are no files for building inverted index!");
            return;
        }

        File[] subFolders = sourceFolder.listFiles();
        File[][] parts = new File[subFolders.length][];
        for (int i = 0; i < subFolders.length; i++) {
            parts[i] = subFolders[i].listFiles();
        }
    }
}
