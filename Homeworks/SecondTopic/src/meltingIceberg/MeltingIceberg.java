package meltingIceberg;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MeltingIceberg {
    private static long iceCubesCount;
    private static int hours;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("C:\\Modis_Java_Academy\\Homeworks\\SecondTopic\\src\\meltingIceberg\\data.txt"));
        char[][] matrix = initiateMatrix(scanner);

        while (iceCubesCount > 0) {
            updateCubesPerOneHour(matrix);
            hours++;
        }

        System.out.println(hours);
    }

    private static void updateCubesPerOneHour(char[][] matrix) {
        replaceMeltingCubes(matrix);
        updateMyMatrix(matrix);
    }

    private static void replaceMeltingCubes(char[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                if (matrix[row][col] == '*' && isMelting(matrix, row, col)) {
                    matrix[row][col] = '@';
                }
            }
        }
    }

    private static void updateMyMatrix(char[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                if (matrix[row][col] == '@') {
                    matrix[row][col] = '0';
                    iceCubesCount--;
                }
            }
        }
    }

    private static char[][] initiateMatrix(Scanner scanner) {
        int matrixSize = Integer.parseInt(scanner.nextLine());
        char[][] matrix = new char[matrixSize][matrixSize];
        for (int row = 0; row < matrixSize; row++) {
            String line = scanner.nextLine();
            matrix[row] = line.toCharArray();
            char iceCube = '*';
            iceCubesCount += line.chars().filter(ch -> ch == iceCube).count();
        }
        return matrix;
    }

    private static boolean isMelting(char[][] matrix, int row, int col) {
        int countZeros = 0;
        if (matrix[row + 1][col] == '0' || matrix[row - 1][col] == '0') {
            countZeros++;
        }
        if (matrix[row][col + 1] == '0' || matrix[row][col - 1] == '0') {
            countZeros++;
        }
        return countZeros >= 2;
    }
}
