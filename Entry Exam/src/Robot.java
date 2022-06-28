package robotMovements;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Robot {
    public static void main(String[] args) throws FileNotFoundException {
        //reading the input
        Scanner scanner = new Scanner(new FileInputStream("src/robotMovements/input"));

        //reading the test cases count
        int testsCount = Integer.parseInt(scanner.nextLine());


        //iterating through the tests
        for (int i = 0; i < testsCount; i++) {
            //read the current line
            String currentLine = scanner.nextLine();

            //check if the current line is a blank/empty string/line and decrement the i by 1
            if (currentLine.isBlank()) {
                i--;
                continue;
            }

            //matrix size
            int n = Integer.parseInt(currentLine.split("\\s+")[0]);
            int m = Integer.parseInt(currentLine.split("\\s+")[1]);

            //initializing matrix
            char[][] matrix = initializeMatrix(n, m, scanner);

            //save all number of executions, starting from the first cell till the last one
            Map<String, Integer> numberOfExecutionsByCell = new LinkedHashMap<>();

            for (int row = 0; row < matrix.length; row++) {
                for (int col = 0; col < matrix[row].length; col++) {
                    int totalCommandExecutions = totalCommandExecutionsForThisRowAndCol(matrix, row, col);
                    numberOfExecutionsByCell.put(row + " " + col, totalCommandExecutions);
                }
            }

            //Printing the top results
            printTopNumberOfRobotExecutions(numberOfExecutionsByCell);
        }

    }

    private static void printTopNumberOfRobotExecutions(Map<String, Integer> numberOfExecutionsByCell) {
        LinkedHashMap<String, Integer> topNumbersOfExecution = new LinkedHashMap<>();

        //Sorting the Map by descending order
        numberOfExecutionsByCell.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach(entry -> topNumbersOfExecution.put(entry.getKey(), entry.getValue()));

        //Printing the top number of executions by taking the first number(the biggest one) from the map and compare it to the others, if equals -> print it, if isn't equal we break the loop
        int topNumber = -1;

        for (Map.Entry<String, Integer> entry : topNumbersOfExecution.entrySet()) {
            topNumber = entry.getValue();
            break;
        }

        List<String> output = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : topNumbersOfExecution.entrySet()) {
            if (entry.getValue() == topNumber) {
                String coordinates = entry.getKey();
                output.add(String.format("%s %s %s",
                        Integer.parseInt(coordinates.toCharArray()[0] + "") + 1,
                        Integer.parseInt(coordinates.toCharArray()[2] + "") + 1,
                        entry.getValue()));
            } else break;
        }

        System.out.println(String.join(";", output));
    }

    private static Integer totalCommandExecutionsForThisRowAndCol(char[][] matrix, int row, int col) {

        //Counter for commands executions
        int commandsExecutionsCount = 0;

        //Save all the coordinates where our robotMovements.Robot was.
        List<String> wasHereCoordinates = new ArrayList<>();

        while (true) {
            //Take direction
            char direction = matrix[row][col];

            //Add wasHere coordinates
            wasHereCoordinates.add(row + "" + col);

            //Move our robotMovements.Robot
            switch (direction) {
                case 'U' -> row--;
                case 'D' -> row++;
                case 'L' -> col--;
                case 'R' -> col++;
            }

            commandsExecutionsCount++;

            //Check if the robot was there before
            if (inBounds(matrix, row, col) && wasHereCoordinates.contains(row + "" + col)) {
                //The robot was already there, so we stop executing the program
                break;
            }

            //Check if the robot is out of bounds
            if (!inBounds(matrix, row, col)) {
                break;
            }
        }

        return commandsExecutionsCount;
    }

    private static boolean inBounds(char[][] matrix, int myNewRow, int myNewCol) {
        if (myNewRow >= 0 && myNewRow < matrix.length && myNewCol >= 0 && myNewCol < matrix[myNewRow].length) {
            return true;
        }
        return false;
    }

    private static char[][] initializeMatrix(int n, int m, Scanner scanner) {
        char[][] matrix = new char[n][m];

        for (int i = 0; i < n; i++) {
            char[] currentMatrixRow = scanner.nextLine().toCharArray();
            matrix[i] = currentMatrixRow;
        }

        return matrix;
    }
}
