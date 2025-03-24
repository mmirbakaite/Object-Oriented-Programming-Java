package game;

import players.Player;

public class LuckyField extends Field {
    private int reward;

    /**
     * Constructor LuckyField is responsible for initializing a specified reward amount.
     * @param reward
     */
    public LuckyField(int reward) {
        this.reward = reward;
    }

    public int getReward() {
        return reward;
    }

    /**
     * Method landOn is responsible for triggering the receiveMoney method
     * which adds the reward by passing through a parameter.
     * @param player
     */
    @Override
    public void landOn(Player player) {
        player.receiveMoney(reward);
    }
}
