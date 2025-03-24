package gameLogic;

import java.util.Random;

/**
 * The Board class represents the game board for a Rubik's Clock puzzle.
 * It manages a grid of Field objects, each representing a clock with a number from 1 to 12.
 * The board handles initialization, incrementing clocks, and checking the completion status.
 */
public class Board {
    private Field[][] fields;
    private int boardSize;
    private int clickCount = 0;
    private int[][] initialClockState;

    /**
     * Constructs a Board of a specified size and initializes its state.
     *
     * @param boardSize The size of the board (e.g., 3 for a 3x3 board).
     */
    public Board(int boardSize) {
        this.boardSize = boardSize;
        fields = new Field[boardSize][boardSize];
        initialClockState = new int[boardSize][boardSize];
        initializeBoard();
    }

    /**
     * Initializes the board with random clock values and saves the initial state.
     */
    private void initializeBoard() {
        Random random = new Random();
        initializeFields(random);
        initializeInitialClockState();
    }

    /**
     * Initializes each Field on the board with a random clock value between 1 and 12.
     *
     * @param random Random object used to generate random clock values.
     */
    private void initializeFields(Random random) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                fields[i][j] = new Field(random.nextInt(12) + 1);
            }
        }
    }

    /**
     * Saves the initial clock state of each Field to allow game reset.
     */
    private void initializeInitialClockState() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                initialClockState[i][j] = fields[i][j].getNumber();
            }
        }
    }

    /**
     * Returns the size of the board.
     *
     * @return The board size.
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Retrieves the Field at a specific location on the board.
     *
     * @param x The row index of the field.
     * @param y The column index of the field.
     * @return The Field object at the specified position.
     */
    public Field get(int x, int y) {
        return fields[x][y];
    }

    /**
     * Increments the clocks in positions surrounding the given extra button's index.
     *
     * @param extraButtonIndex The index of the extra button clicked.
     */
    public void incrementSurroundingClocks(int extraButtonIndex) {
        int[][] positionsToIncrement = getSurroundingPositions(extraButtonIndex);
        incrementClockPositions(positionsToIncrement);
    }

    /**
     * Returns the positions of the fields surrounding the given extra button index.
     *
     * @param extraButtonIndex The index of the extra button clicked.
     * @return An array of integer pairs representing the surrounding positions.
     */
    private int[][] getSurroundingPositions(int extraButtonIndex) {
        int[][][] surroundingPositions = {
                {{0, 0}, {0, 1}, {1, 0}, {1, 1}},
                {{0, 1}, {0, 2}, {1, 1}, {1, 2}},
                {{1, 0}, {1, 1}, {2, 0}, {2, 1}},
                {{1, 1}, {1, 2}, {2, 1}, {2, 2}}
        };
        return surroundingPositions[extraButtonIndex];
    }

    /**
     * Increments the clock values at specified positions on the board.
     *
     * @param positionsToIncrement An array of row and column indices to increment.
     */
    private void incrementClockPositions(int[][] positionsToIncrement) {
        for (int[] pos : positionsToIncrement) {
            int row = pos[0];
            int col = pos[1];
            incrementClockAtPosition(row, col);
        }
    }

    /**
     * Increments the clock value at a specified position, wrapping back to 1 if it exceeds 12.
     *
     * @param row The row index of the clock.
     * @param col The column index of the clock.
     */
    private void incrementClockAtPosition(int row, int col) {
        if (row < boardSize && col < boardSize) {
            Field field = fields[row][col];
            int currentNumber = field.getNumber();
            if (currentNumber < 12) {
                field.setNumber(currentNumber + 1);
            }
        }
    }

    /**
     * Checks if all clocks on the board are set to 12.
     *
     * @return True if all clocks are set to 12; otherwise, false.
     */
    public boolean allClocksAreMaxed() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (fields[i][j].getNumber() < 12) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Resets the game to its initial clock values and resets the click count.
     */
    public void refreshGame() {
        resetClockValues();
        clickCount = 0;
    }

    /**
     * Resets all clock values to their initial state.
     */
    private void resetClockValues() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                fields[i][j].setNumber(initialClockState[i][j]);
            }
        }
    }

    /**
     * Increments the click count by one.
     */
    public void incrementClickCount() {
        clickCount++;
    }

    /**
     * Returns the current number of clicks made.
     *
     * @return The number of clicks.
     */
    public int getClickCount() {
        return clickCount;
    }
}
