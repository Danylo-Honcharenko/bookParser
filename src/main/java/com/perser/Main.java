package com.perser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String defaultDir = "src//main//resources//";
    private static final String prefixStatistic = "%s_statistic.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookParser parser = new BookParser();

        System.out.println("Enter the name of the book on which you want to analyze the operation");
        System.out.print("Book title: ");
        String bookName = scanner.next();

        File file = new File(defaultDir + "Books", bookName + ".txt");
        List<String> words = new ArrayList<>();
        try {
            words = parser.getWords(file);
        } catch (FileNotFoundException notFoundException) {
            System.err.println(Message.FILE_NOT_FOUND);
        }

        StatisticService statistic = new StatisticService();
        statistic.setWordsStorage(words);
        statistic.mostPopularWords(10, 3);
        statistic.uniqueWords();
        System.out.println(statistic);
        try {
            statistic.save(new File(String.format(defaultDir + "Statistics//" + prefixStatistic, bookName)));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}