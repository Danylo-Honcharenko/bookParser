package ByteConverter;

import java.nio.ByteBuffer;
import java.util.*;

public class ByteStream {
    public static List<String> toWordList(List<Byte> byteStorage) {
        List<String> wordsStorage = new ArrayList<>();
        // Converter and optimizer
        ByteMapping byteMapping = new ByteMapping();
        // Optimization means removing punctuation marks but not spaces
        List<Byte> optimizedByteStorage = byteMapping.optimizationByte(byteStorage);
        int currentSizeByteStorage = optimizedByteStorage.size();
        final int bufferSize = 128 * 1024;
        // Buffer initialization
        ByteBuffer intermediateByteStorage = ByteBuffer.allocate(bufferSize);
        // After a word is assembled, it is cleared to store the next word
        StringBuilder wordPicker = new StringBuilder();
        int currentPointerPositionByteStorage = 0;

        for (Byte letterNumber: optimizedByteStorage) {
            /*
            check for a space
            true - add the letter number to the buffer
            false - convert the number to a letter and save it in temporary letter storage
            */
            if (letterNumber != 32) {
                if (byteMapping.getCurrentIterationState()) {
                    // set the pointer position in the buffer from which we will continue recording
                    intermediateByteStorage.position(currentPointerPositionByteStorage);
                    // set the initial value false
                    byteMapping.setCurrentIterationState(false);
                }
                intermediateByteStorage.put(letterNumber);
            } else {
                // saves the cursor position in the buffer taking into account the indentation
                currentPointerPositionByteStorage = intermediateByteStorage.position() + 1;
                // convert received bytes into words
                byteMapping.toLetter(intermediateByteStorage, wordPicker, wordsStorage);
            }
            // we check whether the position of the cursor in the buffer is equal to the total size of our byte storage
            // an important condition because the code is based on indentation in the text
            if (intermediateByteStorage.position() == currentSizeByteStorage) {
                byteMapping.toLetter(intermediateByteStorage, wordPicker, wordsStorage);
            }
        }
        return wordsStorage.stream()
                .map(String::toLowerCase)
                .toList();
    }

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
