package Model.Items;

public class EnergyWeapon extends Weapon{//Caleb Butler
    private int charge;
    private int maxCharge;


    public EnergyWeapon(//Caleb Butler
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
            int charge,
            int maxCharge,
            int cost)
    {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDamage, itemDurability, itemDescription, puzzleID, quantity, cost);
        this.charge = charge;
        this.maxCharge = maxCharge;
    }

}
