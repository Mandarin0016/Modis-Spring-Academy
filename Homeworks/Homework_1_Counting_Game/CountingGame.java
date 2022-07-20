import java.util.ArrayDeque;
import java.util.Scanner;

public class CountingGame {
    private static final Scanner scanner = new Scanner(System.in);
    private static String[] data = null;
    private static final ArrayDeque<Integer> gameParticipants = new ArrayDeque<>();

    public void playGame() {
        //First we get the number of people participating in our game and the fatal number
        getInputData();

        //We are going to initialize our data structure
        registerParticipants(Integer.parseInt(data[0]));

        //Find the game winner
        printTheWinner();
    }

    private void printTheWinner() {
        int winnerNumber = findWinner(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        System.out.printf(GameMessages.GAME_WINNER, winnerNumber);
    }

    private int findWinner(int numberOfPlayers, int fatalNumber) {
        while (numberOfPlayers > 1){
            removeSinglePlayer(fatalNumber);
            numberOfPlayers--;
        }
        return gameParticipants.pop();
    }

    private void removeSinglePlayer(int iterations) {
        for (int i = 1; i < iterations; i++) {
            gameParticipants.offer(gameParticipants.pop());
        }
        gameParticipants.poll();
    }

    private static void registerParticipants(int n) {
        for (int i = 1; i <= n; i++) {
            gameParticipants.offer(i);
        }
    }

    private static void getInputData() {
        data = scanner.nextLine().split(" ");
    }
}
