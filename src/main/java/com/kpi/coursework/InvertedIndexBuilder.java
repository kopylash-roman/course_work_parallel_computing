package com.kpi.coursework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class InvertedIndexBuilder {
    public Map<String, List<String>> buildInvertedIndex(File[][] parts, int threadsNum) {
        if (threadsNum == 1) {
            return sequentialBuild(parts);
        } else {
            return parallelBuild(parts, threadsNum);
        }
    }

    private Map<String, List<String>> sequentialBuild(File[][] parts) {
        return buildIndexPart(parts);
    }

    private Map<String, List<String>> parallelBuild(File[][] parts, int threadsNum) {
        return null;
    }

    private Map<String, List<String>> buildIndexPart(File[][] filePart) {
        Map<String, List<String>> indexPart = new HashMap<>();
        Scanner in;

        try {
            for (int partNum = 0; partNum < filePart.length; partNum++) {
                for (int fileNum = 0; fileNum < filePart[partNum].length; fileNum++) {
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

                        if (indexPart.containsKey(nextWord)) {
                            indexPart.get(nextWord).add(partNum + ":" + fileName);
                        } else {
                            List<String> wordPositions = new LinkedList<>();
                            wordPositions.add(partNum + ":" + fileName);
                            indexPart.put(nextWord, wordPositions);
                        }

                        currentWord = nextWord;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error while opening file!");
        }

        return indexPart;
    }
}
