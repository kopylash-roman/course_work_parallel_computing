package com.kpi.coursework;

import java.io.File;
import java.util.Map;
import java.util.Queue;

public class BuildTask extends Thread {
    private final File[][] parts;
    private final int[][] bounds;
    private final int threadNum;
    private final Map<String, Queue<String>> indexPartHolder;

    public BuildTask(File[][] parts, int[][] bounds, Map<String, Queue<String>> indexPartHolder, int threadNum) {
        this.parts = parts;
        this.bounds = bounds;
        this.indexPartHolder = indexPartHolder;
        this.threadNum = threadNum;
    }

    @Override
    public void run() {
        System.out.println("Thread " + threadNum + " is running...");
        InvertedIndexHelper.buildIndexPart(parts, bounds, indexPartHolder);
        System.out.println("Thread " + threadNum + " finished working!");
    }
}
