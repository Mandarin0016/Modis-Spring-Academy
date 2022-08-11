package walkingAndFilteringFileTree;

import walkingAndFilteringFileTree.common.OutputMessages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get(args[0]);
        List<Path> paths = findJavaFilesSorted(path);

        //List all .java files
        System.out.println(OutputMessages.PROJECT_JAVA_FILES);
        paths.forEach(System.out::println);

        //List the min size file from the given project
        System.out.printf((OutputMessages.MAX_JAVA_FILE_SIZE) + "%n", paths.get(paths.size() - 1).getFileName().toString(), Files.size(paths.get(paths.size() - 1)));

        //List the max size file from the given project
        System.out.printf((OutputMessages.MIN_JAVA_FILE_SIZE) + "%n", paths.get(0).getFileName().toString(), Files.size(paths.get(0)));

        //List the average size file from the given project
        System.out.printf(OutputMessages.AVERAGE_JAVA_FILE_SIZE + "%n", paths.get(paths.size() / 2).getFileName(), Files.size(paths.get(paths.size() / 2)));

        //List the sum of the all file size from the given project
        Long sizeSum = findSizeSum(paths);
        System.out.printf((OutputMessages.SUM_JAVA_FILES) + "%n", sizeSum);

        //List the average size of the files from the given project
        System.out.printf(OutputMessages.AVERAGE_SIZE_OF_JAVA_FILES + "%n", sizeSum / paths.size());
    }

    private static Long findSizeSum(List<Path> paths) throws IOException {
        long sum = 0;
        for (Path path : paths) {
            sum += Files.size(path);
        }
        return sum;
    }

    private static List<Path> findJavaFilesSorted(Path path) throws IOException {
        List<Path> paths;
        try (Stream<Path> walk = Files.walk(path)) {
            paths = walk
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".java"))
                    .sorted(Comparator.comparing(currentPath -> {
                        try {
                            return Files.size(currentPath);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }))
                    .collect(Collectors.toList());
        }
        return paths;
    }
}
