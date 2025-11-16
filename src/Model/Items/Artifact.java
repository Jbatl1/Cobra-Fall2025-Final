package Model.Items;

public class Artifact extends Item{

    public Artifact(String itemID, String roomID, String itemName, String itemType, String itemRarity, String itemDescription, String puzzleID, int quantity, int cost) {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDescription, puzzleID, quantity, cost);
    }
}
