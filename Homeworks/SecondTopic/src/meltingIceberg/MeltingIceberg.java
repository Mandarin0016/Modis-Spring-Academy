package meltingIceberg;

import java.util.Scanner;

public class MeltingIceberg {
    private static long iceCubesCount;
    private static int hours;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        char[][] matrix = initiateMatrix(scanner, n);

        char[][] tempMatrix = new char[n][n];

        while (iceCubesCount > 0) {
            iceCubesCount = updateIceCubes(matrix, iceCubesCount, tempMatrix);
            hours++;
            updateMyMatrix(matrix, tempMatrix);
        }
        System.out.println(hours);
    }

    private static char[][] initiateMatrix(Scanner scanner, int n) {
        char[][] matrix = new char[n][n];

        for (int row = 0; row < n; row++) {
            String line = scanner.nextLine();
            matrix[row] = line.toCharArray();
            char iceCube = '*';
            iceCubesCount += line.chars().filter(ch -> ch == iceCube).count();
        }

        return matrix;
    }

    private static long updateIceCubes(char[][] matrix, long iceCubesCount, char[][] tempMatrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                char currentCell = matrix[row][col];
                if (currentCell == '0') {
                    tempMatrix[row][col] = '0';
                } else if (currentCell == '*') {
                    if (isMelting(matrix, row, col)) {
                        tempMatrix[row][col] = '0';
                        iceCubesCount--;

                    } else {
                        tempMatrix[row][col] = '*';
                    }
                }
            }
        }
        return iceCubesCount;
    }

    private static void updateMyMatrix(char[][] matrix, char[][] tempMatrix) {
        for (int row = 0; row < tempMatrix.length; row++) {
            for (int col = 0; col < tempMatrix[row].length; col++) {
                matrix[row][col] = tempMatrix[row][col];
            }
        }

    }

    private static boolean isMelting(char[][] matrix, int row, int col) {
        int countZeros = 0;
        if (matrix[row + 1][col] == '0') {
            countZeros++;
        }
        if (matrix[row - 1][col] == '0') {
            countZeros++;
        }
        if (matrix[row][col + 1] == '0') {
            countZeros++;
        }
        if (matrix[row][col - 1] == '0') {
            countZeros++;
        }
        if (countZeros >= 2) {
            return true;
        }
        return false;
    }
}
