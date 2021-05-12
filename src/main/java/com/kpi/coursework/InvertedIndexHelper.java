package com.kpi.coursework;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class InvertedIndexHelper {
    private static final int LEFT_BOUND = 0;
    private static final int RIGHT_BOUND = 1;

    public static void buildIndexPart(File[][] fileArrays, int[][] bounds, Map<String, Queue<String>> indexPartHolder) {
        Scanner in;

        try {
            for (int arrNum = 0; arrNum < fileArrays.length; arrNum++) {
                for (int fileNum = bounds[arrNum][LEFT_BOUND]; fileNum < bounds[arrNum][RIGHT_BOUND]; fileNum++) {
                    String fileName = fileArrays[arrNum][fileNum].getName().replaceAll(".txt", "");
                    in = new Scanner(fileArrays[arrNum][fileNum]);

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
                            indexPartHolder.get(nextWord).add(arrNum + ":" + fileName);
                        } else {
                            Queue<String> wordPositions = new PriorityQueue<>();
                            wordPositions.add(arrNum + ":" + fileName);
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

    public static void writeInvertedIndexToFile(Map<String, Queue<String>> invertedIndex, int threadsNum) {
        Calendar calendar = new GregorianCalendar();
        StringBuilder invertedIndexName = new StringBuilder("InvertedIndex_");
        invertedIndexName.append(threadsNum)
                .append("thr_")
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(calendar.get(Calendar.MONTH) + 1)
                .append("-")
                .append(calendar.get(Calendar.HOUR_OF_DAY))
                .append(calendar.get(Calendar.MINUTE));

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(invertedIndexName + ".txt"));
            Queue<String> indexWords = new PriorityQueue<>(invertedIndex.keySet());

            while (indexWords.size() != 0) {
                String word = indexWords.poll();
                StringBuilder indexLine = new StringBuilder(word);
                indexLine.append(": ");
                while (invertedIndex.get(word).size() != 0) {
                    indexLine.append(invertedIndex.get(word).poll()).append("  ");
                }
                writer.write(indexLine + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("The inverted index has been successfully written to the file!");
    }


}
