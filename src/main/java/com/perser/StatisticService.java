package com.perser;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StatisticService {
    private Map<String, Long> popularWord;
    private Set<String> uniqueWords;
    private List<String> words;

    public void setWordsStorage(List<String> words) {
        this.words = words;
    }

    public void mostPopularWords(int limit, int amount) {
        popularWord = words.stream()
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet()
                        .stream()
                        .filter(m -> m.getKey().length() > amount)
                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        .limit(limit)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void uniqueWords() {
        uniqueWords = new HashSet<>(words);
    }

    public void save(File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(this.toString());
        writer.close();
    }

    @Override
    public String toString() {
        return "Unique words: " + uniqueWords.size() + " Total words: " + words.size() + " Popular words: " + popularWord;
    }
}
