package Model.Items;

import Model.Puzzles.Puzzle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public  class Item implements Serializable { //Anita Philip

    private String ItemID;
    private String RoomID;
    private String ItemName;
    private String ItemType;
    private String ItemRarity;
    private String ItemDescription;
    private String PuzzleID;
    private int quantity;
    private List<String> puzzleIDs;        // IDs from DB
    private List<Puzzle> lootPuzzles;     // Actual Puzzle objects
    private int cost;


    public Item(String itemID, String roomID, String itemName, String itemType, String itemRarity, String itemDescription, String puzzleID, int quantity, int cost) { //Anita Philip
        ItemID = itemID;
        RoomID = roomID;
        ItemName = itemName;
        ItemType = itemType;
        ItemRarity = itemRarity;
        ItemDescription = itemDescription;
        PuzzleID = puzzleID;
        this.quantity = quantity;
        this.cost = cost;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
    }

    public String getItemID() { return ItemID; }
    public String getItemName() {return ItemName;}
    public String getRoomID() {return RoomID;}
    public String getItemType() {return ItemType;}
    public String getItemRarity() {return ItemRarity;}
    public String getItemDescription() {return ItemDescription;}
    public String getPuzzleID() {return PuzzleID;}
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void increaseQuantity() { this.quantity++; }
    public void decreaseQuantity() { this.quantity--; }
    public List<String> getPuzzleIDs() { return puzzleIDs; }
    public void setPuzzleIDs(List<String> ids) { this.puzzleIDs = ids; }


    // ------------------ Loot Puzzle Methods ------------------
    public void addLootPuzzle(Puzzle puzzle) { //Anita Philip
        if (lootPuzzles == null) {
            lootPuzzles = new ArrayList<>();
        }
        if (puzzle != null && !lootPuzzles.contains(puzzle)) {
            lootPuzzles.add(puzzle);
        }
    }

    public List<Puzzle> getLootPuzzles() { //Anita Philip
        if (lootPuzzles == null) {
            lootPuzzles = new ArrayList<>();
        }
        return lootPuzzles;
    }

    public void clearLootPuzzles() { //Anita Philip
        if (lootPuzzles != null) {
            lootPuzzles.clear();
        }
    }

    public int getCost() { return cost; }

}