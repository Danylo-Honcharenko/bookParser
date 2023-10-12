package com.perser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class BookParser {
    public List<String> getWords(File file) throws FileNotFoundException {
        return new BufferedReader(new FileReader(file))
                .lines()
                .flatMap(w -> Arrays.stream(w.split("[\\s+!,.]")))
                .map(String::toLowerCase)
                .filter(w -> !w.isEmpty())
                .toList();
    }
}
