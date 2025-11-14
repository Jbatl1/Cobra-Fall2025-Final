package Model.Items;

public class Material extends Item {
    private int damageIncrease;

    public Material(String itemID, String roomID, String itemName, String itemType, String itemRarity, int itemDamage, int itemDurability, int itemRestoreHP, String itemDescription, int itemUpgrade, String puzzleID, int quantity, int damageIncrease) {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDamage, itemDurability, itemRestoreHP, itemDescription, itemUpgrade, puzzleID, quantity);
        this.damageIncrease = damageIncrease;
    }

    public int getDamageIncrease() {
        return damageIncrease;
    }

    public void setDamageIncrease(int damageIncrease) {
        this.damageIncrease = damageIncrease;
    }
}
