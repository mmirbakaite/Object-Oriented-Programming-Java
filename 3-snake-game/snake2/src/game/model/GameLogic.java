package game.model;

import game.view.GameConfig;

import java.awt.*;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * The GameLogic class handles the core logic of the Snake game,
 * including movement, collision detection, and game state management.
 * It delegates apple management to AppleManager and interacts with DatabaseManager for score handling.
 */
public class GameLogic implements Serializable {
    private final int SCREEN_WIDTH;
    private final int SCREEN_HEIGHT;
    private final int UNIT_SIZE;

    private final int[] x;
    private final int[] y;
    private int bodyParts;
    private int applesEaten;
    private char direction;
    private boolean running;

    private final Random random;
    private final LevelGenerator levelGenerator;
    private final AppleManager appleManager;
    private final DatabaseManager databaseManager;

    /**
     * Constructs the GameLogic object using the provided configuration.
     *
     * @param config the GameConfig instance containing game dimensions and unit size
     */
    public GameLogic(GameConfig config) {
        this.SCREEN_WIDTH = config.getScreenWidth();
        this.SCREEN_HEIGHT = config.getScreenHeight();
        this.UNIT_SIZE = config.getUnitSize();

        this.x = new int[(SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE)];
        this.y = new int[(SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE)];
        this.bodyParts = 2;
        this.direction = 'R'; // Initial direction is right
        this.running = true;
        this.random = new Random();

        this.levelGenerator = new LevelGenerator(config);
        this.appleManager = new AppleManager(SCREEN_WIDTH, SCREEN_HEIGHT, UNIT_SIZE);
        this.databaseManager = new DatabaseManager();
    }

    /**
     * Starts the game by initializing the snake, level, and apple.
     */
    public void startGame() {
        bodyParts = 2;
        applesEaten = 0;
        direction = 'R';
        running = true;

        for (int i = 0; i < bodyParts; i++) {
            x[i] = SCREEN_WIDTH / 2 - (i * UNIT_SIZE);
            y[i] = SCREEN_HEIGHT / 2;
        }

        levelGenerator.generateLevel();
        appleManager.generateNewApple(levelGenerator.getObstacles(), x, y, bodyParts);
    }

    /**
     * Moves the snake in the current direction.
     */
    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U' -> y[0] -= UNIT_SIZE;
            case 'D' -> y[0] += UNIT_SIZE;
            case 'L' -> x[0] -= UNIT_SIZE;
            case 'R' -> x[0] += UNIT_SIZE;
        }
    }

    /**
     * Checks if the snake has eaten the apple.
     *
     * @return true if the snake ate the apple, false otherwise
     */
    public boolean checkApple() {
        if (x[0] == appleManager.getAppleX() && y[0] == appleManager.getAppleY()) {
            bodyParts++;
            applesEaten++;
            appleManager.generateNewApple(levelGenerator.getObstacles(), x, y, bodyParts);
            return true;
        }
        return false;
    }

    /**
     * Checks for collisions with the snake's body, walls, or obstacles.
     *
     * @return true if a collision occurred, false otherwise
     */
    public boolean checkCollisions() {
        // Check collision with itself
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                return true;
            }
        }

        // Check collision with walls
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            running = false;
            return true;
        }

        // Check collision with obstacles
        for (Point obstacle : levelGenerator.getObstacles()) {
            if (x[0] == obstacle.x * UNIT_SIZE && y[0] == obstacle.y * UNIT_SIZE) {
                running = false;
                return true;
            }
        }

        return false;
    }

    /**
     * Saves the player's score to the database.
     *
     * @param playerName the name of the player
     */
    public void saveScoreToDatabase(String playerName) {
        databaseManager.saveScore(playerName, applesEaten);
    }

    /**
     * Fetches the top scores from the database.
     *
     * @return a list of top scores as strings
     */
    public List<String> getTopScoresFromDatabase() {
        return databaseManager.getTopScores();
    }

    // Getters and setters

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public int getBodyParts() {
        return bodyParts;
    }

    public int getApplesEaten() {
        return applesEaten;
    }

    public int getAppleX() {
        return appleManager.getAppleX();
    }

    public int getAppleY() {
        return appleManager.getAppleY();
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public boolean isRunning() {
        return running;
    }

    public List<Point> getObstacles() {
        return levelGenerator.getObstacles();
    }
}
