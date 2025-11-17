package Model.Items;

import java.io.Serializable;

public class Consumable extends Item implements Serializable {
    private int health;
    private int amount;

    public Consumable(String itemID, String roomID, String itemName, String itemType, String itemRarity, String itemDescription, String puzzleID, int quantity, int health, int cost) {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDescription, puzzleID, quantity, cost);
        this.health = health;
        this.amount = 1;
        this.setItemDescription("restores " + this.health + " health.");
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
