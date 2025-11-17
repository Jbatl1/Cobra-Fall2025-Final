package Model.Items;

import java.io.Serializable;

public class Artifact extends Item implements Serializable {

    public Artifact(String itemID, String roomID, String itemName, String itemType, String itemRarity, String itemDescription, String puzzleID, int quantity, int cost) { //Caleb Butler
        super(itemID, roomID, itemName, itemType, itemRarity, itemDescription, puzzleID, quantity, cost);
    }

}
