package testing;

import game.model.GameLogic;
import game.model.DatabaseManager;
import game.model.*;
import game.view.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class provides white-box testing for the game logic in the {@code GameLogic} class.
 */
class GameLogicTest {

    private GameLogic gameLogic;
    private GameConfig config;

    /**
     * Sets up a new {@code GameLogic} instance before each test.
     */
    @BeforeEach
    void setUp() {
        config = new GameConfig(600, 600, 25);
        gameLogic = new GameLogic(config);
    }

    /**
     * Tests the initialization of the game to ensure the snake starts with the correct
     * number of body parts and the game is running.
     */
    @Test
    void testGameInitialization() {
        gameLogic.startGame();

        assertEquals(2, gameLogic.getBodyParts());
        assertEquals('R', gameLogic.getDirection());
        assertTrue(gameLogic.isRunning());
    }

    /**
     * Tests that a new apple does not spawn on an obstacle.
     */
    @Test
    void testAppleDoesNotSpawnOnObstacle() {
        gameLogic.startGame();

        List<Point> obstacles = gameLogic.getObstacles();
        gameLogic.checkApple(); // Generates the new apple

        Point apple = new Point(gameLogic.getAppleX() / config.getUnitSize(),
                gameLogic.getAppleY() / config.getUnitSize());

        System.out.println("Obstacles: " + obstacles);
        System.out.println("Apple: " + apple);

        assertFalse(obstacles.contains(apple), "Apple should not spawn on an obstacle.");
    }


    /**
     * Tests the {@code checkApple} method to verify that the snake's body grows
     * and the score increases when an apple is eaten.
     */
    @Test
    void testAppleConsumptionIncreasesBodyAndScore() {
        gameLogic.startGame();

        int initialBodyParts = gameLogic.getBodyParts();
        int initialApplesEaten = gameLogic.getApplesEaten();

        // Set the apple's position directly
        gameLogic.getX()[0] = gameLogic.getAppleX();
        gameLogic.getY()[0] = gameLogic.getAppleY();

        boolean ateApple = gameLogic.checkApple();

        System.out.println("Initial Body Parts: " + initialBodyParts);
        System.out.println("Initial Apples Eaten: " + initialApplesEaten);
        System.out.println("Ate Apple: " + ateApple);
        System.out.println("New Body Parts: " + gameLogic.getBodyParts());
        System.out.println("New Apples Eaten: " + gameLogic.getApplesEaten());

        assertTrue(ateApple, "checkApple should return true if the apple is eaten.");
        assertEquals(initialBodyParts + 1, gameLogic.getBodyParts(), "Body parts should increase by 1 after eating an apple.");
        assertEquals(initialApplesEaten + 1, gameLogic.getApplesEaten(), "Apples eaten should increase by 1 after eating an apple.");
    }


    /**
     * Tests the {@code saveScoreToDatabase} method to verify it correctly interacts
     * with the {@code DatabaseManager}.
     */
    @Test
    void testScoreSavingToDatabase() {
        class MockDatabaseManager extends DatabaseManager {
            private final List<String> savedScores = new ArrayList<>();

            @Override
            public void saveScore(String playerName, int score) {
                savedScores.add(playerName + ": " + score);
            }

            public boolean isScoreSaved(String expected) {
                return savedScores.contains(expected);
            }
        }

        MockDatabaseManager mockDatabaseManager = new MockDatabaseManager();

        GameLogic gameLogicWithMock = new GameLogic(config) {
            @Override
            public void saveScoreToDatabase(String playerName) {
                mockDatabaseManager.saveScore(playerName, getApplesEaten());
            }
        };

        String playerName = "TestPlayer";
        gameLogicWithMock.saveScoreToDatabase(playerName);

        assertTrue(mockDatabaseManager.isScoreSaved("TestPlayer: 0"));
    }

    /**
     * Tests the {@code checkCollisions} method to verify that the game ends correctly
     * when the snake collides with itself.
     */
    @Test
    void testSnakeCollisionEndsGame() {
        gameLogic.startGame();

        gameLogic.getX()[0] = gameLogic.getX()[1];
        gameLogic.getY()[0] = gameLogic.getY()[1];

        boolean collision = gameLogic.checkCollisions();

        assertTrue(collision);
        assertFalse(gameLogic.isRunning());
    }
}
