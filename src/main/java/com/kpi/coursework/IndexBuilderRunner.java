package com.kpi.coursework;

import java.io.File;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class IndexBuilderRunner {
    private static final String SOURCE_FOLDER = "text_files";
    private static final int EXIT_CODE = -1;
    private static final InvertedIndexBuilder BUILDER = new InvertedIndexBuilder();
    private static final int INDEX_USERS_NUM = 10;

    public static void main(String[] args) {
        File sourceFolder = new File(SOURCE_FOLDER);

        File[][] fileArrays = prepareFileArrays(sourceFolder);
        int threadsNum = getThreadsNumFromUser();

        if (threadsNum == EXIT_CODE || fileArrays == null) {
            return;
        }

        long startTime = System.currentTimeMillis();

        Map<String, Queue<String>> invertedIndex = BUILDER.buildInvertedIndex(fileArrays, threadsNum);

        long buildingTime = System.currentTimeMillis() - startTime;

        System.out.println("It took " + buildingTime + " ms to build the index with " + threadsNum + " thread(s)");

        InvertedIndexHelper.writeInvertedIndexToFile(invertedIndex, threadsNum);

        System.out.println("\nStart using the index...");
        IndexUserThread[] indexUsers = new IndexUserThread[INDEX_USERS_NUM];
        for (int i = 0; i < INDEX_USERS_NUM; i++) {
            indexUsers[i] = new IndexUserThread(invertedIndex, i);
            indexUsers[i].start();
        }

        try {
            for (IndexUserThread indexUser : indexUsers) {
                indexUser.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("Using of inverted index is finished!");
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

    private static File[][] prepareFileArrays(File sourceFolder) {
        if (!sourceFolder.isDirectory() || sourceFolder.listFiles().length == 0) {
            System.out.println("There are no files for building inverted index!");
            return null;
        }

        File[] subFolders = sourceFolder.listFiles();
        File[][] fileArrays = new File[subFolders.length][];
        for (int i = 0; i < subFolders.length; i++) {
            fileArrays[i] = subFolders[i].listFiles();
        }

        return fileArrays;
    }
}
