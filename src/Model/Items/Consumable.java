package Model.Items;

public class Consumable extends Item{
    private int health;
    private double riskOfPoison;
    private int amount;

    public Consumable(String itemID, String roomID, String itemName, String itemType, String itemRarity, int itemDamage, int itemDurability, int itemRestoreHP, String itemEffect, String itemMessage, int itemUpgrade, String itemNeeded, int health, double riskOfPoison, int amount) {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDamage, itemDurability, itemRestoreHP, itemEffect, itemMessage, itemUpgrade, itemNeeded);
        this.health = health;
        this.riskOfPoison = riskOfPoison;
        this.amount = amount;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getRiskOfPoison() {
        return riskOfPoison;
    }

    public void setRiskOfPoison(double riskOfPoison) {
        this.riskOfPoison = riskOfPoison;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
