package game;

import players.Player;

public abstract class Field {
    /**
     * Abstract method is responsible to determine action when landed on a certain type of field.
     * This will be extended in subclasses of Field.
     * @param player
     */
    public abstract void landOn(Player player);
}
