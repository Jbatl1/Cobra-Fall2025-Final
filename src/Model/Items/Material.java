package Model.Items;

public class Material extends Item { //Caleb Butler
    private int damageIncrease;

    public Material(String itemID, String roomID, String itemName, String itemType, String itemRarity, String itemDescription, int itemUpgrade, String puzzleID, int quantity, int cost) {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDescription, puzzleID, quantity, cost);
        this.damageIncrease = itemUpgrade;
    } //Caleb Butler

}
