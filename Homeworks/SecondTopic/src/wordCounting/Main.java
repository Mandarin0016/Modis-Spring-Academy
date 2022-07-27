package wordCounting;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Scanner scanner = null;
    private static Writer writer = null;

    private static final Map<String, Integer> words = new HashMap<>();

    public static void main(String[] args) throws IOException {
        initiateScanner("C:\\Modis_Java_Academy\\Homeworks\\SecondTopic\\src\\wordCounting\\wiki_java.txt");
        initiateWriter("C:\\Modis_Java_Academy\\Homeworks\\SecondTopic\\src\\wordCounting\\result.txt");

        replaceSeparatorSymbols();

        countWords();
        printMostFrequentWords();
    }

    private static void printMostFrequentWords() {
        words.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(20).forEach(entry -> System.out.println(entry.getKey() + " -> " + entry.getValue()));
    }

    private static void countWords() throws FileNotFoundException {
        initiateScanner("C:\\Modis_Java_Academy\\Homeworks\\SecondTopic\\src\\wordCounting\\result.txt");
        while (scanner.hasNextLine()){
            String[] currentRowWords = scanner.nextLine().split("\\s+");

            Arrays.stream(currentRowWords).forEach(word -> {
                words.putIfAbsent(word, 0);
                words.put(word, words.get(word) + 1);
            });
        }

    }

    private static void replaceSeparatorSymbols() throws IOException {
        Pattern pattern = Pattern.compile("[-0-9;()\\[\\]\\{\\}\\/*,.:\\<?!>\"'=`~!$%@– ]+");
        Matcher matcher;

        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            matcher = pattern.matcher(currentLine);
            if (matcher.find()) {
                currentLine = matcher.replaceAll(" ").trim();
            }
            if (!currentLine.isEmpty()) {
                writer.write(currentLine + System.lineSeparator());
            }
        }

        writer.close();
    }

    private static void initiateScanner(String path) throws FileNotFoundException {
        scanner = new Scanner(new FileInputStream(path));
    }

    private static void initiateWriter(String path) throws IOException {
        writer = new FileWriter(path);
    }


}
