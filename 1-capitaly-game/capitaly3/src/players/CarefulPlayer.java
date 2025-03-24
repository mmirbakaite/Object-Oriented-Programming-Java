package players;

import game.PropertyField;

public class CarefulPlayer extends Player {
    /**
     * Constructor CarefulPlayer is responsible for calling the constructor of the superclass (Player)
     * to initialize inherited data about the Careful player.
     * @param name
     */
    public CarefulPlayer(String name) {
        super(name);
    }

    /**
     * Method considerBuying is responsible for implementing the game strategy of the Careful player.
     * Buys only if has enough money from half of their total funds.
     * @param property
     */
    @Override
    public void considerBuying(PropertyField property) {
        if (!property.isOwned() && money >= 1000 && money / 2 >= 1000) {
            property.buy(this);
            money -= 1000;
            ownedProperties.add(property);
        } else if (property.canBuyHouse(this) && money / 2 >= 4000) {
            property.buildHouse();
            money -= 4000;
        }
    }
}
