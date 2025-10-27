package Model.Entities;

import java.util.ArrayList;
import Model.Items.Item;
import Model.Rooms.Room;

public class Player extends Entity {

    // ==============================
    // Fields
    // ==============================
    private ArrayList<Item> inventory;
    private ArrayList<Item> toolBelt;
    private Item equippedItem;
    private Room currRoom;

    // ==============================
    // Constructor
    // ==============================
    public Player(Room startingRoom) {
        this.inventory = new ArrayList<>();
        this.toolBelt = new ArrayList<>();
        this.currRoom = startingRoom;
        this.equippedItem = null;
    }

    // ==============================
    // Inventory / ToolBelt Management
    // ==============================
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    private int isInInventory(String s) {
        // TODO: Check if item with name s is in inventory
        return -1;
    }

    public int EquipItemToHands(String s) {
        // TODO: Equip an item from inventory to hands
        return 0;
    }

    public int EquipItemToToolBelt(String s) {
        // TODO: Equip an item from inventory to tool belt
        return 0;
    }

    public int RemoveItemFromHands(String s) {
        // TODO: Remove equipped item from hands
        return 0;
    }

    public int RemoveItemFromToolBelt(String s) {
        // TODO: Remove item from tool belt
        return 0;
    }

    public int dropItem(String s) {
        // TODO: Drop an item from inventory into the current room
        return 0;
    }

    public int PickupItem(String s) {
        // TODO: Pick up an item from the current room
        return 0;
    }

    public int destroyItem(String s) {
        // TODO: Remove and destroy an item
        return 0;
    }

    // ==============================
// Item Usage and Interaction
// ==============================
    public int useKey(String s) {
        // TODO: Use a key item to unlock something
        return 0;
    }

    public int inspectItem(String s) {
        // TODO: Inspect an item for details
        return 0;
    }

    public int attack() {
        // TODO: Attack using the equipped item
        return 0;
    }

    public int upgradeItem(String s1, String s2) {
        // TODO: Upgrade one item using another
        return 0;
    }

    public int chargeItem(String s1, String s2) {
        // TODO: Charge or refuel an item
        return 0;
    }

    public int readArtifact(String s) {
        // TODO: Read an artifact or note
        return 0;
    }

    // ==============================
// Player Actions
// ==============================
    public int showMap() {
        // TODO: Display the current map or room layout
        return 0;
    }

    public int consume(String s) {
        // TODO: Consume an item (food, potion, etc.)
        return 0;
    }

    public int move(String s) {
        // TODO: Move the player to a new room (e.g., "north", "south", etc.)
        return 0;
    }
}