package wordCounting;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Scanner scanner = null;
    private static Writer writer = null;

    private static final Map<String, Integer> words = new HashMap<>();

    public static void main(String[] args) throws IOException {
        initiateScanner(new Scanner(System.in).nextLine());
        initiateWriter();

        replaceSeparatorSymbols();

        initiateScanner("result.txt");

        countWords();

        printMostFrequentWords();
    }

    private static void printMostFrequentWords() {
        words.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(20).forEach(entry -> System.out.println(entry.getKey() + " -> " + entry.getValue()));
    }

    private static void countWords() {
        while (scanner.hasNextLine()) {
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

    @SuppressWarnings("ConstantConditions")
    private static void initiateScanner(String fileName) throws FileNotFoundException {
        File[] allRootFiles = getApplicationRootFile().listFiles();
        for (File currentRootFile : allRootFiles) {
            String currentFileName = currentRootFile.getName().split("\\.")[0];
            if (currentFileName.equals(fileName) || currentRootFile.getName().equals(fileName)) {
                if (!fileName.contains(".txt")){
                    fileName = fileName.concat(".txt");
                }
                scanner = new Scanner(new FileInputStream(currentRootFile.getPath()));
                break;
            }
        }

    }

    @SuppressWarnings({"ConstantConditions"})
    private static File getApplicationRootFile() {
        URL url = Main.class.getResource(".");
        String path = url.getPath();
        return new File(path);
    }

    private static void initiateWriter() throws IOException {
        writer = new FileWriter("C:\\Modis_Java_Academy\\Homeworks\\SecondTopic\\src\\wordCounting\\result.txt");
    }

}
