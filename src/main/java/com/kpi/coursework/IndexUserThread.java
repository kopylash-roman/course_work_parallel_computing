package com.kpi.coursework;

import java.util.Map;
import java.util.Queue;
import java.util.Random;

public class IndexUserThread extends Thread {
    private static final Random RANDOM = new Random();
    private final Map<String, Queue<String>> invertedIndex;
    private final String[] dictionary;
    private final int threadId;

    public IndexUserThread(Map<String, Queue<String>> invertedIndex, int threadId) {
        this.invertedIndex = invertedIndex;
        this.threadId = threadId;

        dictionary = new String[invertedIndex.size()];
        invertedIndex.keySet().toArray(dictionary);
    }

    @Override
    public void run() {
        int dictSize = dictionary.length;

        for (int i = 0; i < 10; i++) {
            String randomWord = dictionary[RANDOM.nextInt(dictSize)];
            String firstPosition = invertedIndex.get(randomWord).peek();

            System.out.println("User " + threadId + ": first position for word [" + randomWord + "] is " + firstPosition);
        }
    }
}
