package wordCounting;

public enum Data {
    RAW_FILE("wiki_java.txt", "C:\\Modis_Java_Academy\\Homeworks\\SecondTopic\\src\\wordCounting\\wiki_java.txt"),
    STOP_WORDS("stop_words.txt", "C:\\Modis_Java_Academy\\Homeworks\\SecondTopic\\src\\wordCounting\\stop_words.txt");

    private final String fileName;
    private final String filePath;

    Data(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }
}
