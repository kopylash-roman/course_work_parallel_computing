package com.kpi.coursework;

import java.io.File;
import java.util.Map;
import java.util.Queue;

public class BuildThread extends Thread {
    private final File[][] fileArrays;
    private final int[][] bounds;
    private final int threadId;
    private final Map<String, Queue<String>> indexPartHolder;

    public BuildThread(File[][] fileArrays, int[][] bounds, Map<String, Queue<String>> indexPartHolder, int threadId) {
        this.fileArrays = fileArrays;
        this.bounds = bounds;
        this.indexPartHolder = indexPartHolder;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        System.out.println("Builder thread " + threadId + " is running...");
        InvertedIndexHelper.buildIndexPart(fileArrays, bounds, indexPartHolder);
        System.out.println("Builder thread " + threadId + " finished index building!");
    }
}
