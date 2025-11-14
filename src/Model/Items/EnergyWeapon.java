package Model.Items;

public class EnergyWeapon extends Weapon{
    private int charge;
    private int maxCharge;


    public EnergyWeapon(String itemID, String roomID, String itemName, String itemType, String itemRarity, int itemDamage, int itemDurability, int itemRestoreHP, String itemDescription, int itemUpgrade, String puzzleID, int quantity, int damage, int durability, int charge, int maxCharge) {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDamage, itemDurability, itemRestoreHP, itemDescription, itemUpgrade, puzzleID, quantity, damage, durability);
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
