package Model.Items;

import java.io.Serializable;

public class Key extends Item implements Serializable {

    public Key(String itemID, String roomID, String itemName, String itemType, String itemRarity, String itemDescription, String puzzleID, int quantity, int cost) { //Caleb Butler
        super(itemID, roomID, itemName, itemType, itemRarity, itemDescription, puzzleID, quantity, cost);
    }
}
