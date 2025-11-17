package Model.Items;

public class Consumable extends Item{ //Caleb Butler
    private int health;
    private int amount;

    public Consumable(String itemID, String roomID, String itemName, String itemType, String itemRarity, String itemDescription, String puzzleID, int quantity, int health, int cost) { //Caleb Butler
        super(itemID, roomID, itemName, itemType, itemRarity, itemDescription, puzzleID, quantity, cost);
        this.health = health;
        this.amount = 1;
        this.setItemDescription("restores " + this.health + " health.");
    }

    public int getHealth() {
        return health;
    } //Caleb Butler

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
