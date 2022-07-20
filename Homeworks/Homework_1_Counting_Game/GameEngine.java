public class GameEngine {
    private final CountingGame countingGame;

    public GameEngine() {
        this.countingGame = new CountingGame();
    }

    public void run(){
        this.countingGame.playGame();
    }
}
