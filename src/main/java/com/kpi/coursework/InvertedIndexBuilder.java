package com.kpi.coursework;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InvertedIndexBuilder {
    public Map<String, List<String>> buildInvertedIndex(File[][] fileArrays, int threadsNum) {
        if (threadsNum == 1) {
            return sequentialBuild(fileArrays);
        } else {
            return parallelBuild(fileArrays, threadsNum);
        }
    }

    private Map<String, List<String>> sequentialBuild(File[][] fileArrays) {
        int[][] bounds = new int[fileArrays.length][2];
        for (int i = 0; i < bounds.length; i++) {
            bounds[i][0] = 0;
            bounds[i][1] = fileArrays[i].length;
        }

        Map<String, List<String>> invertedIndex = new ConcurrentHashMap<>();
        InvertedIndexHelper.buildIndexPart(fileArrays, bounds, invertedIndex);

        return invertedIndex;
    }

    private Map<String, List<String>> parallelBuild(File[][] fileArrays, int threadsNum) {
        BuildThread[] buildThreads = new BuildThread[threadsNum];

        List<Map<String, List<String>>> listOfIndexParts = new ArrayList<>(threadsNum);
        for (int i = 0; i < threadsNum; i++) {
            Map<String, List<String>> indexPart = new HashMap<>();
            listOfIndexParts.add(indexPart);

            int[][] bounds = new int[fileArrays.length][2];
            for(int j = 0; j < bounds.length; j++) {
                bounds[j][0] = fileArrays[j].length / threadsNum * i;
                bounds[j][1] = i == threadsNum - 1 ? fileArrays[j].length : fileArrays[j].length / threadsNum * (i + 1);
            }

            buildThreads[i] = new BuildThread(fileArrays, bounds, indexPart, i);
            buildThreads[i].start();
        }

        try {
            for (BuildThread task : buildThreads) {
                task.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return mergeIndexParts(listOfIndexParts);
    }

    private Map<String, List<String>> mergeIndexParts(List<Map<String, List<String>>> indexParts) {
        Map<String, List<String>> invertedIndex = new ConcurrentHashMap<>();

        for (Map<String, List<String>> indexPart : indexParts) {
            String[] wordsArray = new String[indexPart.keySet().size()];
            indexPart.keySet().toArray(wordsArray);

            for (String word : wordsArray) {
                if (invertedIndex.containsKey(word)) {
                    invertedIndex.get(word).addAll(indexPart.get(word));
                } else {
                    List<String> wordPositions = new LinkedList<>(indexPart.get(word));
                    invertedIndex.put(word, wordPositions);
                }
            }
        }

        for (List<String> positionList : invertedIndex.values()) {
            positionList.sort(String::compareTo);
        }

        return invertedIndex;
    }
}
