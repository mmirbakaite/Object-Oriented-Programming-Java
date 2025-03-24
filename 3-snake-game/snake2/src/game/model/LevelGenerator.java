package game.model;

import game.view.GameConfig;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

/**
 * Responsible for generating levels with obstacles for the game.
 * This class creates randomized levels based on the provided game configuration. Each level is represented by a grid,
 * where obstacles are randomly placed. The class also provides utility methods to check if a specific cell is an
 * obstacle and to retrieve the list of all obstacles.
 */
public class LevelGenerator implements Serializable {

    /**
     * The width of the screen in pixels.
     */
    private final int screenWidth;

    /**
     * The height of the screen in pixels.
     */
    private final int screenHeight;

    /**
     * The size of each unit (cell) in the grid.
     */
    private final int unitSize;

    /**
     * A 2D array representing the grid of the level, where each cell indicates if it contains an obstacle.
     */
    private final boolean[][] grid;

    /**
     * A list of points representing the locations of all obstacles in the level.
     */
    private final List<Point> obstacles;

    /**
     * A random number generator used for obstacle placement.
     */
    private final Random random;

    /**
     * Constructs a LevelGenerator with the specified game configuration.
     *
     * @param config the game configuration, providing screen dimensions and unit size.
     */
    public LevelGenerator(GameConfig config) {
        this.screenWidth = config.getScreenWidth();
        this.screenHeight = config.getScreenHeight();
        this.unitSize = config.getUnitSize();
        this.grid = new boolean[screenWidth / unitSize][screenHeight / unitSize];
        this.obstacles = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Generates a new level by placing random obstacles on the grid.
     * Clears any existing obstacles and regenerates the level by randomly placing a specified number of obstacles
     * within the grid.
     */
    public void generateLevel() {
        obstacles.clear();
        for (boolean[] row : grid) {
            Arrays.fill(row, false);
        }

        int obstacleCount = random.nextInt(10) + 5; // Generate between 5 and 14 obstacles
        for (int i = 0; i < obstacleCount; i++) {
            int obstacleX = random.nextInt(screenWidth / unitSize);
            int obstacleY = random.nextInt(screenHeight / unitSize);

            obstacles.add(new Point(obstacleX, obstacleY));
            grid[obstacleX][obstacleY] = true;
        }
    }

    /**
     * Checks if a specific grid cell contains an obstacle.
     *
     * @param x the x-coordinate of the cell in grid units.
     * @param y the y-coordinate of the cell in grid units.
     * @return {@code true} if the specified cell contains an obstacle; {@code false} otherwise.
     */
    public boolean isObstacle(int x, int y) {
        return grid[x][y];
    }

    /**
     * Retrieves the list of all obstacles in the current level.
     *
     * @return a list of points, where each point represents the location of an obstacle.
     */
    public List<Point> getObstacles() {
        return obstacles;
    }
}
