package Converter;

import java.util.*;

public class Converter {
    public static Map<String, Integer> toMap(List<String> wordStorage) {
        Map<String, Integer> wordRating = new HashMap<>();
        for (String word: wordStorage) {
            if (wordRating.containsKey(word)) {
                int value = wordRating.get(word);
                wordRating.put(word, value + 1);
            }
            else wordRating.put(word, 1);
        }
        return wordRating;
    }

    public static Set<String> toSet(List<String> wordStorage) {
        return new HashSet<>(wordStorage);
    }
}
