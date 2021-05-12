package com.kpi.coursework;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class IndexUserThread extends Thread {
    private static final Random RANDOM = new Random();
    private final Map<String, List<String>> invertedIndex;
    private final String[] dictionary;
    private final int threadId;

    public IndexUserThread(Map<String, List<String>> invertedIndex, int threadId) {
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
            List<String> wordPositions = invertedIndex.get(randomWord);

            String randomPosition = wordPositions.get(RANDOM.nextInt(wordPositions.size()));

            System.out.println("User " + threadId + ": random position for word [" + randomWord + "] is " + randomPosition);
        }
    }
}
