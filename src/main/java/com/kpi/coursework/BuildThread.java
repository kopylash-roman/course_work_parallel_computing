package com.kpi.coursework;

import java.io.File;
import java.util.List;
import java.util.Map;

public class BuildThread extends Thread {
    private final File[][] fileArrays;
    private final int[][] bounds;
    private final int threadId;
    private final Map<String, List<String>> indexPartHolder;

    public BuildThread(File[][] fileArrays, int[][] bounds, Map<String, List<String>> indexPartHolder, int threadId) {
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
