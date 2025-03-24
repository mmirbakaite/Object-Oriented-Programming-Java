package game.model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a player's saved data in the game, storing their state and level obstacles.
 */
public class PlayerSave implements Serializable {

    /**
     * The name of the player.
     */
    private String playerName;

    /**
     * The x-coordinates of the player's body parts.
     */
    private int[] x;

    /**
     * The y-coordinates of the player's body parts.
     */
    private int[] y;

    /**
     * The number of body parts the player currently has.
     */
    private int bodyParts;

    /**
     * The number of apples the player has eaten.
     */
    private int applesEaten;

    /**
     * The current direction of the player's movement.
     */
    private char direction;

    /**
     * A list of points representing the obstacles in the level.
     */
    private List<Point> obstacles;

    /**
     * Default constructor for creating an empty player save.
     */
    public PlayerSave() {}

    /**
     * Constructs a PlayerSave instance with the specified details.
     *
     * @param playerName  the name of the player.
     * @param x           the x-coordinates of the player's body parts.
     * @param y           the y-coordinates of the player's body parts.
     * @param bodyParts   the number of body parts the player has.
     * @param applesEaten the number of apples the player has eaten.
     * @param direction   the direction the player is moving in.
     * @param obstacles   the list of obstacles in the level.
     */
    public PlayerSave(String playerName, int[] x, int[] y, int bodyParts, int applesEaten, char direction, List<Point> obstacles) {
        this.playerName = playerName;
        this.x = Arrays.copyOf(x, x.length);
        this.y = Arrays.copyOf(y, y.length);
        this.bodyParts = bodyParts;
        this.applesEaten = applesEaten;
        this.direction = direction;
        this.obstacles = new ArrayList<>(obstacles);
    }

    /**
     * Retrieves the player's name.
     *
     * @return the player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Retrieves the x-coordinates of the player's body parts.
     *
     * @return an array of x-coordinates.
     */
    public int[] getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinates of the player's body parts.
     *
     * @return an array of y-coordinates.
     */
    public int[] getY() {
        return y;
    }

    /**
     * Retrieves the number of body parts the player has.
     *
     * @return the number of body parts.
     */
    public int getBodyParts() {
        return bodyParts;
    }

    /**
     * Retrieves the number of apples the player has eaten.
     *
     * @return the number of apples eaten.
     */
    public int getApplesEaten() {
        return applesEaten;
    }

    /**
     * Retrieves the current direction of the player's movement.
     *
     * @return the direction of movement.
     */
    public char getDirection() {
        return direction;
    }

    /**
     * Retrieves the list of obstacles in the level.
     *
     * @return a list of points representing obstacles.
     */
    public List<Point> getObstacles() {
        return obstacles;
    }
}
