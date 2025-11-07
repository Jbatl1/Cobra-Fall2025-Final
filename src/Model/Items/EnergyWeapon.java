package Model.Items;

public class EnergyWeapon extends Weapon{
    private int charge;
    private int maxCharge;

    public EnergyWeapon(String itemID, String roomID, String itemName, String itemType, String itemRarity, int itemDamage, int itemDurability, int itemRestoreHP, String itemEffect, String itemMessage, int itemUpgrade, String itemNeeded, int damage, int durability, int charge, int maxCharge) {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDamage, itemDurability, itemRestoreHP, itemEffect, itemMessage, itemUpgrade, itemNeeded, damage, durability);
        this.charge = charge;
        this.maxCharge = maxCharge;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public void setMaxCharge(int maxcCharge) {
        this.maxCharge = maxcCharge;
    }
}
