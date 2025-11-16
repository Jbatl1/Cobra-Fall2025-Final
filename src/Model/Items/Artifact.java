package Model.Items;

public class Artifact extends Item{
    private String memory;

    public Artifact(String itemID, String roomID, String itemName, String itemType, String itemRarity, int itemDamage, int itemDurability, int itemRestoreHP, String itemDescription, int itemUpgrade, String puzzleID, int quantity, String memory, int cost) {
        super(itemID, roomID, itemName, itemType, itemRarity, itemDamage, itemDurability, itemRestoreHP, itemDescription, itemUpgrade, puzzleID, quantity, cost);
        this.memory = memory;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}
