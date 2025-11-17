package Model.Items;

public class Weapon extends Item{ //Caleb Butler
    private int damage;
    private int durability;

    public Weapon( //Caleb Butler
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
    } //Caleb Butler

    public int getDamage() {
        return damage;
    }//Caleb Butler


    public int useDurability() { //Caleb Butler
        durability--;
        if (durability >= 1) {
            return 1;
        }
        return -1;
    }
}
