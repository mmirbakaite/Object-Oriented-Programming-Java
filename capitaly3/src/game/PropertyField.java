package game;

import players.Player;

public class PropertyField extends Field {
    private Player owner;
    private boolean hasHouse;

    /**
     * Constructor PropertyField is responsible for creating  a PropertyField instance with default values.
     */
    public PropertyField() {
        owner = null;
        hasHouse = false;
    }

    /**
     * Method landOn is responsible for buying/paying rent when stepped on property.
     * @param player
     */
    @Override
    public void landOn(Player player) {
        if (owner == null) {
            player.considerBuying(this);
        } else if (owner != player) {
            player.payRent(owner, hasHouse ? 2000 : 500);
        }
    }

    /**
     * Method canBuyHouse is responsible for checking whether a property has an owner, and whether it has a house.
     * @param player
     * @return
     */
    public boolean canBuyHouse(Player player) {
        return owner == player && !hasHouse;
    }

    /**
     * Method buildHouse is responsible for stating the fact that the house is built.
     */
    public void buildHouse() {
        hasHouse = true;
    }

    /**
     * Method getOwner is responsible for getting the owner of the property.
     * @return
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Method buy is responsible for setting the owner of the property.
     * @param player
     */
    public void buy(Player player) {
        owner = player;
    }

    /**
     * Method release is responsible for resetting the owner of the property to none
     * and that it is free to buy, build house (because it is resetted to none).
     */
    public void release() {
        owner = null;
        hasHouse = false;
    }

    /**
     * Method isOwned is responsible for checking if a property belongs to an owner.
     * @return
     */
    public boolean isOwned() {
        return owner != null;
    }
}
