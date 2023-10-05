import ByteConverter.ByteStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the name of the book on which you want to analyze the operation");
        System.out.print("Book title: ");
        String bookTitle = input.nextLine();
        Path path = Path.of(String.format("src\\assets\\Books\\%s.txt", bookTitle));
        if (!Files.isExecutable(path)) {
            System.err.format("The book with the title \"%s\" does not exist, try again", bookTitle);
            return;
        }
        List<Byte> byteStorage = new ArrayList<>();
        try (FileReader readeBook = new FileReader(path.toString())) {
            byte b;
            while ((b = (byte) readeBook.read()) != -1) {
                byteStorage.add(b);
            }
        } catch (IOException e) {
            System.err.format("File read error: %s", e.getMessage());
        }

        List<String> wordStorage = ByteStream.toWordList(byteStorage);
        Map<String, Integer> mostPopularWords = ByteStream.toMap(wordStorage);
        mostPopularWords.entrySet().stream()
                .filter(m -> m.getKey().length() > 3)
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(m -> System.out.println("Word: " + m.getKey() + " " + " Amount: " + m.getValue()));

        System.out.println("\nUnique words");
        System.out.println("===============");
        Set<String> uniqueWords = ByteStream.toSet(wordStorage);
        uniqueWords.forEach(System.out::println);

        int totalWords = wordStorage.size();
        System.out.println("===============");
        System.out.println("Total words: " + totalWords);

        Path bookStatistic = Path.of(String.format("src\\Statistic\\%s_statistic.txt", bookTitle));
        try (FileWriter statisticFile = new FileWriter(bookStatistic.toString())) {
//            Add method create folder
            new File(bookStatistic.toString());
            statisticFile.write("Amount word:\n");
            mostPopularWords.entrySet().stream()
                    .filter(m -> m.getKey().length() > 3)
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(10)
                    .forEach(m -> {
                        try {
                            statisticFile.write("Word: " + m.getKey() + " " + " Amount: " + m.getValue() + "\n");
                        } catch (IOException e) {
                           System.err.format("File write error: %s", e);
                        }
                    });
            statisticFile.write("\nUnique words:\n");
            for (String word: uniqueWords) {
                statisticFile.write(word + "\n");
            }
            statisticFile.write("\nTotal words: " + totalWords);
        } catch (Exception e) {
            System.err.format("File error: %s", e.getMessage());
        }
    }
}