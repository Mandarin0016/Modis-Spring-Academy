package wordCounting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final Map<String, Integer> words = new HashMap<>();
    private static final int TOP_LIMIT = 20;

    public static void main(String[] args) throws IOException {
        //Get the valid words from the specified file
        instantiateWords(Path.of(Data.RAW_FILE.getFilePath()));

        //Get the top (TOP_LIMIT) frequent words sorted into Map
        Map<String, Integer> result = getTopFrequentWordsSorted(TOP_LIMIT);

        //Print the map content
        printResult(result);
    }

    private static void printResult(Map<String, Integer> result) {
        for (Map.Entry<String, Integer> pair : result.entrySet()) {
            System.out.println(pair.getKey() + " -> " + pair.getValue());
        }
    }

    private static void instantiateWords(Path fileLocalization) throws IOException {
        Set<String> STOP_WORDS = Files.lines(Path.of(Data.STOP_WORDS.getFilePath())).map(String::toLowerCase).collect(Collectors.toSet());

        Files.lines(fileLocalization).forEach(line -> {
            String[] currentLine = line.split("\\W+");
            Arrays.stream(currentLine)
                    .map(String::toLowerCase) //map every word to lower case
                    .filter(word -> word.length() > 1) //skip all the words with length less than 2
                    .filter(word -> !STOP_WORDS.contains(word)) //skip all the STOP_WORDS
                    .forEach(word -> words.put(word, words.getOrDefault(word, 0) + 1)); //fill the map with all the valid words
        });
    }

    private static Map<String, Integer> getTopFrequentWordsSorted(int topLimit) {
        return words.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) // sort the STREAM
                .limit(topLimit) // put a limit
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new)); // collect the STREAM into Map
    }

}
