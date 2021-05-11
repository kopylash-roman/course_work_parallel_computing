package com.kpi.coursework;

import java.io.File;
import java.util.Scanner;

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

        int threadNum = getThreadsNumFromUser();
        if (threadNum == -1) {
            return;
        }


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
}
