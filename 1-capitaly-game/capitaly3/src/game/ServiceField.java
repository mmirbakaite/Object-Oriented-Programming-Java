package game;

import players.Player;

public class ServiceField extends Field {
    private int cost;

    /**
     * Constructor ServiceField is responsible for initializing a specified cost amount.
     * @param cost
     */
    public ServiceField(int cost) {
        this.cost = cost;
    }

    /**
     * Method landOn is responsible for triggering the payBank method
     * which deducts the cost by passing through a parameter.
     * @param player
     */
    @Override
    public void landOn(Player player) {
        player.payBank(cost);
    }
}
