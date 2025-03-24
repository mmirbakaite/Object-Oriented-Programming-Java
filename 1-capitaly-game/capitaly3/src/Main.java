import game.Game;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String gameFilePath = "game.txt";
        String diceFilePath = "dice.txt";

        Game game = new Game(gameFilePath, diceFilePath);
        game.startGame();
    }
}
