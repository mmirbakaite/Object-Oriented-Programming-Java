package game.model;

import java.awt.*;
import java.util.Random;
import java.util.List;

/**
 * The AppleManager class is responsible for managing the apple's position in the game.
 */
public class AppleManager {
    private final int screenWidth;
    private final int screenHeight;
    private final int unitSize;
    private final Random random;

    private int appleX;
    private int appleY;

    /**
     * Constructs an AppleManager with the specified screen dimensions and unit size.
     *
     * @param screenWidth  the width of the game screen in pixels
     * @param screenHeight the height of the game screen in pixels
     * @param unitSize     the size of each grid unit in pixels
     */
    public AppleManager(int screenWidth, int screenHeight, int unitSize) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.unitSize = unitSize;
        this.random = new Random();
    }

    /**
     * Generates a new apple position, ensuring it doesn't overlap with obstacles or the snake.
     *
     * @param obstacles a list of obstacle points to avoid
     * @param snakeX    an array of x-coordinates representing the snake's position
     * @param snakeY    an array of y-coordinates representing the snake's position
     * @param bodyParts the number of snake body parts
     */
    public void generateNewApple(List<Point> obstacles, int[] snakeX, int[] snakeY, int bodyParts) {
        boolean validPosition;
        do {
            validPosition = true;
            appleX = random.nextInt(screenWidth / unitSize) * unitSize;
            appleY = random.nextInt(screenHeight / unitSize) * unitSize;

            // Check if apple overlaps with obstacles
            for (Point obstacle : obstacles) {
                if (appleX == obstacle.x * unitSize && appleY == obstacle.y * unitSize) {
                    validPosition = false;
                    break;
                }
            }

            // Check if apple overlaps with the snake
            for (int i = 0; i < bodyParts; i++) {
                if (appleX == snakeX[i] && appleY == snakeY[i]) {
                    validPosition = false;
                    break;
                }
            }
        } while (!validPosition);
    }


    /**
     * Retrieves the x-coordinate of the apple.
     *
     * @return the x-coordinate of the apple
     */
    public int getAppleX() {
        return appleX;
    }

    /**
     * Retrieves the y-coordinate of the apple.
     *
     * @return the y-coordinate of the apple
     */
    public int getAppleY() {
        return appleY;
    }
}
