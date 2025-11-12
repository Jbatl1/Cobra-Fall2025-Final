package Model.Items;

public  class Item {

    private String ItemID;
    private String RoomID;
    private String ItemName;
    private String ItemType;
    private String ItemRarity;
    private int ItemDamage;
    private int ItemDurability;
    private int ItemRestoreHP;
    private String ItemEffect;
    private String ItemMessage;
    private int ItemUpgrade;
    private String ItemNeeded;

    public Item(String itemID, String roomID, String itemName, String itemType, String itemRarity, int itemDamage, int itemDurability, int itemRestoreHP, String itemEffect, String itemMessage, int itemUpgrade, String itemNeeded) {
        ItemID = itemID;
        RoomID = roomID;
        ItemName = itemName;
        ItemType = itemType;
        ItemRarity = itemRarity;
        ItemDamage = itemDamage;
        ItemDurability = itemDurability;
        ItemRestoreHP = itemRestoreHP;
        ItemEffect = itemEffect;
        ItemMessage = itemMessage;
        ItemUpgrade = itemUpgrade;
        ItemNeeded = itemNeeded;
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

    public String getItemEffect() {
        return ItemEffect;
    }

    public String getItemMessage() {
        return ItemMessage;
    }

    public int getItemUpgrade() {
        return ItemUpgrade;
    }

    public String getItemNeeded() {
        return ItemNeeded;
    }
}