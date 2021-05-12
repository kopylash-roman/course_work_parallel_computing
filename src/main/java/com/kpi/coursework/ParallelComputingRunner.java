package com.kpi.coursework;

import java.io.File;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class ParallelComputingRunner {
    public static final String SOURCE_FOLDER = "text_files";
    public static final int EXIT_CODE = -1;

    public static void main(String[] args) {
        File sourceFolder = new File(SOURCE_FOLDER);

        File[][] fileParts = prepareFileParts(sourceFolder);
        int threadNum = getThreadsNumFromUser();

        if (threadNum == EXIT_CODE || fileParts == null) {
            return;
        }

        InvertedIndexBuilder builder = new InvertedIndexBuilder();
        Map<String, Queue<String>> invertedIndex = builder.buildInvertedIndex(fileParts, threadNum);
    }

    private static int getThreadsNumFromUser() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the num of threads (from 1 to 100) or 0 to exit: ");

        int threadsNum;
        while (true) {
            String userInput = scan.next();
            if (userInput.matches("\\d+")) {
                if (Integer.parseInt(userInput) >= 0 && Integer.parseInt(userInput) <= 100) {
                    threadsNum = Integer.parseInt(userInput);
                    break;
                } else if (Integer.parseInt(userInput) == 0) {
                    System.out.println("Exiting...");
                    return -1;
                }
            }
            System.out.println("The userInput is incorrect! Try one more time.");
            System.out.println("(Enter the num of threads from 1 to 100 or 0 to exit)");
        }

        return threadsNum;
    }

    private static File[][] prepareFileParts(File sourceFolder) {
        if (!sourceFolder.isDirectory() || sourceFolder.listFiles().length == 0) {
            System.out.println("There are no files for building inverted index!");
            return null;
        }

        File[] subFolders = sourceFolder.listFiles();
        File[][] parts = new File[subFolders.length][];
        for (int i = 0; i < subFolders.length; i++) {
            parts[i] = subFolders[i].listFiles();
        }

        return parts;
    }
}