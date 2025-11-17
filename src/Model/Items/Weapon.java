package Model.Items;

import java.io.Serializable;

public class Weapon extends Item implements Serializable {
    private int damage;
    private int durability;

    public Weapon(
            String itemID,
            String roomID,
            String itemName,
            String itemType,
            String itemRarity,
            int itemDamage,
            int itemDurability,
            String itemDescription,
            String puzzleID,
            int quantity,
            int cost
    ) {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDescription, puzzleID, quantity, cost);
        this.damage = itemDamage;
        this.durability = itemDurability;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int useDurability() { // Caleb
        durability--;
        if (durability >= 1) {
            return 1;
        }
        return -1;
    }
}
