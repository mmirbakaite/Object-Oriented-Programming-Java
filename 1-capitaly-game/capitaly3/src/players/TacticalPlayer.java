package players;

import game.PropertyField;

public class TacticalPlayer extends Player {
    private boolean skipNextPurchase;

    /**
     * Constructor TacticalPlayer is responsible for calling the constructor of the superclass (Player) to initialize inherited data about the Tactical player.
     * skipNextPurchase is false because player is currently considering buying.
     * @param name
     */
    public TacticalPlayer(String name) {
        super(name);
        this.skipNextPurchase = false;
    }

    /**
     * Method considerBuying is responsible for implementing the game strategy of the Tactical player.
     * If skipNextPurchase is true player stops the decision making of buying (sets it to false for another round).
     * If it is false it continues it.
     * Player buys if has enough money.
     * @param property
     */
    @Override
    public void considerBuying(PropertyField property) {
        if (skipNextPurchase) {
            skipNextPurchase = false;
            return;
        }

        if (!property.isOwned() && money >= 1000) {
            property.buy(this);
            money -= 1000;
            ownedProperties.add(property);
        } else if (property.canBuyHouse(this) && money >= 4000) {
            property.buildHouse();
            money -= 4000;
        }

        skipNextPurchase = true;
    }
}
