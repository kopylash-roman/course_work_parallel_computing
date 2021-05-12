package com.kpi.coursework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InvertedIndexBuilder {
    public Map<String, Queue<String>> buildInvertedIndex(File[][] parts, int threadsNum) {
        if (threadsNum == 1) {
            return sequentialBuild(parts);
        } else {
            return parallelBuild(parts, threadsNum);
        }
    }

    private Map<String, Queue<String>> sequentialBuild(File[][] parts) {
        int[][] bounds = new int[parts.length][2];
        for (int i = 0; i < bounds.length; i++) {
            bounds[i][0] = 0;
            bounds[i][1] = parts[i].length;
        }

        Map<String, Queue<String>> invertedIndex = new ConcurrentHashMap<>();
        InvertedIndexHelper.buildIndexPart(parts, bounds, invertedIndex);

        return invertedIndex;
    }

    private Map<String, Queue<String>> parallelBuild(File[][] parts, int threadsNum) {
        BuildTask[] buildTasks = new BuildTask[threadsNum];

        List<Map<String, Queue<String>>> listOfIndexParts = new ArrayList<>(threadsNum);
        for (int i = 0; i < threadsNum; i++) {
            Map<String, Queue<String>> indexPart = new HashMap<>();
            listOfIndexParts.add(indexPart);

            int[][] bounds = new int[parts.length][2];
            for(int j = 0; j < bounds.length; j++) {
                bounds[j][0] = parts[j].length / threadsNum * i;
                bounds[j][1] = i == threadsNum - 1 ? parts[j].length : parts[j].length / threadsNum * (i + 1);
            }

            buildTasks[i] = new BuildTask(parts, bounds, indexPart, i);
            buildTasks[i].start();
        }

        try {
            for (BuildTask task : buildTasks) {
                task.join();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return mergeIndexParts(listOfIndexParts);
    }

    private Map<String, Queue<String>> mergeIndexParts(List<Map<String, Queue<String>>> indexParts) {
        Map<String, Queue<String>> invertedIndex = new ConcurrentHashMap<>();

        for (Map<String, Queue<String>> indexPart : indexParts) {
            String[] wordsArray = new String[indexPart.keySet().size()];
            indexPart.keySet().toArray(wordsArray);

            for (String word : wordsArray) {
                if (invertedIndex.containsKey(word)) {
                    invertedIndex.get(word).addAll(indexPart.get(word));
                } else {
                    PriorityQueue<String> wordPositions = new PriorityQueue<>();
                    wordPositions.addAll(indexPart.get(word));
                    invertedIndex.put(word, wordPositions);
                }
            }
        }

        return invertedIndex;
    }
}