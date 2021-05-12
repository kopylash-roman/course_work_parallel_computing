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
        return null;
    }
}