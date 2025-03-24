package gameTests;

import mvc.GameLogic;
import game.LevelGenerator;
import database.DatabaseManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class provides white-box testing for the game logic in the {@code GameLogic} class.
 */
class WhiteBox {

    private GameLogic gameLogic;
    private final int screenWidth = 600;
    private final int screenHeight = 600;
    private final int unitSize = 25;

    /**
     * Sets up a new {@code GameLogic} instance before each test.
     */
    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic(screenWidth, screenHeight, unitSize);
    }

    /**
     * Tests the initialization of the game to ensure the snake starts with the correct
     * number of body parts and the game is running.
     */
    @Test
    void testGameInitialization() {
        gameLogic.startGame();

        assertEquals(2, gameLogic.getBodyParts(), "Snake should start with 2 body parts.");
        assertEquals('R', gameLogic.getDirection(), "Initial direction should be right.");
        assertTrue(gameLogic.isRunning(), "Game should be running after startGame.");
    }

    /**
     * Tests that a new apple does not spawn on an obstacle.
     */
    @Test
    void testAppleDoesNotSpawnOnObstacle() {
        gameLogic.startGame();

        LevelGenerator levelGenerator = new LevelGenerator(screenWidth, screenHeight, unitSize);
        levelGenerator.generateLevel();
        gameLogic.newApple();

        List<Point> obstacles = levelGenerator.getObstacles();
        Point apple = new Point(gameLogic.getAppleX() / unitSize, gameLogic.getAppleY() / unitSize);

        assertFalse(obstacles.contains(apple), "Apple should not spawn on an obstacle.");
    }

    /**
     * Tests the {@code checkApple} method to verify that the snake's body grows
     * and the score increases when an apple is eaten.
     */
    @Test
    void testAppleConsumptionIncreasesBodyAndScore() {
        gameLogic.startGame();

        gameLogic.newApple();
        int appleX = gameLogic.getAppleX();
        int appleY = gameLogic.getAppleY();

        // Simulate eating the apple
        gameLogic.getX()[0] = appleX;
        gameLogic.getY()[0] = appleY;

        boolean ateApple = gameLogic.checkApple();

        assertTrue(ateApple, "checkApple should return true if the apple is eaten.");
        assertEquals(3, gameLogic.getBodyParts(), "Body parts should increase by 1 after eating an apple.");
        assertEquals(1, gameLogic.getApplesEaten(), "Apples eaten should increase by 1 after eating an apple.");
    }

    /**
     * Tests the {@code saveScoreToDatabase} method to verify it correctly interacts
     * with the {@code DatabaseManager}.
     */
    @Test
    void testScoreSavingToDatabase() {
        DatabaseManager mockDatabaseManager = new DatabaseManager() {
            private boolean saveCalled = false;

            @Override
            public void saveScore(String playerName, int score) {
                saveCalled = true;
            }

            public boolean isSaveCalled() {
                return saveCalled;
            }
        };

        GameLogic gameLogicWithMock = new GameLogic(screenWidth, screenHeight, unitSize) {
            @Override
            public void saveScoreToDatabase(String playerName) {
                mockDatabaseManager.saveScore(playerName, getApplesEaten());
            }
        };

        String playerName = "TestPlayer";
        gameLogicWithMock.saveScoreToDatabase(playerName);

        assertTrue(((DatabaseManager) mockDatabaseManager).isSaveCalled(), "saveScoreToDatabase should save the score.");
    }

    /**
     * Tests the {@code checkCollisions} method to verify that the game ends correctly
     * when the snake collides with itself.
     */
    @Test
    void testSnakeCollisionEndsGame() {
        gameLogic.startGame();

        // Simulate the snake colliding with itself
        gameLogic.getX()[0] = gameLogic.getX()[1];
        gameLogic.getY()[0] = gameLogic.getY()[1];

        boolean collision = gameLogic.checkCollisions();

        assertTrue(collision, "checkCollisions should return true if the snake hits itself.");
        assertFalse(gameLogic.isRunning(), "Game should not be running after a collision.");
    }
}
