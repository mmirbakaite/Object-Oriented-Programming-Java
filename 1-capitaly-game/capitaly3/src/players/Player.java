package players;

import game.Board;
import game.PropertyField;
import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected String name;
    protected int money;
    protected int position;
    protected List<PropertyField> ownedProperties;
    protected boolean bankrupt;

    /**
     * Constructor Player is responsible for setting the initial data of the players.
     * @param name
     */
    public Player(String name) {
        this.name = name;
        this.money = 10000;
        this.position = 0;
        this.ownedProperties = new ArrayList<>();
        this.bankrupt = false;
    }

    /**
     * Method move is responsible for updating a player's position on the game board.
     * @param roll
     * @param board
     */
    public void move(int roll, Board board) {
        position = (position + roll) % board.fields.size();
        board.getField(position).landOn(this);
    }

    /**
     * Method payRent is responsible for paying to the property owner.
     * Player goes bankrupt if doen’t have enough money.
     * @param owner
     * @param amount
     */
    public void payRent(Player owner, int amount) {
        if (money >= amount) {
            money -= amount;
            owner.receiveMoney(amount);
        } else {
            bankrupt();
        }
    }

    /**
     * Method payBank is responsible for paying to the bank.
     * Player goes bankrupt if doen’t have enough money.
     * @param amount
     */
    public void payBank(int amount) {
        if (money >= amount) {
            money -= amount;
        } else {
            bankrupt();
        }
    }

    /**
     * Method receiveMoney is responsible for tranfering funds to the needed player.
     * @param amount
     */
    public void receiveMoney(int amount) {
        money += amount;
    }

    /**
     * Method considerBuying is responsible for the strategies of the players.
     * Implementation will be found on subclasses of players.
     * @param property
     */
    public void considerBuying(PropertyField property) {

    }

    /**
     * Method bankrupt is responsible for clearing out the properties of the player that lost the game.
     */
    public void bankrupt() {
        bankrupt = true;
        ownedProperties.clear();
    }

    /**
     * Method isBankrupt is responsible for returning the bankrupt status of the player that lost the game.
     * @return
     */
    public boolean isBankrupt() {
        return bankrupt;
    }

    /**
     * Method getName is responsible for returning the name of the player that lost the game.
     * @return
     */
    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public int getBalance() {
        return money;
    }

    /**
     * Method toString is responsible for returning the data of current player status in a string format.
     * @return
     */
    @Override
    public String toString() {
        return "Player: " + name + ", Money: " + money + ", Properties: " + ownedProperties.size();
    }
}
