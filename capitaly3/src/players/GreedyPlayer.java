package players;

import game.PropertyField;

/**
 * Constructor GreedyPlayer is responsible for calling the constructor of the superclass (Player)
 * to initialize inherited data about the Greedy player.
 */
public class GreedyPlayer extends Player {
    public GreedyPlayer(String name) {
        super(name);
    }

    /**
     * Method considerBuying is responsible for implementing the game strategy of the Greedy player.
     * Always buys if has enough money.
     * @param property
     */
    @Override
    public void considerBuying(PropertyField property) {
        if (!property.isOwned() && money >= 1000) {
            property.buy(this);
            money -= 1000;
            ownedProperties.add(property);
        } else if (property.canBuyHouse(this) && money >= 4000) {
            property.buildHouse();
            money -= 4000;
        }
    }


}
