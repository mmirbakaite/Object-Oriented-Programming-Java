package game;

import players.Player;
import java.io.*;
import java.util.*;

public class Game {
    private Board board;
    private List<Player> players;
    private int rounds;
    private Dice dice;

    /**
     * Constructor Game is responsible for reading input data of the game,
     * adding according fields, players to the board
     * @param gameFile
     * @param diceFile
     * @throws IOException
     */
    public Game(String gameFile, String diceFile) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(gameFile));
        int numberOfFields = Integer.parseInt(br.readLine());
        board = new Board(numberOfFields);

        for (int i = 0; i < numberOfFields; i++) {
            String[] fieldInfo = br.readLine().split(" ");
            switch (fieldInfo[0]) {
                case "P":
                    board.addField(new PropertyField());
                    break;
                case "S":
                    int serviceCost = Integer.parseInt(fieldInfo[1]);
                    board.addField(new ServiceField(serviceCost));
                    break;
                case "L":
                    int luckyReward = Integer.parseInt(fieldInfo[1]);
                    board.addField(new LuckyField(luckyReward));
                    break;
            }
        }

        int numberOfPlayers = Integer.parseInt(br.readLine());
        players = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            String[] playerInfo = br.readLine().split(" ");
            String playerName = playerInfo[0];
            String strategy = playerInfo[1];
            switch (strategy) {
                case "Greedy":
                    players.add(new players.GreedyPlayer(playerName));
                    break;
                case "Careful":
                    players.add(new players.CarefulPlayer(playerName));
                    break;
                case "Tactical":
                    players.add(new players.TacticalPlayer(playerName));
                    break;
            }
        }

        rounds = Integer.parseInt(br.readLine());
        br.close();

        dice = new Dice(diceFile);
    }

    /**
     * Method startGame is responsible for continuing the game if there is rounds left,
     * players moving around the board, and rolling a Dice by triggering roll method.
     */
    public void startGame() {
        int currentRound = 0;
        while (currentRound < rounds && players.size() > 1) {
            for (int i = 0; i < players.size(); i++) {
                Player player = players.get(i);
                int roll = dice.roll();
                player.move(roll, board);

                if (player.isBankrupt()) {
                    System.out.println(player.getName() + " is bankrupt and removed from the game.");
                    board.releaseProperties(player);
                    players.remove(i--);
                }
            }
            currentRound++;
        }

        printResults();
    }

    /**
     * Method printResults is responsible for printing out the results when the game is over.
     */
    private void printResults() {
        for (Player player : players) {
            System.out.println(player);
        }
    }
}
