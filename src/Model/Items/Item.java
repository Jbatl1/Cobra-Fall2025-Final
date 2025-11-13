package Model.Items;

import java.util.List;

public  class Item {

    private String ItemID;
    private String RoomID;
    private String ItemName;
    private String ItemType;
    private String ItemRarity;
    private int ItemDamage;
    private int ItemDurability;
    private int ItemRestoreHP;
    private String ItemDescription;
    private int ItemUpgrade;
    private String PuzzleID;
    private int quantity;

    private List<String> puzzleIDs;


    public Item(String itemID, String roomID, String itemName, String itemType, String itemRarity, int itemDamage, int itemDurability, int itemRestoreHP, String itemDescription, int itemUpgrade, String puzzleID, int quantity) {
        ItemID = itemID;
        RoomID = roomID;
        ItemName = itemName;
        ItemType = itemType;
        ItemRarity = itemRarity;
        ItemDamage = itemDamage;
        ItemDurability = itemDurability;
        ItemRestoreHP = itemRestoreHP;
        ItemDescription = itemDescription;
        ItemUpgrade = itemUpgrade;
        PuzzleID = puzzleID;
        this.quantity = quantity;
    }

    public String getItemID() { return ItemID; }

    public String getItemName() {return ItemName;}

    public String getRoomID() {return RoomID;}

    public String getItemType() {
        return ItemType;
    }

    public String getItemRarity() {
        return ItemRarity;
    }

    public int getItemDamage() {
        return ItemDamage;
    }

    public int getItemDurability() {
        return ItemDurability;
    }

    public int getItemRestoreHP() {
        return ItemRestoreHP;
    }


    public int getItemUpgrade() {
        return ItemUpgrade;
    }

    public String getItemDescription() {return ItemDescription;}

    public String getPuzzleID() {return PuzzleID;}

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public void increaseQuantity() { this.quantity++; }
    public void decreaseQuantity() { this.quantity--; }

    public List<String> getPuzzleIDs() { return puzzleIDs; }
    public void setPuzzleIDs(List<String> ids) { this.puzzleIDs = ids; }

}