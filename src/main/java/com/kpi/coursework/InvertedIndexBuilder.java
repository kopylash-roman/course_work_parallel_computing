package com.kpi.coursework;

import java.io.File;
import java.util.List;
import java.util.Map;

public class InvertedIndexBuilder {
    public Map<String, List<String>> buildInvertedIndex(File[][] parts, int threadsNum) {
        if (threadsNum == 1) {
            return sequentialBuild(parts);
        } else {
            return parallelBuild(parts, threadsNum);
        }
    }

    private Map<String, List<String>> sequentialBuild(File[][] parts) {
        return null;
    }

    private Map<String, List<String>> parallelBuild(File[][] parts, int threadsNum) {
        return null;
    }
}
