package com.kpi.coursework;

import java.io.*;
import java.util.*;

public class InvertedIndexHelper {
    private static final int LEFT_BOUND = 0;
    private static final int RIGHT_BOUND = 1;

    public static void buildIndexPart(File[][] filePart, int[][] bounds, Map<String, Queue<String>> indexPartHolder) {
        Scanner in;

        try {
            for (int partNum = 0; partNum < filePart.length; partNum++) {
                for (int fileNum = bounds[partNum][LEFT_BOUND]; fileNum < bounds[partNum][RIGHT_BOUND]; fileNum++) {
                    String fileName = filePart[partNum][fileNum].getName().replaceAll(".txt", "");
                    in = new Scanner(filePart[partNum][fileNum]);

                    String preparedLine = in.nextLine()
                            .replaceAll("[^A-Za-z\\s]", "")
                            .replaceAll(" +", " ")
                            .trim()
                            .toLowerCase();

                    Queue<String> wordsQueue = new PriorityQueue<>(Arrays.asList(preparedLine.split(" ")));

                    String currentWord = "";
                    while (!wordsQueue.isEmpty()) {
                        String nextWord = wordsQueue.poll();
                        if (nextWord.equals(currentWord) || nextWord.length() < 2) {
                            continue;
                        }

                        if (indexPartHolder.containsKey(nextWord)) {
                            indexPartHolder.get(nextWord).add(partNum + ":" + fileName);
                        } else {
                            Queue<String> wordPositions = new PriorityQueue<>();
                            wordPositions.add(partNum + ":" + fileName);
                            indexPartHolder.put(nextWord, wordPositions);
                        }

                        currentWord = nextWord;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error while opening file!");
        }
    }
}
