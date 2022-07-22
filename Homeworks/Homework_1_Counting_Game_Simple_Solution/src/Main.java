package simpleSolution;

import java.util.ArrayDeque;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static String[] data = null;
    private static final ArrayDeque<Integer> gameParticipants = new ArrayDeque<>();

    public static void main(String[] args) {
        //Read the input from the console separated by space
        getInputData();

        //Get the winner ID/number
        int winnerId = findWinner(Integer.parseInt(data[0]), Integer.parseInt(data[1]));

        //Print the winner ID/number
        System.out.println(winnerId);
    }

    private static int findWinner(int numberOfPlayers, int hotNumber) {

        //First we register the participants
        registerParticipants(numberOfPlayers);

        //Second we find the left player (the winner)
        while (gameParticipants.size() > 1){
            //Remove the player on 'hotNumber' turn
            removeSinglePlayer(hotNumber);
        }

        //Return the player number
        return gameParticipants.pop();
    }

    private static void removeSinglePlayer(int hotNumber) {
        for (int i = 1; i < hotNumber; i++) {
            gameParticipants.offer(gameParticipants.pop());
        }
        gameParticipants.poll();
    }


    private static void registerParticipants(int numberOfPeople) {
        for (int i = 1; i <= numberOfPeople; i++) {
            gameParticipants.offer(i);
        }
    }

    private static void getInputData() {
        data = scanner.nextLine().split("\\s+");
    }
}
