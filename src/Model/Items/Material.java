package Model.Items;

public class Material extends Item {
    private int damageIncrease;

    public Material(String itemID, String roomID, String itemName, String itemType, String itemRarity, int itemDamage, int itemDurability, int itemRestoreHP, String itemEffect, String itemMessage, int itemUpgrade, String itemNeeded, int damageIncrease) {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDamage, itemDurability, itemRestoreHP, itemEffect, itemMessage, itemUpgrade, itemNeeded);
        this.damageIncrease = damageIncrease;
    }

    public int getDamageIncrease() {
        return damageIncrease;
    }

    public void setDamageIncrease(int damageIncrease) {
        this.damageIncrease = damageIncrease;
    }
}
