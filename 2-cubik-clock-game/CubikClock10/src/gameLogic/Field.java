package gameLogic;

/**
 * The Field class represents a single field or cell on the game board.
 * Each Field object holds an integer value representing a number, which can be updated.
 */
public class Field {
    private int number;

    /**
     * Constructs a Field with a specified initial number.
     *
     * @param number The initial number for this field.
     */
    public Field(int number) {
        this.number = number;
    }

    /**
     * Gets the current number in this field.
     *
     * @return The number in this field.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets a new number for this field.
     *
     * @param number The new number to set in this field.
     */
    public void setNumber(int number) {
        this.number = number;
    }
}
