package game.view;

/**
 * The GameConfig class holds configuration values related to the game's dimensions and unit size.
 */
public class GameConfig {
    private final int screenWidth;
    private final int screenHeight;
    private final int unitSize;

    /**
     * Constructs a GameConfig instance with the specified screen dimensions and unit size.
     *
     * @param screenWidth  the width of the game screen in pixels
     * @param screenHeight the height of the game screen in pixels
     * @param unitSize     the size of each grid unit in pixels
     */
    public GameConfig(int screenWidth, int screenHeight, int unitSize) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.unitSize = unitSize;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getUnitSize() {
        return unitSize;
    }
}
